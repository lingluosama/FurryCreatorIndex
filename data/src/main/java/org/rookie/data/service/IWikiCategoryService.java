package org.rookie.data.service;

import com.mybatisflex.core.service.IService;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.WikiCategoryForm;

import java.util.List;

public interface IWikiCategoryService extends IService<WikiCategory> {

    WikiCategory createNewCategory(WikiCategoryForm form);

    List<CategoryDetailDTO> getAllCategory();

    CategoryDetailDTO getCategoryDetailAndChildrenById(Long id);

    Boolean deleteCategory(Long id);

}