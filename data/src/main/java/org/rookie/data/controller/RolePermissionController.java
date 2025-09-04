package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.data.service.IPermissionService;
import org.rookie.data.service.IRoleService;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.RolePermissionSearchDTO;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.entity.database.Role;
import org.rookie.model.form.PermissionForm;
import org.rookie.model.form.RoleForm;
import org.rookie.model.query.PermissionQuery;
import org.rookie.model.query.RolePageQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class RolePermissionController {
    private  final IRoleService roleService;
    private  final IPermissionService permissionService;

    @PostMapping("/role")
    Result<Boolean> createRole(RoleForm form){
        Boolean success = roleService.createRole(form);
        return success?Result.success(true):Result.failed("创建失败");
    }

    @PostMapping("/role/query")
    Result<RolePermissionSearchDTO> queryRole(RolePageQuery query){
        RolePermissionSearchDTO permissionSearchDTO = roleService.searchRoleWithPermission(
                query.getKeyword(),
                query.getPageNumber(),
                query.getPageSize());

        return Result.success(permissionSearchDTO);
    }

    @DeleteMapping("/role/{id}")
    Result<Boolean> deleteRole(@PathVariable("id") Long roleId){
        boolean deleteCount = roleService.removeById(roleId);
        return deleteCount?Result.success(true):Result.failed("删除失败");
    }

    @PutMapping("/role/{id}")
    Result<Boolean> updateRolePermission(@RequestBody List<Long> permissionIds, @PathVariable("id") Long roleId){
        boolean updateCount = permissionService.updateRolePermission(permissionIds, roleId);
        return updateCount?Result.success(true):Result.failed("更新失败");
    }

    @PutMapping("/users/{userId}/roles")
    Result<Boolean> updateUserRole(@RequestBody List<Long> roleIds, @PathVariable("userId") Long userId){
        boolean updateCount = roleService.updateUserRole(roleIds, userId);
        return updateCount?Result.success(true):Result.failed("更新失败");
    }

    @PostMapping("/permission")
    Result<Boolean> createPermission(@RequestBody PermissionForm form){
        Boolean success = permissionService.createPermission(form);
        return success?Result.success(true):Result.failed("创建失败");
    }

    @PostMapping("/permissions/query")
    Result<PageResult<Permission>> searchPermission(PermissionQuery query){
        PageResult<Permission> permissionPageResult = permissionService.searchPermission(query);
        return Result.success(permissionPageResult);
    }

    @DeleteMapping("/permission/{id}")
    Result<Boolean> deletePermission(@PathVariable("id")Long id){
        boolean removed = permissionService.removeById(id);
        return removed?Result.success(true):Result.failed("删除失败");

    }






}
