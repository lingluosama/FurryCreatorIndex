package org.rookie.model.message;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RedisAutoIncrementMessage {
    String tableName;
    
    String fildName;
    
    Integer increment;
    
    Long primaryKey;
    
}
