package org.rookie.business.service.impl;


import lombok.AllArgsConstructor;
import org.rookie.business.feign.UserFeignClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl {
    
    private final UserFeignClient userFeignClient;
}
