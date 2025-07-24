package org.rookie.model.entity.database;


import com.mybatisflex.annotation.Id;import com.mybatisflex.annotation.KeyType;import com.mybatisflex.annotation.Table;import com.mybatisflex.core.keygen.KeyGenerators;import lombok.Data;import org.rookie.consts.CreatorType;


@Table("creator")
@Data
public class CreatorTable {
    
    
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;
    
    private CreatorType type;
    
    private String name;
    
    private String twitter;
    
    private String bli;
    
    private String pixiv;
    
    private String youtube;
    
    private String github;
    
    private String avatar;
    
    private String information;
    
    
    
}
