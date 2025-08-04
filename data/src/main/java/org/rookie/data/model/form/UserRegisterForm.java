package org.rookie.data.model.form;


import lombok.Data;

@Data
public class UserRegisterForm {
    String userName;
    String password;
    String email;
    String nickName;
    String phoneNumber;
    String ip;
}
