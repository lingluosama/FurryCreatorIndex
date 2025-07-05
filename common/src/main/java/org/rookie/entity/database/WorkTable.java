package org.rookie.entity.database;

import com.mybatisflex.annotation.Id;import com.mybatisflex.annotation.KeyType;import com.mybatisflex.annotation.Table;import com.mybatisflex.core.keygen.KeyGenerators;import org.rookie.consts.WorkType;

@Table("work")
public class WorkTable {
        @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
        private Long id;
        
        private Long cid;
        
        private String cover;
        
        private String title;
        
        private String link;
        
        private WorkType type;
        
        private String description;
        
        private String publish_time;
        
        private String language;
    
}
