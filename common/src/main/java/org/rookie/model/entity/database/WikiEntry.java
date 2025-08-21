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
@Table("wiki_entry")
public class WikiEntry implements Serializable {

    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private String title;

    private String slug;

    private Long categoryId;

    private String content;

    private String coverImageUrl;

    private String status; // WikiEntryStatus 枚举，例如 DRAFT, PUBLISHED

    private Long viewCount;

    private Long createdBy;

    @Column(onInsertValue = "now()")
    private LocalDateTime createdAt;

    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updatedAt;

    private Long updatedBy;

    @Column(isLogicDelete = true) // 逻辑删除字段
    private Boolean isDeleted;
}
