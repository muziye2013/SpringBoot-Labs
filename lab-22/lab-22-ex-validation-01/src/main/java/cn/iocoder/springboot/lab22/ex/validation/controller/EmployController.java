package cn.iocoder.springboot.lab22.ex.validation.controller;

import cn.iocoder.springboot.lab22.ex.validation.annotation.BeanValidation;
import cn.iocoder.springboot.lab22.ex.validation.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.List;
import java.util.Set;

/**
 * 用户信息接口
 * @author galen
 * @date 2020/2/26
 * */
@RequestMapping(path = "/employee")
@RestController
public class EmployController {

    private static String lineSeparator = System.lineSeparator();

    /**
     * 注解实现
     * 使用@Valid 注解 实体， 并传入参数bindResult以获取校验结果信息
     * @param employee
     * @param bindingResult
     * @return
     */
    @PostMapping("/bindingResult")
    public Object addEmployee(@RequestBody @Valid Employee employee, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            //校验结果以集合的形式返回，当然也可以获取单个。具体可以查看bindResult的API文档
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            //StringBuilder组装异常信息
            StringBuilder builder = new StringBuilder();
            //遍历拼装
            fieldErrorList.forEach(error -> {
                builder.append(error.getDefaultMessage() + lineSeparator);
            });
            builder.insert(0,"use @Valid n BingdingResult :" +lineSeparator);
            return builder.toString();
        }

        //TODO there can invoke service layer method to do someting
        return "添加职员信息成功:" + employee.toString();
    }

    //Spring boot 已帮我们把 validation 的关键对象的实例装载如 IOC 容器中
    @Autowired
    private ValidatorFactory autowiredValidatorFactory;

    @Autowired
    private Validator autowiredValidator;
    /**
     * 调用validator实现
     * @param employee
     * @return
     */
    @PostMapping("/validator")
    public Object addEmployee(@RequestBody Employee employee){
        System.out.println("这里将导入 由 Springboot 的 IOC 容器中获取的 校验器工厂和 校验器类");
        System.out.println("validator工厂类:"+ autowiredValidatorFactory.toString());
        System.out.println("validator类："+ autowiredValidator.toString());

        /**
         * 下述的工厂类和校验器类也可以使用上述由IOC容器中获取的对象实例代替
         */

        //实例化一个 validator工厂
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        //获取validator实例
        Validator validator = validatorFactory.getValidator();
        //调用调用，得到校验结果信息 Set
        Set<ConstraintViolation<Employee>> constraintViolationSet = validator.validate(employee);
        //StringBuilder组装异常信息
        StringBuilder builder = new StringBuilder();
        //遍历拼装
        constraintViolationSet.forEach(violationInfo -> {
            builder.append(violationInfo.getMessage() + lineSeparator);
        });
        if (builder.toString().length() > 0){
            builder.insert(0,"use validator :" +lineSeparator);
            return builder.toString();
        }
        return "添加职员信息成功:" + employee.toString();
    }

    /**
     * 调用BeanValidation 切面实现
     * @param
     * @return
     */
    @PostMapping("/validateByAspect")
    @BeanValidation
    public Object addEmployeeByAspect(@RequestBody Employee employee1){
        return "添加职员信息成功" ;
    }

    /**
     * 使用springvalidation默认的实现方式，返回数据不友好
     * @param
     * @return
     */
    @PostMapping("/addEmployeeByValid")
    public Object addEmployeeByValid(@RequestBody @Valid Employee employee1){
        return "添加职员信息成功" ;
    }

}
