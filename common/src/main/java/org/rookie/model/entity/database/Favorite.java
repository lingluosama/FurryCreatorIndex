package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // 自动生成 Getter, Setter, equals, hashCode, toString
@Table("favorite")
public class Favorite implements Serializable {

    // 联合主键，MyBatis-Flex 会自动识别
    private Long userId;

    private Long entityId;

    private String entityType; // 例如 WIKI_ENTRY, GAME

    private LocalDateTime createdAt;
}