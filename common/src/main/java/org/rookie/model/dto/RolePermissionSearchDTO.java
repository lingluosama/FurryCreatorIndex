package org.rookie.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rookie.model.bo.RolePermissionBO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionSearchDTO {
    Long count;
    List<RolePermissionBO> rolePermissions;
}
