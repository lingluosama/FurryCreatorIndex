package org.rookie.business.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.rookie.business.service.AuthService;
import org.rookie.business.service.UserService;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.rookie.model.dto.AuthDTO;
import org.rookie.model.dto.PageResult;
import org.rookie.model.dto.RolePermissionSearchDTO;
import org.rookie.model.entity.database.Permission;
import org.rookie.model.form.RoleForm;
import org.rookie.model.form.UserLoginForm;
import org.rookie.model.form.UserRegisterForm;
import org.rookie.model.query.PermissionQuery;
import org.rookie.model.query.RolePageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    Result<AuthDTO> userRegister(
            UserRegisterForm form
    ) {
        try {
            AuthDTO dto = userService.userRegister(form);
            return Result.success(dto);
        }catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/login")
    Result<AuthDTO> userLogin(
            UserLoginForm form,
            HttpServletResponse response
    ) {
        try {
            log.warn(form.toString());

            AuthDTO dto = userService.userLogin(form);
            String token = dto.getToken();
            if(token!=null){
                Cookie saTokenCookie = new Cookie("fc-token", token);

                saTokenCookie.setPath("/");
                saTokenCookie.setMaxAge(86400);
                saTokenCookie.setHttpOnly(false);
                saTokenCookie.setSecure(false);
                saTokenCookie.setDomain("localhost");

                response.addCookie(saTokenCookie);
            }


            dto.setToken(fakeTokenGenerate(24));
            return Result.success(dto);
        }catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/role")
    Result<Void> createRole(RoleForm form) {
        authService.createRole(form);
        return Result.success();
    }

    @GetMapping("/role")
    Result<RolePermissionSearchDTO> queryRole(RolePageQuery query) {
        RolePermissionSearchDTO dto = authService.queryRole(query);
        return Result.success(dto);
    }

    @DeleteMapping("/role/{id}")
    Result<Void> deleteRole(Long roleId) {
        authService.deleteRole(roleId);
        return Result.success();
    }

    @PutMapping("/role/{id}")
    Result<Void> updateRolePermission(List<Long> permissionIds, @PathVariable("id") Long roleId){
        authService.updateRolePermission(permissionIds, roleId);
        return Result.success();
    }

    @PutMapping("/users/{userId}/roles")
    Result<Void> updateUserRole(@RequestBody List<Long> roleIds, @PathVariable("userId") Long userId){
        authService.updateUserRole(roleIds, userId);
        return Result.success();
    }

    @PostMapping("/permission")
    Result<Void> createPermission(RoleForm form) {
        authService.createPermission(form);
        return Result.success();
    }

    @GetMapping("/permission")
    Result<PageResult<Permission>> searchPermission(PermissionQuery query) {
        PageResult<Permission> result = authService.searchPermission(query);
        return Result.success(result);
    }

    @DeleteMapping("/permission/{id}")
    Result<Void> deletePermission(Long permissionId) {
        authService.deletePermission(permissionId);
        return Result.success();
    }


    //处理Auth响应中已经存入cookie不应该返回的token字段
    public static String fakeTokenGenerate(int length) {
        UUID uuid = UUID.randomUUID();
        BigInteger bigInt = new BigInteger(uuid.toString().replace("-", ""), 16);
        // 将BigInteger转换为二进制字符串
        String binaryString = bigInt.toString(2);

        // 如果生成的字符串长度不足，前面用0补齐
        while (binaryString.length() < length) {
            binaryString = "0" + binaryString;
        }

        // 截取到需要的长度
        return binaryString.substring(0, length);
    }

}
