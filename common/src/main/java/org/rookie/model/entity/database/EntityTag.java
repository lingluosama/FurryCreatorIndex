package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Table;
import lombok.Data; // 引入 Lombok 的 Data 注解

import java.io.Serializable;

@Data 
@Table("entity_tag")
public class EntityTag implements Serializable {

    // 联合主键，MyBatis-Flex 会自动识别
    private Long entityId;

    private String entityType; // 例如 WIKI_ENTRY, CREATOR, WORK

    private Long tagId;
}