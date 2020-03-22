package com.github.plugin.sysdict.common.dict;

import com.github.plugin.sysdict.common.excel.Reflections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

/**
 * @author duhongming
 * @version 1.0
 * @description 主要是对所有返回值为bean包下的方法，
 * 进行字典转换
 * @date 2020/3/21 08:50
 */
@Slf4j
@Aspect
@Component
public class DictTransformAspect {

    //service.impl包下的方法 并且 方法上带有@DictSign
    @Pointcut("execution(* com.github.plugin.sysdict.service.impl.*.*(..)) " +
            "&& @annotation(com.github.plugin.sysdict.common.dict.DictSign)")
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
        //处理List<T>
        if (obj instanceof List) {
            List l = (List) obj;
            for (Object o : l) {
                handleField(o);
            }
        }
        //处理T
        else {
            handleField(obj);
        }

        return obj;
    }

    private void handleField(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);
            DictField dictField = field.getAnnotation(DictField.class);

            //成员变量值
            Object fieldValue = field.get(obj);
            if (Objects.isNull(dictField)) {
                continue;
            }

            String funcation = dictField.funcation();
            if (StringUtils.isNotBlank(funcation) && Objects.isNull(fieldValue)) {
//                Object result = obj.getClass().getMethod(funcation).invoke(obj);
                Object result = Reflections.invokeMethodByName(obj, funcation, new Object[]{});
                field.set(obj, result);
            } else {
                String typeCode = dictField.typeCode();
                //成员变量即为字典名
                if (StringUtils.isBlank(typeCode)) {
                    typeCode = field.getName();
                }
                String result = DictUtils.me.getDictName(Key.of().typeCode(typeCode).dictValue(fieldValue.toString()), "");
                field.set(obj, result);
            }
        }
    }
}