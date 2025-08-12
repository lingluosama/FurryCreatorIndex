package org.rookie.business.service.impl;


import lombok.RequiredArgsConstructor;
import org.rookie.business.feign.UserFeignClient;
import org.rookie.business.service.UserService;
import org.rookie.consts.Result;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
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
    public AuthDTO userLogin(UserLoginForm form) {
        Result<AuthDTO> dtoResult = userFeignClient.login(
                form
        );
        return dtoResult.getData();
    }
}