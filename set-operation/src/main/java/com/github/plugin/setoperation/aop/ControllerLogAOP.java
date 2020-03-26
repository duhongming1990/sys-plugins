package com.github.plugin.setoperation.aop;

/**
 * @author duhongming
 * @version 1.0
 * @description TODO
 * @date 2020/3/26 13:23
 */

import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author duhongming
 * @version 1.0
 * @description 记录初始化耗时
 * @date 2019/9/25 15:27
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAOP {

    private TimeInterval interval = new TimeInterval();

    @Pointcut("execution(* com.github.plugin.setoperation.service.*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        log.info("Method [{}.{}] start execute...",
                pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName());
        interval.start();
        Object obj = null;
        try {
            obj =  pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("Method [{}.{}] execute success, spend time [{}]ms",
                pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName(),
                interval.intervalMs());
        return obj;
    }

}