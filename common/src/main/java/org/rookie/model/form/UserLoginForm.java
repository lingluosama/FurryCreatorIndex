package org.rookie.model.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginForm {
    
    @NotNull
    String credentials;
    
    @NotNull
    String password;
}
