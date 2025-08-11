package org.rookie.business.controller;


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
