package org.rookie.business.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.rookie.consts.Result;
import org.rookie.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * 这个切面捕获的是被下游服务成功返回为Result，但状态码并非为200的结果
 */
@Aspect
@Component
public class FeignResultAspect {


    private static final Logger log = LoggerFactory.getLogger(FeignResultAspect.class);

    @Around("execution(* org.rookie.business.feign.*.*(..))")
    public Object handleFeignResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        if(result instanceof Result<?> feignResult){
            if(feignResult.getCode()!= HttpStatus.OK.value()){
                log.warn("已将下游服务非正确响应封装为Bis，原响应: {}", feignResult);
                throw new BusinessException(feignResult.getCode(), feignResult.getMsg());
            }

        }
        return result;

    }
    
}
