package org.rookie.business.service.impl;


import lombok.RequiredArgsConstructor;
import org.rookie.business.service.AuthService;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.entity.database.Role;
import org.rookie.model.form.RoleForm;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.model.query.PermissionQuery;
import org.rookie.model.query.RolePageQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthDTO userRegister(UserRegisterForm form) {
        return null;
    }

    @Override
    public AuthDTO userLogin(UserLoginForm form) {
        return null;
    }

    @Override
    public Boolean createRole(RoleForm form) {
        return null;
    }

    @Override
    public PageResult<Role> queryRole(RolePageQuery query) {
        return null;
    }

    @Override
    public Long deleteRole(Long roleId) {
        return 0L;
    }

    @Override
    public Boolean updateRolePermission(List<Long> permissionIds, Long roleId) {
        return null;
    }

    @Override
    public Boolean updateUserRole(List<Long> roleIds, Long userId) {
        return null;
    }

    @Override
    public Boolean removeUserRole(Long userId) {
        return null;
    }

    @Override
    public Boolean createPermission(RoleForm form) {
        return null;
    }

    @Override
    public PageResult<Permission> searchPermission(PermissionQuery query) {
        return null;
    }

    @Override
    public Boolean deletePermission(Long permissionId) {
        return null;
    }
}
