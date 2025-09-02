package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;

@Data 
@Table("role_permission")
@AllArgsConstructor
public class RolePermission implements Serializable {

    // 联合主键，MyBatis-Flex 会自动识别
    private Long roleId;

    private Long permissionId;
}