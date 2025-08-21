package org.rookie.data.converter;


import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.rookie.model.dto.CategoryDetailDTO;
import org.rookie.model.entity.database.User;
import org.rookie.model.entity.database.WikiCategory;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.model.form.WikiCategoryForm;

@Mapper(componentModel = "spring")
public interface WikiCategoryConverter {
    WikiCategoryConverter INSTANCE = Mappers.getMapper(WikiCategoryConverter.class);

    WikiCategory toWikiCategory(WikiCategoryForm form);

    /**
     * 制定处理parent字段，避免递归转换
     */
    @Mapping(target = "parent", qualifiedByName = "convertParentCategory")
    @Mapping(target = "children", qualifiedByName = "convertChildrenCategory")
    CategoryDetailDTO toCategoryDetailDTO(WikiCategory wikiCategory);

    /**
     * 转换父分类，并忽略其子分类。
     */
    @Named("convertParentCategory")
    @Mapping(target = "children", ignore = true)
    CategoryDetailDTO toCategoryDetailDTOWithoutChildren(WikiCategory wikiCategory);

    /**
     * 转换子分类，并忽略其父分类。
     */
    @Named("convertChildrenCategory")
    @Mapping(target = "parent", ignore = true)
    CategoryDetailDTO toCategoryDetailDTOWithoutParent(WikiCategory wikiCategory);

}
