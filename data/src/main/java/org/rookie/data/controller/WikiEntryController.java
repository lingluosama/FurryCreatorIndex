package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.data.service.IWikiEntryService;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data/wiki-entries")
@RequiredArgsConstructor
public class WikiEntryController {

    private final IWikiEntryService wikiEntryService;

    @PostMapping
    Result<WikiEntry> createWikiEntry(WikiEntryForm form) {
        return Result.success(wikiEntryService.createWikiEntry(form));
    }

    @GetMapping
    Result<PageResult<WikiEntryBO>> queryWikiEntry(WikiEntryPageQuery query) {
        return Result.success(wikiEntryService.queryWikiEntry(query));
    }

    @GetMapping("/{id}")
    Result<WikiEntryDetailDTO> getWikiEntryById(@PathVariable Long id) {
        return Result.success(wikiEntryService.getWikiEntryById(id));
    }

    @DeleteMapping("/{id}")
    Result<Boolean> deleteWikiEntry(@PathVariable Long id) {
        return Result.success(wikiEntryService.deleteWikiEntry(id));
    }

    @PostMapping("/{id}/versions")
    Result<Boolean> submitNewEntryVersion(WikiEntryForm form) {
        return Result.success(wikiEntryService.submitNewEntryVersion(form));
    }



}
