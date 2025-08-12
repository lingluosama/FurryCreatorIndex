package org.rookie.business.service;

import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;

public interface UserService {

    String ping();

    AuthDTO userRegister(UserRegisterForm form);
    
    AuthDTO userLogin(UserLoginForm form);
    
}
