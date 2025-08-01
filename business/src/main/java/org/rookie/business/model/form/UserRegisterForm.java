package org.rookie.business.model.form;


import lombok.Data;

@Data
public class UserRegisterForm {
    String userName;
    String password;
    String captcha;
    String email;
    String nickName;
    String phoneNumber;
    String ip;
}
