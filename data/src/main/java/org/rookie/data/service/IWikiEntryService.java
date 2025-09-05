package org.rookie.data.service;

import com.mybatisflex.core.service.IService;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.DraftSubmitConflictDTO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;

import java.util.List;

public interface IWikiEntryService extends IService<WikiEntry> {

    WikiEntry createWikiEntry(WikiEntryForm form);

    PageResult<WikiEntryBO> queryWikiEntry(WikiEntryPageQuery query);

    WikiEntryDetailDTO getWikiEntryById(Long id);

    Boolean deleteWikiEntry(Long id);

    Boolean submitNewEntryVersion(WikiEntryForm form);

    WikiEntryDetailDTO getWikiEntryWithVersion(Long id,Integer version);

    List<WikiEntryVersion> queryWikiEntryVersion(Long id);

    Boolean forceDeleteWikiEntry(Long id);
    
    DraftSubmitConflictDTO<WikiEntryDetailDTO> submitDraftAsNewVersion(Long draftId);
    
}
