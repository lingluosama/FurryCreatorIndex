package org.rookie.data.consumer;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.rookie.data.converter.WikiEntryConverter;
import org.rookie.data.service.IDraftService;
import org.rookie.data.service.IWikiEntryService;
import org.rookie.model.entity.database.Draft;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.form.WikiEntryForm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class DraftSaveHandler {
    
    private final IDraftService draftService;
    
    private final RedisTemplate<String,String> redisTemplate;
    
    private final WikiEntryConverter wikiEntryConverter;
    
    @KafkaListener(topics = "draft-entry",groupId = "draft-group")
    private void handleDraftSave(ConsumerRecord<String, WikiEntryForm> record)throws Exception {
        WikiEntryForm form = record.value();
        String key="draft-cache-entry:"+form.getId().toString()+"&"+form.getUpdatedBy();
        
        String cacheDraft = redisTemplate.opsForValue().get(key);
        if(cacheDraft==null){
            Draft draft = draftService.saveDraft(form);
            String newDraftJson = JSON.toJSONString(draft);
            redisTemplate.opsForValue().set(key, newDraftJson);
        }else{
            //异步更新数据库
            CompletableFuture.runAsync(() -> {draftService.saveDraft(form);});
            
            //手动处理缓存更换新
            Draft draft = JSON.parseObject(cacheDraft, Draft.class);
            WikiEntry wikiEntry = wikiEntryConverter.toEntity(form);
            WikiEntryVersion versionEntity = wikiEntryConverter.toVersionEntity(wikiEntry);
            String draftDataFiled = JSON.toJSONString(versionEntity);
            draft.setData(draftDataFiled);
            redisTemplate.opsForValue().set(key, JSON.toJSONString(draft));
        }

    }
    
    
}
