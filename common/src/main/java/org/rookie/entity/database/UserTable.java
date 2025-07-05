package org.rookie.entity.database;



import com.mybatisflex.annotation.ColumnMask;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.mybatisflex.core.mask.Masks;
import lombok.Data;

@Table("user")
@Data
public class UserTable {
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;
    private String name;
    private String password;
    private String salt;
    private String signature;
    private String avatar;
    @ColumnMask(Masks.EMAIL)
    private String email;
    

    
}
