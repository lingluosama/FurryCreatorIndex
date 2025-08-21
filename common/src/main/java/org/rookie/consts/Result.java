package org.rookie.consts;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private T data;
    private String msg;

    public static <T> Result<T> success() {
        return success(null);
    }
    
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("All Accepted");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed(HttpStatus code,String msg,T data) {
        return result(code, msg, data);
    }

    public static <T> Result<T> failed(HttpStatus code,String msg) {
        return result(code, msg, null);
    }
    
    public static <T> Result<T> failed(String msg) {
        return result(HttpStatus.BAD_REQUEST, msg, null);
    }

    private static <T> Result<T> result(HttpStatus code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code.value());
        result.setData(data);
        result.setMsg(msg);
        return result;
    }


    
    
}
