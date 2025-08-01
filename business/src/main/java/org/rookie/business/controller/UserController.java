package org.rookie.business.controller;


import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserRegisterForm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data/user")
public class UserController {
    
    @PostMapping("/register")
    AuthDTO userRegister(
            UserRegisterForm form
    ) {
        AuthDTO dto = new AuthDTO();
        dto.setAvatar_url("https://www.google.com/");
        return dto;
    }
    
}
