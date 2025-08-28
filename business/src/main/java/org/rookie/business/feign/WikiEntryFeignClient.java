package org.rookie.business.feign;

import org.apache.ibatis.annotations.Delete;
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

@FeignClient(name = "data-service", path = "/data", configuration = FeignConfig.class)
public interface WikiEntryFeignClient {

    @PostMapping("/wiki-entries")
    Result<WikiEntry> createWikiEntry(@RequestBody WikiEntryForm form);

    @RequestMapping(value = "/wiki-entries/query",method = RequestMethod.POST)
    Result<PageResult<WikiEntryBO>> queryWikiEntry(@RequestBody WikiEntryPageQuery query);

    @GetMapping("/wiki-entries/{id}")
    Result<WikiEntryDetailDTO> getWikiEntryById(@PathVariable("id") Long id);

    @DeleteMapping("/wiki-entries/{id}")
    Result<Boolean> deleteWikiEntry(@PathVariable("id") Long id);


    @PostMapping("/wiki-entries/{id}/versions")
    Result<Boolean> submitNewEntryVersion(@PathVariable("id") Long id, @RequestBody WikiEntryForm form);

    @GetMapping("/wiki-entries/{id}/versions")
    Result<List<WikiEntryVersion>> queryWikiEntryVersion(@PathVariable("id") Long id);

    @GetMapping("/wiki-entries/{id}/versions/{versionNumber}")
    Result<WikiEntryDetailDTO> getWikiEntryWithVersion(
            @PathVariable("id") Long id,
            @PathVariable("versionNumber") Integer versionNumber);

    @PutMapping("/wiki-entries/{id}/versions/{versionNumber}")
    Result<Boolean> publishWikiEntryVersion(
            @PathVariable("id") Long id,
            @PathVariable("versionNumber") Integer versionNumber);

    @DeleteMapping("/wiki-entries/{id}/force")
    Result<Boolean> forceDeleteWikiEntry(@PathVariable("id") Long id);

}
