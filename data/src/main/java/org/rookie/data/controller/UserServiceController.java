package org.rookie.data.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.data.service.IUserService;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.AuthDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data/user")
@RequiredArgsConstructor
public class UserServiceController {
    
    private final IUserService userService;   
        
    @SaCheckLogin
    @GetMapping("/ping")
    Result<Boolean> ping(){
        return Result.success(true);
    }
    
    @PostMapping("/register")
    Result<AuthDTO> register(UserRegisterForm form) {
        try {
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
     * @param credentials
     * @param password
     * @return
     */
    @GetMapping("/login")
    Result<AuthDTO> login(
            String credentials,
            String password
    ){
        try {
            AuthDTO dto = userService.userLogin(credentials, password);
            return Result.success(dto);
        }catch (BusinessException e){
            return Result.failed(e.getMessage());
        }catch (Exception e){
            throw e;
        }
    }
    

}
