package org.rookie.business.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import lombok.RequiredArgsConstructor;
import org.rookie.business.service.WikiCategoryService;
import org.rookie.consts.Result;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.WikiCategoryForm;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wiki-categories")
@RequiredArgsConstructor
public class WikiCategoryController {

    private final WikiCategoryService wikiCategoryService;

    @SaCheckRole("admin")
    @PostMapping
    Result<WikiCategory> createWikiCategory(WikiCategoryForm form) {
        WikiCategory category = wikiCategoryService.createWikiCategory(form);
        return Result.success(category);
    }

    @GetMapping
    Result<List<CategoryDetailDTO>> getAllCategory() {
        List<CategoryDetailDTO> allCategory = wikiCategoryService.getAllCategory();
        return Result.success(allCategory);
    }

    @GetMapping("/{id}")
    Result<CategoryDetailDTO> getCategoryDetailAndChildrenById(@PathVariable Long id) {
        CategoryDetailDTO dto = wikiCategoryService.getCategoryDetailAndChildrenById(id);
        return Result.success(dto);
    }

    @DeleteMapping("/{id}")
    Result<Void> deleteCategory(@PathVariable Long id) {
        Boolean b = wikiCategoryService.deleteWikiCategory(id);
        if(b){
            return Result.success();
        }else{
            return Result.failed("记录删除失败");
        }

    }

}
