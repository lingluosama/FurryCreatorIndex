package org.rookie.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.entity.database.Role;

import java.util.List;

@Data
@NoArgsConstructor
public class RolePermissionBO {
    Role role;
    List<Permission> permissions;
}
