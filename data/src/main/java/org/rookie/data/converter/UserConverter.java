package org.rookie.data.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.rookie.data.model.form.UserRegisterForm;
import org.rookie.model.entity.database.User;

@Mapper(componentModel = "spring") // componentModel="spring" 让 MapStruct 生成的类作为 Spring Bean
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    // 定义转换规则
    @Mapping(target = "passwordHash", source = "password") // 将 form 的 password 映射到实体的 passwordHash
    @Mapping(target = "registrationIp", source = "ip")     // 将 form 的 ip 映射到实体的 registrationIp
    @Mapping(target = "username", source = "userName")     // 将 form 的 userName 映射到实体的 username
    @Mapping(target = "nickname", source = "nickName")     // 将 form 的  nickName 映射到实体的 nickname
    User toUser(UserRegisterForm form);
}