package org.rookie.business.feign;

import org.rookie.business.config.FeignConfig;
import org.rookie.consts.Result;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.WikiCategoryForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "data-service", path = "/data/wiki-categories", configuration = FeignConfig.class)
public interface WikiCategoryFeignClient {

    @PostMapping("")
    Result<WikiCategory> createWikiCategory(@RequestBody WikiCategoryForm form);

    @GetMapping("")
    Result<List<CategoryDetailDTO>> getAllCategory();

    @GetMapping("/{id}")
    Result<CategoryDetailDTO> getCategoryDetailAndChildrenById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    Result<Void> deleteCategory(@PathVariable("id") Long id);
}