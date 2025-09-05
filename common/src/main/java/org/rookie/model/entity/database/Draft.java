package org.rookie.model.entity.database;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table("draft")
public class Draft {
    
    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;
    
    private String type;
    
    private Long entityId;
    
    //json化数据
    private String data;
    
    private Long createdBy;
    
    @Column(onInsertValue = "now()")
    private LocalDateTime createdAt;
    
}
