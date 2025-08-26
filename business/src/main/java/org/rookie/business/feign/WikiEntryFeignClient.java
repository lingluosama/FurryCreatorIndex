package org.rookie.business.feign;

import org.rookie.business.config.FeignConfig;
import org.rookie.consts.Result;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.form.WikiEntryForm;
import org.rookie.model.query.WikiEntryPageQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "data-service", path = "/data/wiki-entries", configuration = FeignConfig.class)
public interface WikiEntryFeignClient {

    @PostMapping("")
    Result<WikiEntry> createWikiEntry(@RequestBody WikiEntryForm form);

    @PostMapping("/query")
    Result<PageResult<WikiEntryBO>> queryWikiEntry(@RequestBody WikiEntryPageQuery query);

    @GetMapping("/{id}")
    Result<WikiEntryDetailDTO> getWikiEntryById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    Result<Boolean> deleteWikiEntry(@PathVariable("id") Long id);


    @PostMapping("/{id}/versions")
    Result<Boolean> submitNewEntryVersion( @RequestBody WikiEntryForm form);

    @GetMapping("/{id}/versions")
    Result<List<WikiEntryVersion>> queryWikiEntryVersion(@PathVariable("id") Long id);

    @GetMapping("/{id}/versions/{versionNumber}")
    Result<WikiEntryDetailDTO> getWikiEntryWithVersion(
            @PathVariable("id") Long id,
            @PathVariable("versionNumber") Integer versionNumber);

    @PutMapping("/{id}/versions/{versionNumber}")
    Result<Boolean> publishWikiEntryVersion(
            @PathVariable("id") Long id,
            @PathVariable("versionNumber") Integer versionNumber);

}
