package org.rookie.user.service;


import com.mybatisflex.core.service.IService;
import org.rookie.annotation.CacheDbSync;
import org.rookie.annotation.RedisCache;
import org.rookie.model.entity.database.UserTable;

import java.util.List;

public interface IUserService extends IService<UserTable>{
    
    List<UserTable> SearchUser(String keyword,Integer offset,Integer limit);

    @RedisCache(key = "UserTable:%s",expire = 168)
    UserTable queryByid(Long id);

    @CacheDbSync
    UserTable saveUser(UserTable user);
    @CacheDbSync
    UserTable updateUser(UserTable user);
    
}
