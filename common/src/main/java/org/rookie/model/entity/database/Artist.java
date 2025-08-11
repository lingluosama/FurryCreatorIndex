package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // 自动生成 Getter, Setter, equals, hashCode, toString
@Table("artist")
public class Artist implements Serializable {

    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private String name;

    private String bio;

    private String avatarUrl;

    private String artStyle;

    // JSON 字段
    private String socialLinks; // 存储 JSON 字符串

    private String websiteUrl;

    private String status; // 例如 ACTIVE, INACTIVE, SUSPENDED

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
