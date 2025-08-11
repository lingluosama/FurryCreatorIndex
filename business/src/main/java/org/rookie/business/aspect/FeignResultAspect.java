package org.rookie.business.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * 处理下游服务调用非200的情况(已经被Decoder代替实现)
 */
@Aspect
@Component
public class FeignResultAspect {
    
//
//    @Around("execution(* org.rookie.business.feign.*.*(..))")
//    public Object handleFeignResult(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object result = joinPoint.proceed();
//        
//        //直接抛下游服务异常
//        if(result instanceof Result<?> feignResult){
//            if(feignResult.getCode()!= HttpStatus.OK.value()){
//                throw new BusinessException(feignResult.getCode(), feignResult.getMsg());
//            }
//            
//        }
//        return result;
//        
//    }
    
}
