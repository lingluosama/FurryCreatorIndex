package org.rookie.data.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.rookie.annotation.AtomicIncrementRedis;
import org.rookie.annotation.RedisCache;
import org.rookie.exception.BusinessException;
import org.rookie.exception.BusinessExceptionEnum;
import org.rookie.data.config.CircuitBreakerStateManager;
import org.rookie.model.message.RedisAutoIncrementMessage;
import org.rookie.model.query.CacheKeyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

@Aspect
@Component
@RequiredArgsConstructor
public class RedisCacheAspect {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheAspect.class);
    private final RedisTemplate<String, Object> redisTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final TransactionTemplate transactionTemplate;

    private final CircuitBreakerStateManager circuitBreakerStateManager;

    private final RedissonClient redissonClient;

    //处理使用id查询的缓存
    @Around("@annotation(redisCache)")
    public Object readCache(ProceedingJoinPoint joinPoint, RedisCache redisCache) throws Throwable {

        String keyTemplate = redisCache.key();
        Object[] args = joinPoint.getArgs();
        String cacheKey = keyTemplate;

        if (args.length > 0) {
            Object arg = joinPoint.getArgs()[0];
            //使用的query类的话走toKey方法，其他的例如使用id的情况则直接拼接上去
            if (arg instanceof CacheKeyProvider) {
                cacheKey = ((CacheKeyProvider) arg).toCacheKey();
            } else if (keyTemplate.contains("%s")) cacheKey = String.format(keyTemplate, arg);
        }

        //获取读锁
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(cacheKey);
        readWriteLock.readLock().lock();

        Object cache = redisTemplate.opsForValue().get(cacheKey);
        if (cache != null) {
            //命中缓存后释放读锁
            readWriteLock.readLock().unlock();
            return cache;
        }
        Object result;

        //高压力状态下随机丢弃请求
        if (circuitBreakerStateManager.getCurrentLevel() == CircuitBreakerStateManager.BreakerState.RANDOM_THROW) {
            if (Math.random() < 0.5) throw new RuntimeException("数据库服务繁忙");
        }

        //未命中并请求数据库
        try {
            //释放读锁并尝试获取写锁   
            readWriteLock.readLock().unlock();
            readWriteLock.writeLock().lock();

            result = joinPoint.proceed();
            redisTemplate.opsForValue().set(
                    cacheKey,
                    result,
                    redisCache.expire() * 60 + RandomGenerator.getDefault().nextLong(30),//离散化过期时间
                    TimeUnit.MINUTES
            );
            circuitBreakerStateManager.incrementNotHitCount();

        } catch (BusinessException e) {
            //空值缓存
            if (e.getCode() == BusinessExceptionEnum.NOT_FIND_IN_DATABASE.getCode()) {
                redisTemplate.opsForValue().set(
                        cacheKey,
                        NullValue.INSTANCE,
                        3,
                        TimeUnit.MINUTES
                );

            }
            throw e;
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return result;

    }

    /**
     * @param joinPoint-第一个参数为更新对象的key
     * @return
     * @throws Throwable
     */
    @Around("@annotation(org.rookie.annotation.UpdateCache)")
    private Object updateCache(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {
            RReadWriteLock readWriteLock = redissonClient.getReadWriteLock((String) args[0]);
            readWriteLock.writeLock().lock();
            try {
                Object result = joinPoint.proceed();
                redisTemplate.delete((String) args[0]);
                return result;
            } catch (Throwable e) {
                readWriteLock.writeLock().unlock();
                throw e;
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 
     * @param joinPoint--第一个参数为更新对象Id,第二个参数为变动操作的大小
     * @param atomicIncrementRedis
     * @return
     * @throws Throwable
     */
    @Around("@annotation(atomicIncrementRedis)")
    private Boolean atomicIncrementRedis(ProceedingJoinPoint joinPoint, AtomicIncrementRedis atomicIncrementRedis) throws Throwable { 
        String keyPrefix = atomicIncrementRedis.keyPrefix();
        Object[] args = joinPoint.getArgs();
        if(args.length>1){
            String key=String.format(keyPrefix,args[0]);
            Object proceed = joinPoint.proceed();
            if((boolean)proceed){
                redisTemplate.opsForValue().increment(key, (Long) args[1]);
                RedisAutoIncrementMessage message = new RedisAutoIncrementMessage();
                message.setIncrement((int) args[1]);
                message.setFildName(atomicIncrementRedis.filedName());
                message.setTableName(atomicIncrementRedis.tableName());
                kafkaTemplate.send("redis-auto-increment",message);
                return true;
            }else{
                return false;
            }
        }
        return (boolean)joinPoint.proceed();

    }
    
    
    //将更新放到消息队列中，已弃用
    @Around("@annotation(org.rookie.annotation.CacheDbSync)")
    public Object syncCache(ProceedingJoinPoint joinPoint) throws Throwable {
        
        
        Object[] args = joinPoint.getArgs();
        //第一个参数要求为更新后实体对象,要求包含id字段
        Object updateEntity = args[0];
        String topic = "sync-cache";
        String key = updateEntity.getClass().getSimpleName();
        

        circuitBreakerStateManager.incrementNotHitCount();
        //处理缓存同步
        return transactionTemplate.execute(status -> {
            try {
                Object result = joinPoint.proceed(); 



                kafkaTemplate.executeInTransaction(operations -> {
                    operations.send(topic, key, updateEntity); // 这里会阻塞并等待 ack
                    return null;
                });

                return result;
            } catch (Throwable e) {
                
                status.setRollbackOnly(); // 回滚事务
                throw new BusinessException(402, "异步同步缓存错误,消息发送失败:" + e.getMessage());
            
            }
        });
        

    }
    



}
