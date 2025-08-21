package org.rookie.business.service;


import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.WikiCategoryForm;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface WikiCategoryService {

    WikiCategory createWikiCategory(WikiCategoryForm form);

    CategoryDetailDTO getCategoryDetailAndChildrenById(Long id);

    List<CategoryDetailDTO> getAllCategory();

    Boolean deleteWikiCategory(Long id);
}
