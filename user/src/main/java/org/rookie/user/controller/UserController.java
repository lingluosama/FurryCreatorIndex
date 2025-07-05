package org.rookie.user.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.rookie.entity.database.UserTable;
import org.rookie.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    
    
    @GetMapping("/id")
    public ResponseEntity<UserTable> GetUserById(@RequestParam("id") String id) {
        long uid = Long.parseLong(id);
        UserTable userTable = userService.queryByid(uid);
        return ResponseEntity.status(HttpStatus.OK).body(userTable);

    }
    
    @PostMapping("/register")
    public ResponseEntity<String> RegisterUser(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("signature") String signature,
            @RequestParam("email") String email
    ) {
        UserTable userTable = new UserTable();
        userTable.setName(name);
        userTable.setPassword(password);
        userTable.setSignature(signature);
        userTable.setEmail(email);
        userService.saveUser(userTable);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
    @PostMapping("/update")
    public ResponseEntity<String> UpdateUser(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("signature") String signature,
            @RequestParam("email") String email
    ){
        UserTable userTable = new UserTable();
        userTable.setId(Long.parseLong(id));
        userTable.setName(name);
        userTable.setPassword(password);
        userTable.setSignature(signature);
        userTable.setEmail(email);
        userTable.setAvatar("http://test.xyz/img/avatar.png");
        userTable.setSalt("fine");
        userService.updateUser(userTable);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
    
}
