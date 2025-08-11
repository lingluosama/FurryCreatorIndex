package org.rookie.business.feign;


import org.rookie.consts.Result;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserRegisterForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "data-service",path = "/data")
public interface UserFeignClient {
    
    @PostMapping("/user/register")
    Result<AuthDTO> register(
            UserRegisterForm form
    );
    
    @GetMapping("/user/login")
    Result<AuthDTO> login(
           @RequestParam String credentials,
           @RequestParam String password
    );
    
}
