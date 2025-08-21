package org.rookie.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.rookie.business.feign.WikiCategoryFeignClient;
import org.rookie.business.service.WikiCategoryService;
import org.rookie.consts.Result;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.WikiCategoryForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WikiCategoryServiceImpl implements WikiCategoryService {

    private final WikiCategoryFeignClient  wikiCategoryFeignClient;

    @Override
    public WikiCategory createWikiCategory(WikiCategoryForm form) {
        Result<WikiCategory> result = wikiCategoryFeignClient.createWikiCategory(form);
        return result.getData();
    }

    @Override
    public CategoryDetailDTO getCategoryDetailAndChildrenById(Long id) {
        Result<CategoryDetailDTO> result = wikiCategoryFeignClient.getCategoryDetailAndChildrenById(id);

        return result.getData();
    }

    @Override
    public List<CategoryDetailDTO> getAllCategory() {
        Result<List<CategoryDetailDTO>> result = wikiCategoryFeignClient.getAllCategory();
        return result.getData();
    }

    @Override
    public Boolean deleteWikiCategory(Long id) {
        Result<Void> result = wikiCategoryFeignClient.deleteCategory(id);
        return result.getCode() == HttpStatus.SC_OK;
    }
}
