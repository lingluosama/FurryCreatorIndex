package org.rookie.data.service;

import com.mybatisflex.core.service.IService;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.form.PermissionForm;
import org.rookie.model.form.RoleForm;
import org.rookie.model.query.PermissionQuery;

import java.util.List;

public interface IPermissionService extends IService<Permission> {

    Boolean createPermission(PermissionForm form);

    Boolean updateRolePermission(List<Long> permissionIds, Long roleId);

    PageResult<Permission> searchPermission(PermissionQuery query);



}
