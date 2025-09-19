package org.rookie.data.consumer;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.DbChain;
import com.mybatisflex.core.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.rookie.model.message.RedisAutoIncrementMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisAutoIncrementHandler {

    private static final Logger log = LoggerFactory.getLogger(RedisAutoIncrementHandler.class);

    @KafkaListener(topics = "redis_auto_increment",groupId = "cache-group")
    private void doDataBaseSync(ConsumerRecord<String, RedisAutoIncrementMessage> record)throws  Exception{
        RedisAutoIncrementMessage message = record.value();
        if(message==null){
            throw new IllegalAccessException("?收到了空对象的缓存更新消息");
        }
        try {
            String sql="update "+message.getTableName()+" set "+message.getFildName()+"="+message.getFildName()+"+"+message.getIncrement()+" where id="+message.getPrimaryKey();
            Db.updateBySql(sql);
        }catch (Exception e) {
            log.warn("更新数据库异常:{}",e.getMessage());
            e.printStackTrace();
            
        }
        
    }
}
