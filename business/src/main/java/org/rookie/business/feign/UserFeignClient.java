package org.rookie.business.feign;


import org.rookie.consts.Result;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserRegisterForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "data-service",path = "/data")
public interface UserFeignClient {
    
    @PostMapping("/user/register")
    Result<AuthDTO> register(
            UserRegisterForm form
    );
}
