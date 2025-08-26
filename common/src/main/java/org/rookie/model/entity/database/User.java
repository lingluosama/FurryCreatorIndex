package org.rookie.model.entity.database;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.mybatisflex.core.mask.Masks;
import lombok.Data;

import java.time.LocalDateTime;

@Table("user")
@Data
public class User {
    @Id(keyType = KeyType.Generator, value = "FcIdGenerator")
    private Long id;

    private String username;

    @JsonIgnore // 敏感信息，不直接暴露给前端
    private String passwordHash;
    
    private String nickname;

    private String avatarUrl;

    private String email;

    private String phoneNumber;

    private String status; // UserStatus 枚举，例如 ACTIVE, INACTIVE, LOCKED

    private String registrationIp;

    private LocalDateTime lastLoginAt;

    @Column(onInsertValue = "now()")
    private LocalDateTime createdAt;

    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updatedAt;

    
}
