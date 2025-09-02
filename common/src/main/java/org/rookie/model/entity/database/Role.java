package org.rookie.model.entity.database;

import com.mybatisflex.annotation.*;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data // 自动生成 Getter, Setter, equals, hashCode, toString
@Table("role")
public class Role implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    @RelationManyToMany(
            joinTable = "role_permission",
            selfField = "id",joinSelfColumn = "role_id",
            targetField = "id",joinTargetColumn = "permission_id"
    )
    private List<Permission> permissions;

}