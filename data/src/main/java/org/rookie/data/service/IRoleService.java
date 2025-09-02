package org.rookie.data.service;

import com.mybatisflex.core.service.IService;
import org.rookie.model.dto.RolePermissionSearchDTO;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.entity.database.Role;
import org.rookie.model.form.RoleForm;

import java.util.List;

public interface IRoleService extends IService<Role> {
    
    List<String> getUserRoles(Long uid);
    
    RolePermissionSearchDTO searchRoleWithPermission(String keyword, Integer offset, Integer limit);
    
    
    List<Permission> getRolePermissions(Long roleId);
    
    Void setRolePermissionToRedis();
    
    Boolean addRoleForUser(Long uid, Long roleId);

    Boolean createRole(RoleForm form);

    Boolean updateUserRole(List<Long>roleIds,Long userId);



}
