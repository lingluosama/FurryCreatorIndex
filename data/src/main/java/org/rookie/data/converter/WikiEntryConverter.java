package org.rookie.data.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.rookie.model.bo.WikiEntryBO;
import org.rookie.model.dto.WikiEntryDetailDTO;
import org.rookie.model.entity.database.WikiEntry;
import org.rookie.model.entity.database.WikiEntryVersion;
import org.rookie.model.entity.elastic.WikiEntryEs;
import org.rookie.model.form.WikiEntryForm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring",uses = MapperUtils.class)
public interface WikiEntryConverter {
    WikiEntryConverter INSTANCE = Mappers.getMapper(WikiEntryConverter.class);


    WikiEntry toEntity(WikiEntryForm form);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "longToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "longToLocalDateTime")
    WikiEntryBO toBO(WikiEntryEs entity);

    List<WikiEntryBO> toBOList(List<WikiEntryEs> entityList);

    @Mapping(target = "creator", source = "creator.username")
    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorAvatarUrl", source = "creator.avatarUrl")
    @Mapping(target = "updater", source = "updater.username")
    @Mapping(target = "updaterId", source = "updater.id")
    @Mapping(target = "updaterAvatarUrl", source = "updater.avatarUrl")
    @Mapping(target = "category",source = "category.name")
    @Mapping(target = "createdAt", source = "createdAt") // 显式映射
    @Mapping(target = "updatedAt", source = "updatedAt") // 显式映射
    WikiEntryDetailDTO toDetailDTO(WikiEntry entity);

    @Mapping(target = "wikiEntryId",source = "id")
    WikiEntryVersion toVersionEntity(WikiEntry entity);
    
    @Mapping(target = "id",source = "wikiEntryId")
    WikiEntryForm versionToForm(WikiEntryVersion entity);

}
