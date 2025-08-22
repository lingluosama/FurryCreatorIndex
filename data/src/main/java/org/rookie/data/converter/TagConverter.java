package org.rookie.data.converter;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rookie.model.entity.database.Tag;
import org.rookie.model.form.TagForm;

@Mapper(componentModel = "spring")
public interface TagConverter {
    TagConverter INSTANCE = Mappers.getMapper(TagConverter.class);

    TagForm toTagForm(Tag tag);

    Tag toTag(TagForm tag);


}
