package org.rookie.business.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.rookie.business.service.UserService;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserRegisterForm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/ping")
    Result<String> ping() {
        String pinged = userService.ping();
        return Result.success(pinged);
    }
    
    @SaCheckLogin
    @GetMapping("/ping")
    Result<Boolean> ping(){
        return Result.success(true);
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
            String credentials,
            String password
    ) {
        try {
            AuthDTO dto = userService.userLogin(credentials, password);
            return Result.success(dto);
        }catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }catch (Exception e) {
            throw e;
        }
    }
    
}
