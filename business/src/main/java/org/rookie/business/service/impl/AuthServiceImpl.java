package org.rookie.business.service.impl;


import lombok.RequiredArgsConstructor;
import org.rookie.business.feign.RolePermissionFeignClient;
import org.rookie.business.feign.UserFeignClient;
import org.rookie.business.service.AuthService;
import org.rookie.consts.Result;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RolePermissionFeignClient rolePermissionFeignClient;
    private final UserFeignClient userFeignClient;

    @Override
    public AuthDTO userRegister(UserRegisterForm form) {

        Result<AuthDTO> result = userFeignClient.register(form);
        return result.getData();
    }

    @Override
    public AuthDTO userLogin(UserLoginForm form) {
        Result<AuthDTO> result = userFeignClient.login(form);
        return result.getData();
    }

    @Override
    public Void createRole(RoleForm form) {
        Result<Boolean> role = rolePermissionFeignClient.createRole(form);
        return null;
    }

    @Override
    public RolePermissionSearchDTO queryRole(RolePageQuery query) {
        Result<RolePermissionSearchDTO> result = rolePermissionFeignClient.searchRoleWithPermission(query);
        return result.getData();
    }

    @Override
    public Void deleteRole(Long roleId) {
        Result<Boolean> result = rolePermissionFeignClient.deleteRole(roleId);
        return null;
    }

    @Override
    public Void updateRolePermission(List<Long> permissionIds, Long roleId) {
        Result<Boolean> result = rolePermissionFeignClient.updateRolePermission(roleId, (Long[]) permissionIds.toArray());
        return null;
    }

    @Override
    public Void updateUserRole(List<Long> roleIds, Long userId) {

        Result<Boolean> result = rolePermissionFeignClient.updateUserRole(userId, (Long[]) roleIds.toArray());
        return null;
    }

    @Override
    public Void createPermission(RoleForm form) {
        Result<Boolean> result = rolePermissionFeignClient.createPermission(form);
        return null;
    }

    @Override
    public PageResult<Permission> searchPermission(PermissionQuery query) {
        Result<PageResult<Permission>> result = rolePermissionFeignClient.searchPermission(query);
        return null;
    }

    @Override
    public Void deletePermission(Long permissionId) {
        Result<Boolean> result = rolePermissionFeignClient.deletePermission(permissionId);
        return null;
    }
}
