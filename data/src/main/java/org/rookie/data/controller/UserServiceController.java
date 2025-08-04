package org.rookie.data.controller;

import lombok.RequiredArgsConstructor;
import org.rookie.consts.Result;
import org.rookie.data.model.form.UserRegisterForm;
import org.rookie.data.service.IUserService;
import org.rookie.model.entity.database.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data/user")
@RequiredArgsConstructor
public class UserServiceController {
    private IUserService userService;
    
    @PostMapping("/register")
    Result<Boolean> registerUser(UserRegisterForm form) {
        try {
            User user = userService.saveUser(form);
        }
    }
    

}
