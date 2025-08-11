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
@Table("creator")
public class Creator implements Serializable {

    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private String name;

    private String bio;

    private String avatarUrl;

    // JSON 字段，需要配置 MyBatis-Flex 的类型处理器或者手动序列化/反序列化
    private String socialLinks; // 存储 JSON 字符串

    private String websiteUrl;

    private String status; // 例如 ACTIVE, INACTIVE, SUSPENDED

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
