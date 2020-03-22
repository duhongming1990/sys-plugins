package com.github.plugin.sysdict.common.response;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * @author duhongming
 * @version 1.0
 * @description
 *      主要是对所有返回值为JsonResult的方法，
 *      进行入参监控，
 *      以及异常处理
 * @date 2020/3/21 08:42
 */
@Slf4j
@Aspect
@Component
public class ResponseResultAspect {

    @Pointcut("execution(public com.github.plugin.sysdict.common.response.JsonResult *(..))")
    public void webLog() {
    }


    /**
     * 对返回值为resultbean的方法进行切面，获取其入参
     *
     * @param pjp
     * @return
     */
    @Around("webLog()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {

        long startTime = System.currentTimeMillis();
        JsonResult result;
        try {
            result = (JsonResult) pjp.proceed();
            log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (!Objects.isNull(attributes)) {
                HttpServletRequest request = attributes.getRequest();
                // 记录下请求内容
                log.info("URL : " + request.getRequestURL().toString());
                log.info("HTTP_METHOD : " + request.getMethod());
                log.info("IP : " + request.getRemoteAddr());
                log.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());

                //序列化时过滤掉request和response
                List<Object> logArgs = streamOf(pjp.getArgs())
                        .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                        .collect(Collectors.toList());
                log.info("ARGS : " + JSON.toJSONString(logArgs));
            }
        } catch (Throwable e) {
            e.printStackTrace();
            result = handlerException(pjp, e);
        }
        return result;

    }

    private <T> Stream<T> streamOf(T[] array) {
        return ArrayUtils.isEmpty(array) ? Stream.empty() : Arrays.asList(array).stream();
    }


    /**
     * 对切面方法进行返回值处理
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + JSON.toJSONString(ret));
    }

    @SuppressWarnings("rawtypes")
    /**
     * 对切面方法进行异常捕获处理
     * @param pjp
     * @param e
     * @return
     */
    private JsonResult handlerException(ProceedingJoinPoint pjp, Throwable e) {

        JsonResult result = new JsonResult().setSuccess(false);
//        // 已知异常
//        if (e instanceof IllegalStateException) {
//            result.setMsg(e.getLocalizedMessage() + "非法文件格式");
//            result.setCode(JsonResult.FAIL);
//        } else if (e instanceof IOException) {
//            result.setMsg(e.getLocalizedMessage() + "找不到文件夹");
//            result.setCode(JsonResult.FAIL);
//        } else if (e instanceof CommonException) {
//            logger.error(pjp.getSignature() + " exception ", e);
//            CommonException commonException = (CommonException) e;
////            result.setCode(commonException.getErrorCode());
////            result.setMsg(commonException.getErrorInfo());
//            // 未知异常是应该重点关注的，这里可以做其他操作，如通知邮件，单独写到某个文件等等。
//        }
        if (e instanceof BadSqlGrammarException) {
            result.setMessage("SQL语法错误！");
        } else {
            log.error(pjp.getSignature() + " exception ", e);
        }
        return result;
    }

}