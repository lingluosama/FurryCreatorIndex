package org.rookie.user.aspect;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.ReflectionUtil;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Null;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.rookie.annotation.RedisCache;
import org.rookie.config.BusinessException;
import org.rookie.config.BusinessExceptionEnum;
import org.rookie.user.config.CircuitBreakerStateManager;
import org.rookie.utils.GetFiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

@Aspect
@Component
public class RedisCacheAspect {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheAspect.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    @Resource
    private CircuitBreakerStateManager circuitBreakerStateManager;

    
    
    @Around("@annotation(redisCache)")
    public Object readCache(ProceedingJoinPoint joinPoint, RedisCache redisCache) throws Throwable {
        String keyTemplate = redisCache.key();
        Object[] args = joinPoint.getArgs();
        String cacheKey = keyTemplate;
        if(args.length > 0&&keyTemplate.contains("%s")){
            cacheKey=String.format(keyTemplate,args[0]);
        }
        
        Object cache=redisTemplate.opsForValue().get(cacheKey);
        if(cache!=null){
            return cache;
        }
        Object result;
        
        //高压力状态下随机丢弃请求
        if(circuitBreakerStateManager.getCurrentLevel()==CircuitBreakerStateManager.BreakerState.RANDOM_THROW){
            if(Math.random()<0.5)throw new RuntimeException("数据库服务繁忙");
        }
        
        //未命中并请求数据库
        try {
            result = joinPoint.proceed();
            redisTemplate.opsForValue().set(
                    cacheKey,
                    result,
                    redisCache.expire()*60+ RandomGenerator.getDefault().nextLong(30),//离散化过期时间
                    TimeUnit.MINUTES
            );
            circuitBreakerStateManager.incrementNotHitCount();

        }catch (BusinessException e){
            //空值缓存
            if(e.getCode()== BusinessExceptionEnum.NOT_FIND_IN_DATABASE.getCode()){
                redisTemplate.opsForValue().set(
                        cacheKey,
                        NullValue.INSTANCE,
                        3,
                        TimeUnit.MINUTES
                );
                return null;

            }else{
                throw e;
            }
            
        }
        return result;
        
    }
    
    @Around("@annotation(org.rookie.annotation.CacheDbSync)")
    public Object syncCache(ProceedingJoinPoint joinPoint) throws Throwable {
        
        Object[] args = joinPoint.getArgs();
        //第一个参数要求为更新后实体对象,要求包含id字段
        Object updateEntity = args[0];

        circuitBreakerStateManager.incrementNotHitCount();
        //处理缓存同步
        return transactionTemplate.execute(status -> {
            try {
                Object result = joinPoint.proceed(); 

                String topic = "sync-cache";
                String key = updateEntity.getClass().getSimpleName();

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
