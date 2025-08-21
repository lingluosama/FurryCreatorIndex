package org.rookie.consts;

import com.mybatisflex.annotation.EnumValue;

public enum RedisKeys {
    
    ROLE_PERMISSION_KEY_PREFIX("satoken:role:permission:");
    
    
    @EnumValue
    private final String key;

    RedisKeys(String key) {
        this.key = key;
    }
}
