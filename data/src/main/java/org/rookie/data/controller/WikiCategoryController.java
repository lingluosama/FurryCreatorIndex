package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.data.service.IWikiCategoryService;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.WikiCategoryForm;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data/wiki-categories")
@RequiredArgsConstructor
public class WikiCategoryController {

    private final IWikiCategoryService wikiCategoryService;


    @PostMapping
    Result<WikiCategory> createWikiCategory(@RequestBody WikiCategoryForm form) {
        WikiCategory category = wikiCategoryService.createNewCategory(form);
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
        Boolean b = wikiCategoryService.deleteCategory(id);
        if(b){
            return Result.success();
        }else{
            return Result.failed("记录删除失败");
        }

    }

}
