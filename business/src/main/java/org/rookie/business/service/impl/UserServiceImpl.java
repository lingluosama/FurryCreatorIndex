package org.rookie.business.service.impl;


import lombok.AllArgsConstructor;
import org.rookie.business.feign.UserFeignClient;
import org.rookie.business.model.form.UserRegisterForm;
import org.rookie.business.service.UserService;
import org.rookie.model.dto.AuthDTO;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserFeignClient userFeignClient;

    @Override
    public AuthDTO userRegister(UserRegisterForm form) {
        AuthDTO dto = userFeignClient.register(
                form.getUserName(),
                form.getPassword(),
                form.getEmail(),
                form.getNickName(),
                form.getPhoneNumber(),
                form.getIp()
        );
        return dto;
    }
}