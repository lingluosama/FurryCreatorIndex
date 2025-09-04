package org.rookie.business.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rookie.business.service.UserService;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @SaCheckLogin
    @GetMapping("/ping")
    Result<String> ping() {
        String pinged = userService.ping();
        return Result.success(pinged);
    }
    

    
}
