package org.rookie.business.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rookie.business.feign.WikiEntryFeignClient;
import org.rookie.business.service.WikiEntryService;
import org.rookie.consts.Result;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.DraftSubmitConflictDTO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikiEntryServiceImpl implements WikiEntryService {

    private final WikiEntryFeignClient wikiEntryFeignClient;

    private final KafkaTemplate<String, WikiEntryForm> kafkaTemplate;

    @Override
    public WikiEntry createWikiEntry(WikiEntryForm form) {
        long uid = Long.parseLong(StpUtil.getLoginId().toString());
        form.setCreatedBy(uid);
        Result<WikiEntry> result = wikiEntryFeignClient.createWikiEntry(form);
        return result.getData();
    }

    @Override
    public PageResult<WikiEntryBO> queryWikiEntry(WikiEntryPageQuery query) {

        Result<PageResult<WikiEntryBO>> result = wikiEntryFeignClient.queryWikiEntry(query);
        return result.getData();
    }

    @Override
    public WikiEntryDetailDTO getWikiEntryDetailById(Long id) {
        Result<WikiEntryDetailDTO> result = wikiEntryFeignClient.getWikiEntryById(id);
        return result.getData();
    }

    @Override
    public Boolean deleteWikiEntry(Long id) {
        Result<Boolean> result = wikiEntryFeignClient.deleteWikiEntry(id);
        return result.getData();
    }


    @Override
    public Boolean submitNewEntryVersion(WikiEntryForm form) {
        long uid = Long.parseLong(StpUtil.getLoginId().toString());
        form.setCreatedBy(uid);
        Result<Boolean> result = wikiEntryFeignClient.submitNewEntryVersion(form.getId(),form);
        return result.getData();
    }

    @Override
    public List<WikiEntryVersion> queryWikiEntryVersion(Long id) {
        Result<List<WikiEntryVersion>> result = wikiEntryFeignClient.queryWikiEntryVersion(id);
        return result.getData();
    }

    @Override
    public WikiEntryDetailDTO getWikiEntryWithVersion(Long id, Integer versionNumber) {
        Result<WikiEntryDetailDTO> result = wikiEntryFeignClient.getWikiEntryWithVersion(id, versionNumber);
        return result.getData();
    }

    @Override
    public Boolean forceDeleteWikiEntry(Long id) {
        Result<Boolean> result = wikiEntryFeignClient.forceDeleteWikiEntry(id);
        return result.getData();
    }

    @Override
    public CompletableFuture<Boolean> autoSaveDraft(Long id, WikiEntryForm form) {
        String topic = "draft-entry";
        String key=id.toString();

        CompletableFuture<SendResult<String, WikiEntryForm>> future = kafkaTemplate.send(topic, key, form);
        
        return CompletableFuture.supplyAsync(()->{
            try {
                future.get();
                return true;
            }catch (Exception e){
                log.error("发送wikiEntry草稿更新消息失败: {}", id, e);
                return false;
            }
        });
    }

    @Override
    public DraftSubmitConflictDTO<WikiEntryDetailDTO> submitDraftAsNewVersion(Long draftId) {
        Result<DraftSubmitConflictDTO<WikiEntryDetailDTO>> result = wikiEntryFeignClient.submitDraftAsNewVersion(draftId);
        return result.getData();
    }
}
