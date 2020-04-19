package cn.iocoder.springboot.lab22.ex.validation.annotation;

import cn.iocoder.springboot.lab22.ex.validation.domain.R;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 定义切面
 * @author galen
 * @date 2020/2/26
 * */
@Aspect
@Component
public class BeanValidationHandler {

    private static Logger log = LoggerFactory.getLogger(BeanValidationHandler.class);

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * 定义环绕通知，在指定注解处（切点）拦截，获取切点对象参数，以及获取BeanValidation的自定义参数
     * */
    @Around("@annotation(beanValidation)")
    public final Object validateParamByAnnotation(ProceedingJoinPoint ponit, BeanValidation beanValidation) throws Throwable {
        log.info("==========================  start bean validation in :" + ponit.getSignature().getDeclaringTypeName() + "==========================");
        String errorMsg = packErrorMsg(ponit.getArgs());
        if (errorMsg != null && errorMsg.length() > 0) {
            String handlerName = beanValidation.handler();
            if ("default".equals(handlerName)) {
                //返回指定格式错误信息
                return R.error(errorMsg);
            }
            return customHandlerInvoke(handlerName, errorMsg);
        }
        return ponit.proceed();
    }

    private String packErrorMsg(Object[] args) {
        Validator validator = validatorFactory.getValidator();
        StringBuilder builder = new StringBuilder();
        Object arg = args[0];
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(arg);
        builder.append(packErrorMsg(constraintViolationSet));
        return builder.toString();
    }

    private String packErrorMsg(Set<ConstraintViolation<Object>> constraintViolationSet) {
        StringBuilder builder = new StringBuilder();
        constraintViolationSet.forEach(e -> {
            builder.append(e.getMessage()).append(",");
        });
        String errorMsg = "";
        if (builder.toString().length() > 0) {
            errorMsg = builder.toString().substring(0, builder.toString().length() - 1);
            errorMsg = errorMsg + System.getProperty("line.separator");
        }
        return errorMsg;
    }

    private final Object customHandlerInvoke(String handlerName, String errorMsg) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class handlerClass = null;
        try {
            handlerClass = Class.forName(handlerName);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("not find handler class by name:" + handlerName);
        }
        isRightHandler(handlerClass);
        Method method = handlerClass.getMethod("constraintViolationHanding", String.class);
        CustomBeanValidationHandler handler = (CustomBeanValidationHandler) handlerClass.newInstance();
        return method.invoke(handler, errorMsg);
    }

    private final void isRightHandler(Class handlerClass) {
        Class[] intefaceClazzs = handlerClass.getInterfaces();
        if (intefaceClazzs == null) {
            throw new IllegalArgumentException("handler must be Implement The interface CustomBeanValidationHandler");
        }
        List<Class> classList = new ArrayList<>();
        Collections.addAll(classList, intefaceClazzs);
        if (!classList.contains(CustomBeanValidationHandler.class)) {
            throw new IllegalArgumentException("handler must be Implement The interface CustomBeanValidationHandler");
        }
    }
}
