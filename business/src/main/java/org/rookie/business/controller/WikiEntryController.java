package org.rookie.business.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/wiki-entries")
@RequiredArgsConstructor
public class WikiEntryController {

    private final WikiEntryService wikiEntryService;

    @SaCheckPermission("wiki_entry:create")
    @PostMapping
    public Result<WikiEntry> createWikiEntry(WikiEntryForm form) {


        WikiEntry entry = wikiEntryService.createWikiEntry(form);

        return Result.success(entry);
    }
    @GetMapping("")
    public Result<PageResult<WikiEntryBO>> queryWikiEntry(WikiEntryPageQuery query){
        PageResult<WikiEntryBO> pageResult = wikiEntryService.queryWikiEntry(query);

        return Result.success(pageResult);

    }

    @GetMapping("/{id}")
    public Result<WikiEntryDetailDTO> getWikiEntryById(@PathVariable("id") Long id) {
        WikiEntryDetailDTO detailDTO = wikiEntryService.getWikiEntryDetailById(id);
        return Result.success(detailDTO);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteWikiEntry(@PathVariable("id") Long id) {
        Boolean result = wikiEntryService.deleteWikiEntry(id);
        if(result){
            return Result.success();
        }else{
            return Result.failed("删除失败");
        }
    }

    @SaCheckPermission("wiki_entry:update")
    @PostMapping("/{id}/versions")
    public Result<Boolean> submitNewEntryVersion(@PathVariable Long id,WikiEntryForm form) {

        long uid = Long.parseLong(StpUtil.getLoginId().toString());
        form.setUpdatedBy(uid);
        form.setId(id);

        Boolean result = wikiEntryService.submitNewEntryVersion(form);
        if(result){
            return Result.success();
        }else{
            return Result.failed("提交失败");
        }
    }

    @GetMapping("/{id}/versions")
    public Result<List<WikiEntryVersion>> queryWikiEntryVersion(@PathVariable("id") Long id) {
        List<WikiEntryVersion> versions = wikiEntryService.queryWikiEntryVersion(id);
        return Result.success(versions);
    }

    @GetMapping("/{id}/versions/{versionNumber}")
    public Result<WikiEntryDetailDTO> getWikiEntryWithVersion(
            @PathVariable("id") Long id,
            @PathVariable("versionNumber") Integer versionNumber) {
        WikiEntryDetailDTO detailDTO = wikiEntryService.getWikiEntryWithVersion(id, versionNumber);
        return Result.success(detailDTO);
    }

    @DeleteMapping("/{id}/force")
    public Result<Boolean> forceDeleteWikiEntry(@PathVariable("id") Long id) {
        Boolean result = wikiEntryService.forceDeleteWikiEntry(id);
        if(result){
            return Result.success();
        }else{
            return Result.failed("删除失败");
        }
    }
    
    @SaCheckLogin
    @PostMapping("/{id}/draft/autoSave")
    public Result<Boolean> autoSaveDraft(@PathVariable("id") Long id, @RequestBody WikiEntryForm form) { 
        form.setId(id);
        long uid = Long.parseLong(StpUtil.getLoginId().toString());
        form.setUpdatedBy(uid);
        CompletableFuture<Boolean> future = wikiEntryService.autoSaveDraft(id, form);
        return Result.success(future.join());
    }
    
    @PostMapping("/{id}/draft/draft-submit")
    public Result<DraftSubmitConflictDTO<WikiEntryDetailDTO>> submitDraft(@PathVariable("id")Long draftId){
        DraftSubmitConflictDTO<WikiEntryDetailDTO> dto = wikiEntryService.submitDraftAsNewVersion(draftId);
        return Result.success(dto);
    }




}
