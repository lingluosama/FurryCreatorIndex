package org.rookie.business.feign;


import org.rookie.business.config.FeignConfig;
import org.rookie.consts.Result;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "data-service",path = "/data",configuration = FeignConfig.class)
public interface UserFeignClient {

    @GetMapping("/user/ping")
    Result<Boolean> ping();

    @PostMapping("/user/register")
    Result<AuthDTO> register(
           @RequestBody UserRegisterForm form
    );
    
    @PostMapping("/user/login")
    Result<AuthDTO> login(
            @RequestBody UserLoginForm form
    );
    
}
