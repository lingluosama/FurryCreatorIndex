package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // 自动生成 Getter, Setter, equals, hashCode, toString
@Table("report")
public class Report implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long userId;

    private Long entityId;

    private String entityType;

    private String reason;

    private String description;

    private String status; // 例如 PENDING, REVIEWED, RESOLVED

    private Long handledBy;

    private LocalDateTime handledAt;

    private LocalDateTime createdAt;
}