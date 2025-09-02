package org.rookie.data.converter;


import org.mapstruct.Mapper;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.form.PermissionForm;

@Mapper(componentModel = "spring")
public interface PermissionConverter {

    Permission formToPermission(PermissionForm form);
}
