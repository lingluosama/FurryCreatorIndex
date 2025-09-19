package org.rookie.data.service;

import com.mybatisflex.core.service.IService;
import org.rookie.annotation.RedisCache;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.WikiCategoryForm;

import java.util.List;

public interface IWikiCategoryService extends IService<WikiCategory> {

    WikiCategory createNewCategory(WikiCategoryForm form);
    
    @RedisCache(key = "CategoryDetail:all",expire = 168)
    List<CategoryDetailDTO> getAllCategory();

    @RedisCache(key = "CategoryDetail:%s",expire = 168)
    CategoryDetailDTO getCategoryDetailAndChildrenById(Long id);

    Boolean deleteCategory(Long id);

}