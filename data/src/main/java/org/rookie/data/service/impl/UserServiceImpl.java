package org.rookie.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.rookie.annotation.CacheDbSync;
import org.rookie.annotation.RedisCache;
import org.rookie.data.converter.UserConverter;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.exception.BusinessException;
import org.rookie.exception.BusinessExceptionEnum;
import org.rookie.data.mapper.UserMapper;
import org.rookie.data.service.IUserService;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.entity.database.User;
import org.rookie.model.entity.database.table.UserTableDef;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    
    private final UserConverter userConverter;
    //使用getMapper获取mapper
    
    @Override
    public List<User> SearchUser(String keyword, Integer offset, Integer limit) {
        
        return List.of();
    }

    @Override
    @RedisCache(key = "UserTable:%s",expire = 168)
    public User queryByid(Long id) {
        User user = queryChain().where(UserTableDef.USER.ID.eq(id)).one();
        if(user ==null){
            throw BusinessExceptionEnum.NOT_FIND_IN_DATABASE.exception();
        }
        return user;
    }

    @Override
    @CacheDbSync
    public AuthDTO saveUser(UserRegisterForm form) {
        User user = userConverter.toUser(form);
        

        boolean exists = queryChain().where(UserTableDef.USER.USERNAME.eq(user.getUsername())).exists();
        if(exists){
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"用户名已存在"); 
        }

        boolean save = this.save(user);
        if (save){
            AuthDTO dto = new AuthDTO();
            dto.setId(user.getId());
            dto.setUserName(user.getUsername());
            dto.setRole("TODO://");
            return dto;
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "未保存用户");
        }
    }

    @Override
    @CacheDbSync
    public User updateUser(User user) {

        boolean update = updateById(user);
        if(update){
            return getById(user.getId());
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"更新失败");
        }

    }
}
