package org.rookie.user.config;

import com.mybatisflex.core.datasource.DataSourceManager;
import lombok.Data;
import lombok.Getter;
import org.rookie.config.RWSeparationStrategy;
import org.rookie.config.RandomReadDataSourceStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Data
public class CircuitBreakerStateManager {
    
    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerStateManager.class.getName());
    
    @Getter
    public enum  BreakerState {
        DEFAULT(0),
        RANDOM_READ(1),
        RANDOM_THROW(2);
        private final int code;
        BreakerState(int code) {
            this.code = code;
        }
    }
    
    private final AtomicInteger level=new AtomicInteger(0);
    
    public volatile long RandReadLevelThreshold=10000;
    public volatile long RandomThrowLevelThreshold=20000;
    public volatile long RecoveryThreshold = 10000; // 恢复默认阈值
    
    public BreakerState getCurrentLevel() {
        int code =level.get();
        for (BreakerState state : BreakerState.values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        return BreakerState.DEFAULT; // 默认返回正常状态
    }


    public void updateLevel(BreakerState newLevel) {
        level.set(newLevel.getCode());
        System.out.println("熔断器状态更新为: " + newLevel);
    }

    public void resetThresholds() {
        this.RandReadLevelThreshold = 10000; 
        this.RandomThrowLevelThreshold = 20000; 
        this.RecoveryThreshold = 10000; 
        log.warn("熔断阈值已经恢复默认值");
    }
    
    
    
    
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public static String NOT_HIT_REDIS_COUNT_KEY = "cache:query_not_hit_count";
    public static long NOT_HIT_VALUE_EXPIRE_MINUTES = 2;
    
    public void incrementNotHitCount(){
        try {
            Long currentCount = redisTemplate.opsForValue().increment(NOT_HIT_REDIS_COUNT_KEY, 1);
            log.warn("穿透触发:{}",currentCount);
        
        } catch (Exception e){
            log.warn("穿透计数更新异常:{}",e.getMessage());
        }
        
    }
    
    @Scheduled(initialDelay = 10000,fixedRate = 120000)
    public void checkLevel(){
        try {
            if(Boolean.FALSE.equals(redisTemplate.hasKey(NOT_HIT_REDIS_COUNT_KEY))){
                log.warn("正在执行定时穿透数量检查,未查询到计数");
                this.updateLevel(BreakerState.DEFAULT);
                return;
            }
            Object value = redisTemplate.opsForValue().get(NOT_HIT_REDIS_COUNT_KEY);
            if(value instanceof Long|| value instanceof Integer){
                log.warn("正在执行定时穿透数量检查,周期内缓存未命中:{}",value);
                if((Long)value>=this.getRandReadLevelThreshold()){
                    this.updateLevel(BreakerState.RANDOM_READ);
                    DataSourceManager.setDataSourceShardingStrategy(new RandomReadDataSourceStrategy());
                }
                else if((Long)value>=this.getRandomThrowLevelThreshold()){
                    this.updateLevel(BreakerState.RANDOM_THROW);
                    DataSourceManager.setDataSourceShardingStrategy(new RandomReadDataSourceStrategy());
                }
                else if((Long)value<this.getRecoveryThreshold()){
                    this.updateLevel(BreakerState.DEFAULT);
                    DataSourceManager.setDataSourceShardingStrategy(new RWSeparationStrategy());
                }
            }else{
                if (value != null) {
                    log.warn("正在执行定时穿透数量检查,参数类型错误:{}",value.getClass());
                }
            }
        }catch (Exception e){
            log.warn("穿透计数更新检查异常:{}",e.getMessage());
        }

        
    }
    

}
