package org.rookie.data.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.entity.elastic.WikiEntryEs;
import org.rookie.model.form.WikiEntryForm;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WikiEntryConverter {
    WikiEntryConverter INSTANCE = Mappers.getMapper(WikiEntryConverter.class);


    WikiEntry toEntity(WikiEntryForm form);

    WikiEntryBO toBO(WikiEntryEs entity);

    List<WikiEntryBO> toBOList(List<WikiEntryEs> entityList);

    @Mapping(target = "creator", source = "creator.username")
    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorAvatarUrl", source = "creator.avatar_url")
    @Mapping(target = "updater", source = "updater.username")
    @Mapping(target = "updaterId", source = "updater.id")
    @Mapping(target = "updaterAvatarUrl", source = "updater.avatar_url")
    @Mapping(target = "category",source = "category.name")
    WikiEntryDetailDTO toDetailDTO(WikiEntry entity);

    @Mapping(target = "wikiEntryId",source = "id")
    WikiEntryVersion toVersionEntity(WikiEntry entity);

}
