package org.rookie.data.service;


import com.mybatisflex.core.service.IService;
import org.rookie.annotation.CacheDbSync;
import org.rookie.annotation.RedisCache;
import org.rookie.model.entity.database.User;

import java.util.List;

public interface IUserService extends IService<User>{
    
    List<User> SearchUser(String keyword, Integer offset, Integer limit);

    @RedisCache(key = "UserTable:%s",expire = 168)
    User queryByid(Long id);

    @CacheDbSync
    User saveUser(User user);
    @CacheDbSync
    User updateUser(User user);
    
}
