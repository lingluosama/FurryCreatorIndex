package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data; // 引入 Lombok 的 Data 注解
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data 
@Table("user_role")
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {

    private Long userId;

    private Long roleId;
}