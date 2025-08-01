package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Table;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;

@Data 
@Table("user_role")
public class UserRole implements Serializable {

    private Long userId;

    private Long roleId;
}