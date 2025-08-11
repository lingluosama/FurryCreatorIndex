package org.rookie.business.service;

import org.rookie.model.dto.AuthDTO;
import org.rookie.model.form.UserRegisterForm;

public interface UserService {
    
    AuthDTO userRegister(UserRegisterForm form);
    
    AuthDTO userLogin(String credentials, String password);
    
}
