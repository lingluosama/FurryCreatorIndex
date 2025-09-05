package org.rookie.data.service.impl;

import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.rookie.data.converter.WikiEntryConverter;
import org.rookie.data.mapper.DraftMapper;
import org.rookie.data.service.IDraftService;
import org.rookie.model.entity.database.Draft;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.form.WikiEntryForm;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DraftServiceImpl extends ServiceImpl<DraftMapper, Draft> implements IDraftService {
    
    private final WikiEntryConverter wikiEntryConverter;
    

    @Override
    public Draft saveDraft(WikiEntryForm form) {
        WikiEntry wikiEntry = wikiEntryConverter.toEntity(form);
        WikiEntryVersion versionEntity = wikiEntryConverter.toVersionEntity(wikiEntry);
        String data = JSON.toJSONString(versionEntity);
        Draft draft = new Draft();
        draft.setType("wiki_entry");
        draft.setEntityId(wikiEntry.getId());
        draft.setData(data);
        draft.setCreatedBy(form.getCreatedBy());
        
        //TODO:检查存在


        boolean saved = this.save(draft);
        return draft;
    }
}
