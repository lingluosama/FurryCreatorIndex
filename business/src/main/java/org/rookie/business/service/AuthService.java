package org.rookie.business.service;

import org.rookie.model.dto.AuthDTO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.RolePermissionSearchDTO;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.entity.database.Role;
import org.rookie.model.form.RoleForm;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.model.query.PermissionQuery;
import org.rookie.model.query.RolePageQuery;

import java.util.List;

public interface AuthService {

    AuthDTO userRegister(UserRegisterForm form);

    AuthDTO userLogin(UserLoginForm form);

    Void createRole(RoleForm form);

    RolePermissionSearchDTO queryRole(RolePageQuery query);

    Void deleteRole(Long roleId);

    Void updateRolePermission(List<Long> permissionIds, Long roleId);

    Void updateUserRole(List<Long>roleIds,Long userId);

    Void createPermission(RoleForm form);

    PageResult<Permission> searchPermission(PermissionQuery query);

    Void deletePermission(Long permissionId);

}
