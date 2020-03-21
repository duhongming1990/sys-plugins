package com.github.plugin.sysdict.common.dict;

import com.github.plugin.sysdict.common.utils.DictUtils;
import com.github.plugin.sysdict.common.utils.Key;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author duhongming
 * @version 1.0
 * @description
 *      主要是对所有返回值为bean包下的方法，
 *      进行字典转换
 * @date 2020/3/21 08:50
 */
@Slf4j
@Aspect
@Component
public class ServiceAOP {

    @Pointcut("execution(public com.github.plugin.sysdict.bean.* *(..))")
    public void dictSign() {
    }

    /**
     * 对注解dictSign进行处理
     *
     * @param pjp
     * @return
     */
    @Around("dictSign()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object obj = pjp.proceed();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            DictField dictField = field.getAnnotation(DictField.class);
            Object fieldValue = field.get(obj);
            if (Objects.isNull(dictField) || Objects.isNull(fieldValue)) {
                continue;
            }
            String typeCode = dictField.typeCode();
            if (StringUtils.isBlank(typeCode)) {
                typeCode = field.getName();
            }
            String result = DictUtils.me.getDictName(Key.of().typeCode(typeCode).dictValue(fieldValue.toString()), "");
            field.set(obj, result);
        }
        return obj;
    }
}