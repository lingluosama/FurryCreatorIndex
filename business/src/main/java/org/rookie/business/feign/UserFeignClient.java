package org.rookie.business.feign;


import org.rookie.model.dto.AuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "data-service",path = "/data")
public interface UserFeignClient {
    
    @PostMapping("/user/register")
    AuthDTO register(
        String userName,
        String password,
        String captcha,
        String email,
        String nickName,
        String phoneNumber,
        String ip
    );
}
