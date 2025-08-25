package org.rookie.model.entity.database;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data // 自动生成 Getter, Setter, equals, hashCode, toString
@Table("comment")
public class Comment implements Serializable {

    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private Long entityId;

    private String entityType; // 例如 WIKI_ENTRY, GAME

    private Long userId;

    private Long parentId;

    private String content;

    private String status; // 例如 APPROVED, PENDING, SPAM

    @Column(onInsertValue = "now()")
    private LocalDateTime createdAt;

    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updatedAt;

    @RelationOneToMany(selfField ="id",targetField = "parentId",targetTable = "comment")
    private List<Comment> children;

    @RelationOneToOne(selfField = "userId",targetField = "id",targetTable = "user",selectColumns ={"username","avatar_url"})
    private User user;

}
