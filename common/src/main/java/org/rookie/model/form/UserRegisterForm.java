package org.rookie.model.form;


import lombok.Data;

@Data
public class UserRegisterForm {
    String userName;
    String password;
    String email;
    String captcha;
    String nickName;
    String phoneNumber;
    String ip;
}
