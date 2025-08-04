package org.rookie.business.service;

import org.rookie.business.model.form.UserRegisterForm;
import org.rookie.model.dto.AuthDTO;

public interface UserService {
    
    AuthDTO userRegister(UserRegisterForm form);
    
}
