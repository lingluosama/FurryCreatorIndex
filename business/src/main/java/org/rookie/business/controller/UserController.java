package org.rookie.business.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
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
    
    @PostMapping("/register")
    Result<AuthDTO> userRegister(
            UserRegisterForm form
    ) {
        try {
            AuthDTO dto = userService.userRegister(form);
            return Result.success(dto);
        }catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }catch (Exception e) {
            throw e;
        }
    }
    
    @GetMapping("/login")
    Result<AuthDTO> userLogin(
            UserLoginForm form
    ) {
        try {
            log.warn(form.toString());
            AuthDTO dto = userService.userLogin(form);
            return Result.success(dto);
        }catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }catch (Exception e) {
            throw e;
        }
    }
    
}
