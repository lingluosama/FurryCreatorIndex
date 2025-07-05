package org.rookie.user.consumer;

import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.rookie.config.BusinessException;
import org.rookie.utils.GetFiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;


@Service
public class AsyncCacheHandler {

    private static final Logger log = LoggerFactory.getLogger(AsyncCacheHandler.class);
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    @Qualifier("redisTaskExecutor")
    private ThreadPoolTaskExecutor redisTaskExecutor;
    
    

    public void updateCache(ConsumerRecord<String, Object> record) {
        redisTaskExecutor.execute(()->{
            try {
                doCacheUpdate(record);
            }catch (Exception e) {
                throw new RuntimeException("处理kafka消息[" +  record.value()+"]时发生错误:",e);
            }
        });
        
    }
    
    @KafkaListener(topics = "sync-cache",groupId = "cache-group")
    private void doCacheUpdate(ConsumerRecord<String, Object> record) throws Exception{
        Object value = record.value();
//        log.warn("已接受消息类型:{}",record.value().getClass());
        if(value==null){
//            log.warn("消息对象值为null");
            throw new IllegalAccessException("?收到了空对象的缓存更新消息");
        }
        try {

            Long id = GetFiled.reflectId(value);

            String key = value.getClass().getSimpleName()+":" + id.toString();

            redisTemplate.opsForValue().set(key, value,186*60+ RandomGenerator.getDefault().nextLong(30), TimeUnit.MINUTES);
//            log.debug("缓存已更新:{}",key);
        }catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("?没法对传来的缓存对象进行反射?", e);
        }
        
    }
    
    
    private static Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null&&!clazz.equals(Object.class)) {
            try{
                return clazz.getDeclaredField(fieldName);
            }catch (NoSuchFieldException e){
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
    
}
