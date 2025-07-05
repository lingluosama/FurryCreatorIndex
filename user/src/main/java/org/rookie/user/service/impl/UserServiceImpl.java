package org.rookie.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.rookie.annotation.CacheDbSync;
import org.rookie.annotation.RedisCache;
import org.rookie.config.BusinessException;
import org.rookie.config.BusinessExceptionEnum;
import org.rookie.entity.database.UserTable;
import org.rookie.entity.database.table.UserTableTableDef;
import org.rookie.user.mapper.UserMapper;
import org.rookie.user.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl extends ServiceImpl<UserMapper, UserTable> implements IUserService {

//使用getMapper获取mapper
    
    @Override
    public List<UserTable> SearchUser(String keyword, Integer offset, Integer limit) {
        
        return List.of();
    }

    @Override
    @RedisCache(key = "UserTable:%s",expire = 168)
    public UserTable queryByid(Long id) {
        UserTable userTable = queryChain().where(UserTableTableDef.USER_TABLE.ID.eq(id)).one();
        if(userTable==null){
            throw BusinessExceptionEnum.NOT_FIND_IN_DATABASE.exception();
        }
        return userTable;
    }

    @Override
    @CacheDbSync
    public UserTable saveUser(UserTable user) {
        boolean exists = queryChain().where(UserTableTableDef.USER_TABLE.NAME.eq(user.getName())).exists();
        if(exists){
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"用户名已存在"); 
        }

        boolean save = save(user);
        if (save){
            return queryChain().where(UserTableTableDef.USER_TABLE.NAME.eq(user.getName())).one(); 
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "未保存用户");
        }
    }

    @Override
    @CacheDbSync
    public UserTable updateUser(UserTable user) {

        boolean update = updateById(user);
        if(update){
            return getById(user.getId());
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"更新失败");
        }

    }
}
