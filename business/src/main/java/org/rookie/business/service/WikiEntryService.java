package org.rookie.business.service;

import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;

import java.util.List;

public interface WikiEntryService {

    WikiEntry createWikiEntry(WikiEntryForm form);

    PageResult<WikiEntryBO> queryWikiEntry(WikiEntryPageQuery query);

    WikiEntryDetailDTO getWikiEntryDetailById(Long id);

    Boolean deleteWikiEntry(Long id);


    Boolean submitNewEntryVersion(WikiEntryForm form);

    List<WikiEntryVersion> queryWikiEntryVersion(Long id);

    WikiEntryDetailDTO getWikiEntryWithVersion(Long id, Integer versionNumber);

}
