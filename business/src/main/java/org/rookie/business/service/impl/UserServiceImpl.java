package org.rookie.business.service.impl;


import lombok.RequiredArgsConstructor;
import org.rookie.business.feign.UserFeignClient;
import org.rookie.business.service.UserService;
import org.rookie.consts.Result;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserRegisterForm;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserFeignClient userFeignClient;

    @Override
    public String ping() {
        Result<Boolean> ping = userFeignClient.ping();
        return ping.getData().toString();
    }

    @Override
    public AuthDTO userRegister(UserRegisterForm form) {
        Result<AuthDTO> dtoResult = userFeignClient.register(
                form
        );
        return dtoResult.getData();
    }

    @Override
    public AuthDTO userLogin(String credentials, String password) {
        Result<AuthDTO> dtoResult = userFeignClient.login(
                credentials,
                password
        );
        return dtoResult.getData();
    }
}