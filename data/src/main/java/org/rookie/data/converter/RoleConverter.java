package org.rookie.data.converter;


import org.mapstruct.Mapper;
import org.rookie.model.entity.database.Role;
import org.rookie.model.form.RoleForm;

@Mapper(componentModel = "spring")
public interface RoleConverter {

    Role formToRole(RoleForm form);


}
