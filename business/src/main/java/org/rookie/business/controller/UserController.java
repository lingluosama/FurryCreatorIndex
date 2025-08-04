package org.rookie.business.controller;


import lombok.AllArgsConstructor;
import org.rookie.business.model.form.UserRegisterForm;
import org.rookie.business.service.UserService;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.AuthDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data/user")
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;
    
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
    
}
