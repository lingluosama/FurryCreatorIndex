package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // 自动生成 Getter, Setter, equals, hashCode, toString
@Table("wiki_entry_version")
public class WikiEntryVersion implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long wikiEntryId;

    private Integer versionNumber;

    private String content;

    private Long createdBy;

    private LocalDateTime createdAt;

    private String comment;
}