package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * &#064;description  漫画小说实体类
 */
@Data // 自动生成 Getter, Setter, equals, hashCode, toString
@Table("literature_work")
public class LiteratureWork implements Serializable {

    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private String title;

    private String type; // 例如 COMIC, NOVEL, WEBTOON

    private Long authorId;

    private Long illustratorId;

    private Long publisherId;

    private LocalDate releaseDate;

    private String description;

    private String coverImageUrl;

    private String officialWebsiteUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
