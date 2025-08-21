package org.rookie.data.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.rookie.annotation.CacheDbSync;
import org.rookie.annotation.RedisCache;
import org.rookie.data.converter.UserConverter;
import org.rookie.data.service.IRoleService;
import org.rookie.data.utils.PasswordEncryptor;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.exception.BusinessException;
import org.rookie.exception.BusinessExceptionEnum;
import org.rookie.data.mapper.UserMapper;
import org.rookie.data.service.IUserService;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.entity.database.User;
import org.rookie.model.entity.database.table.UserTableDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserConverter userConverter;
    private final PasswordEncryptor passwordEncryptor;
    private final IRoleService roleService;
    
    @Value("${fc-config.default-user-role-id}")
    private Long defaultUserPermissionId;
    //使用getMapper获取mapper
    
    @Override
    public List<User> SearchUser(String keyword, Integer offset, Integer limit) {
        
        return List.of();
    }

    @Override
    public User queryByid(Long id) {
        User user = queryChain().where(UserTableDef.USER.ID.eq(id)).one();
        if(user ==null){
            throw BusinessExceptionEnum.NOT_FIND_IN_DATABASE.exception();
        }
        return user;
    }

    @Override
    public AuthDTO userRegister(UserRegisterForm form) {
        User user = userConverter.toUser(form);
        
        user.setPasswordHash(passwordEncryptor.encrypt(user.getPasswordHash()));
        user.setAvatarUrl("https://pbs.twimg.com/media/FhS2eMFUcAEGIYr.jpg");
        
        boolean exists = queryChain().where(UserTableDef.USER.USERNAME.eq(user.getUsername())).exists();
        if(exists){
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"用户名已存在"); 
        }

        boolean save = this.save(user);
        if (save){
            
            //读取yml中的默认权限id配置
            roleService.addRoleForUser(user.getId(),defaultUserPermissionId);

            //处理SaToken登录
            StpUtil.login(user.getId());
            List<String> roles = roleService.getUserRoles(user.getId());
            StpUtil.getSession().set("roleList",roles);
            
            
            AuthDTO dto = new AuthDTO();
            dto.setId(user.getId());
            dto.setUserName(user.getUsername());
            return dto;
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "未保存用户");
        }
    }

    @Override
    public AuthDTO userLogin(String credentials, String password) {

        AuthDTO dto = new AuthDTO();

        User tableUser = this.queryChain()
                .where(UserTableDef.USER.USERNAME.eq(credentials))
                .or(UserTableDef.USER.EMAIL.eq(credentials))
                .one();
        if(tableUser==null){
            throw BusinessExceptionEnum.NOT_FIND_IN_DATABASE.exception();
        }
        log.warn(password);
        boolean isCurrant = passwordEncryptor.matches( password,tableUser.getPasswordHash());
        if(!isCurrant){
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), "用户密码错误");
        }

        //处理SaToken登录
        StpUtil.login(tableUser.getId());
        List<String> roles = roleService.getUserRoles(tableUser.getId());
        StpUtil.getSession().set("roleList",roles);
        
        //手动返回token,使业务层通过feign调用能拿到
        dto.setToken(StpUtil.getTokenValue());
        
        dto.setUserName(tableUser.getUsername());
        Optional.ofNullable(tableUser.getNickname()).ifPresent(dto::setNickName);
        dto.setAvatarUrl(tableUser.getAvatarUrl());
        dto.setId(tableUser.getId());
        
        return dto;
    }

    @Override
    public User updateUser(User user) {

        boolean update = updateById(user);
        if(update){
            return getById(user.getId());
        }else{
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),"更新失败");
        }

    }
}
