package cn.iocoder.springboot.lab22.validation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

//javax.validation.constraints包下所支持的一些约束类型

/**
 * 员工信息
 * @author galen
 * @date 2020/2/26
 * */
@Data
public class Employee {

    public interface AGroup{}

    public interface BGroup{}

    @NotEmpty(groups = AGroup.class,message = "姓名必填!")
    @Length(max = 20, message = "姓名过长!")
    private String name;

    @NotEmpty(groups = BGroup.class,message = "工牌必填!")
    /*@Pattern(regexp = "^\\d{10}",message = "请输入10位数字工牌!")//长度10，0-9*/
    private String badgeCode;

    @Pattern(regexp = "^[1-2]",message = "性别参数错误!")
    @NotNull(message = "性别必填!")
    private String gender;

    @Past(message = "无效的出生日期!")
    private Date birthDate;

    @Email
    private String email;

    @NotNull(message = "身份证号码必填!")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$"
            ,message = "身份证号码格式错误")
    private String idCardNumber;

    @Valid
    private Salary salary;

    @NotNull(message = "联系电话必填!")
    @Pattern(regexp = "^[1]+\\d{10}$"
            ,message = "电话号码格式错误")
    private String phoneNumber;
}
