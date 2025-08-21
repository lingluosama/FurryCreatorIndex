package org.rookie.data.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.data.service.IUserService;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.AuthDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data/user")
@RequiredArgsConstructor
public class UserServiceController {

    private static final Logger log = LoggerFactory.getLogger(UserServiceController.class);
    private final IUserService userService;   

    @GetMapping("/ping")
    Result<Boolean> ping() {
        return Result.failed(HttpStatus.BAD_REQUEST,"test failed");
    }

    @PostMapping("/register")
    Result<AuthDTO> register(@RequestBody UserRegisterForm form) {
        try {
            log.warn(form.toString());
            AuthDTO dto = userService.userRegister(form);
            return Result.success(dto);
        }catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * 输入邮箱或用户名
     * @param form
     * @return
     */
    @PostMapping("/login")
    Result<AuthDTO> login(
            @RequestBody UserLoginForm form
    ){
        
        try {
            AuthDTO dto = userService.userLogin(form.getCredentials(), form.getPassword());
            return Result.success(dto);
        }catch (BusinessException e){
            return Result.failed(e.getMessage());
        }catch (Exception e){
            throw e;
        }
    }
    

}
