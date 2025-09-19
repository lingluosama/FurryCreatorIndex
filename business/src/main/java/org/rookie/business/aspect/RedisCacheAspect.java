package org.rookie.business.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.rookie.annotation.AtomicIncrementRedis;
import org.rookie.model.message.RedisAutoIncrementMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RedisCacheAspect {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheAspect.class);
    private final RedisTemplate<String, Object> redisTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    /**
     * @param joinPoint--第一个参数为更新对象Id,第二个参数为变动操作的大小,方法返回值必须为Boolean，表示是否允许更改
     * @param atomicIncrementRedis
     * @return
     * @throws Throwable
     */
    @Around("@annotation(atomicIncrementRedis)")
    private Boolean atomicIncrementRedis(ProceedingJoinPoint joinPoint, AtomicIncrementRedis atomicIncrementRedis) throws Throwable {
        String keyPrefix = atomicIncrementRedis.keyPrefix();
        Object[] args = joinPoint.getArgs();
        if (args.length > 1) {
            String key = String.format(keyPrefix, args[0]);
            Object proceed = joinPoint.proceed();
            if ((boolean) proceed) {
                redisTemplate.opsForValue().increment(key, (Long) args[1]);
                RedisAutoIncrementMessage message = new RedisAutoIncrementMessage();
                message.setIncrement((int) args[1]);
                message.setFildName(atomicIncrementRedis.filedName());
                message.setTableName(atomicIncrementRedis.tableName());
                kafkaTemplate.send("redis-auto-increment", message);
                return true;
            } else {
                return false;
            }
        }
        return (boolean) joinPoint.proceed();

    }

}
