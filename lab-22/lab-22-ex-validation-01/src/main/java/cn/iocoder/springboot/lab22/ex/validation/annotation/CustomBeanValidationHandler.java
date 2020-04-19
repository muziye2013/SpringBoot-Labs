package cn.iocoder.springboot.lab22.ex.validation.annotation;

/**
 * 自定义异常处理，未实现
 * @author galen
 * @date 2020/2/26
 * */
public interface  CustomBeanValidationHandler {
    Object constraintViolationHanding(String errorMsg) ;
}
