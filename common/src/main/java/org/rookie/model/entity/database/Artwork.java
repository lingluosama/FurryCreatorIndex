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
@Table("artwork")
public class Artwork implements Serializable {

    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private String title;

    private Long artistId;

    private String description;

    // JSON 字段
    private String imageUrls; // 存储 JSON 字符串

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
