package org.rookie.data.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.relation.RelationManager;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rookie.data.converter.WikiCategoryConverter;
import org.rookie.data.mapper.WikiCategoryMapper;
import org.rookie.data.service.IWikiCategoryService;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.entity.database.table.WikiCategoryTableDef;
import org.rookie.model.form.WikiCategoryForm;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.rookie.model.entity.database.table.WikiCategoryTableDef.WIKI_CATEGORY;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikiCategoryServiceImpl extends ServiceImpl<WikiCategoryMapper, WikiCategory> implements IWikiCategoryService {


    private final WikiCategoryConverter  categoryConverter;

    @Override
    public WikiCategory createNewCategory(WikiCategoryForm form) {
        WikiCategory wikiCategory = categoryConverter.toWikiCategory(form);
        this.save(wikiCategory);
        return wikiCategory;
    }

    @Override
    public List<CategoryDetailDTO> getAllCategory() {
        return this.queryChain().listAs(CategoryDetailDTO.class);
    }

    @Override
    public CategoryDetailDTO getCategoryDetailAndChildrenById(Long id) {
        WikiCategory wikiCategory = this.mapper.selectOneWithRelationsById(id);
        return categoryConverter.toCategoryDetailDTO(wikiCategory);

    }

    @Override
    public Boolean deleteCategory(Long id) {
        return this.removeById(id);
    }
}
