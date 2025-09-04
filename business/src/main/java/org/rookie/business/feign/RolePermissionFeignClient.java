package org.rookie.business.feign;

import org.rookie.business.config.FeignConfig;
import org.rookie.consts.Result;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.RolePermissionSearchDTO;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.form.RoleForm;
import org.rookie.model.query.PermissionQuery;
import org.rookie.model.query.RolePageQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "data-service",path = "/data",configuration = FeignConfig.class)
public interface RolePermissionFeignClient {

    @PostMapping("/role")
    Result<Boolean> createRole(@RequestBody RoleForm form);

    @PostMapping("/role/query")
    Result<RolePermissionSearchDTO> searchRoleWithPermission(@RequestBody RolePageQuery query);

    @DeleteMapping("/role/{id}")
    Result<Boolean> deleteRole(@PathVariable("id") Long id);

    @PutMapping("/role/{id}")
    Result<Boolean> updateRolePermission(@PathVariable("id") Long id, @RequestBody Long[] permissionIds);

    @PutMapping("/users/{userId}/roles")
    Result<Boolean> updateUserRole(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds);

    @PostMapping("/permission")
    Result<Boolean> createPermission(@RequestBody RoleForm form);

    @PostMapping("/permission/query")
    Result<PageResult<Permission>> searchPermission(@RequestBody PermissionQuery query);

    @DeleteMapping("/permission/{id}")
    Result<Boolean> deletePermission(@PathVariable("id") Long id);

}
