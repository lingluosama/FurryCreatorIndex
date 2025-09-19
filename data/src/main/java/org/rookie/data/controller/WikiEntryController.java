package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rookie.consts.Result;
import org.rookie.data.service.IWikiEntryService;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.DraftSubmitConflictDTO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/data/wiki-entries")
@RequiredArgsConstructor
public class WikiEntryController {

    private final IWikiEntryService wikiEntryService;

    @PostMapping("")
    Result<WikiEntry> createWikiEntry(@RequestBody WikiEntryForm form) {
        return Result.success(wikiEntryService.createWikiEntry(form));
    }

    @PostMapping("/query")
    Result<PageResult<WikiEntryBO>> queryWikiEntry(@RequestBody WikiEntryPageQuery query) {
        return Result.success(wikiEntryService.queryWikiEntry(query));
    }

    @GetMapping("/{id}")
    Result<WikiEntryDetailDTO> getWikiEntryById(@PathVariable Long id) {
        return Result.success(wikiEntryService.getWikiEntryById(id));
    }

    @DeleteMapping("/{id}")
    Result<Boolean> deleteWikiEntry(@PathVariable Long id) {
        return Result.success(wikiEntryService.deleteWikiEntry("WikiEntry:"+id.toString(),id));
    }

    @PostMapping("/{id}/versions")
    Result<Boolean> submitNewEntryVersion(@RequestBody WikiEntryForm form) {
        return Result.success(wikiEntryService.submitNewEntryVersion("WkiEntry:"+form.getId().toString(),form));
    }

    @GetMapping("/{id}/versions")
    Result<List<WikiEntryVersion>> queryWikiEntryVersion(@PathVariable Long id) {
        return Result.success(wikiEntryService.queryWikiEntryVersion(id));
    }

    @GetMapping("/{id}/versions/{versionNumber}")
    Result<WikiEntryDetailDTO> getWikiEntryWithVersion(@PathVariable Long id, @PathVariable Integer versionNumber) {
        return Result.success(wikiEntryService.getWikiEntryWithVersion(id, versionNumber));
    }

    @DeleteMapping("/{id}/force")
    Result<Boolean> forceDeleteWikiEntry(@PathVariable Long id) {
           return Result.success(wikiEntryService.forceDeleteWikiEntry(id));
    }
    
    @PostMapping("/{draftId}/draft-submit")
    Result<DraftSubmitConflictDTO<WikiEntryDetailDTO>> submitDraftAsNewVersion(@PathVariable Long draftId) {
        return Result.success(wikiEntryService.submitDraftAsNewVersion(draftId));
    }




}
