package org.rookie.model.entity.database;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table("wiki_category")
public class WikiCategory implements Serializable {

    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private String name;

    private Long parentId;

    private String description;

    private Integer sortOrder;

    @Column(onInsertValue = "now()")

    private LocalDateTime createdAt;

    @Column(onInsertValue = "now()",onUpdateValue = "now()")
    private LocalDateTime updatedAt;

    @RelationManyToOne(selfField = "parentId",targetField = "id",targetTable = "wiki_category")
    private WikiCategory parent;

    @RelationOneToMany(selfField ="id" ,targetField = "parentId",targetTable = "wiki_category")
    private List<WikiCategory> children;
}
