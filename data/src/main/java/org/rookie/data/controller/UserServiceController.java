package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.data.service.IUserService;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.AuthDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data/user")
@RequiredArgsConstructor
public class UserServiceController {
    
    private final IUserService userService;   
    
    @PostMapping("/register")
    Result<AuthDTO> registerUser(UserRegisterForm form) {
        try {
            AuthDTO dto = userService.userRegister(form);
            return Result.success(dto);
        }catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }catch (Exception e) {
            throw e;
        }
    }
    

}
