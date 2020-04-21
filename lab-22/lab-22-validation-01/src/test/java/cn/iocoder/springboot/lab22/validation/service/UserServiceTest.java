package cn.iocoder.springboot.lab22.validation.service;

import cn.iocoder.springboot.lab22.validation.Application;
import cn.iocoder.springboot.lab22.validation.dto.Employee;
import cn.iocoder.springboot.lab22.validation.dto.Salary;
import cn.iocoder.springboot.lab22.validation.dto.UserAddDTO;
import cn.iocoder.springboot.lab22.validation.dto.UserUpdateStatusDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Test
    public void testGet() {
        userService.get(-1);
    }

    @Test
    public void testAdd() {
        UserAddDTO addDTO = new UserAddDTO();
        userService.add(addDTO);
    }

    @Test
    public void testAdd01() {
        UserAddDTO addDTO = new UserAddDTO();
        userService.add01(addDTO);
    }

    @Test
    public void testAdd02() {
        UserAddDTO addDTO = new UserAddDTO();
        userService.add02(addDTO);
    }

    @Test
    public void testValidator() {
        // 打印，查看 validator 的类型
        System.out.println(validator);

        // 创建 UserAddDTO 对象
        UserAddDTO addDTO = new UserAddDTO();
        // 校验
        Set<ConstraintViolation<UserAddDTO>> result = validator.validate(addDTO);
        // 打印校验结果
        for (ConstraintViolation<UserAddDTO> constraintViolation : result) {
            // 属性:消息
            System.out.println(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
        }
    }

    @Test
    public void testGroupValidator() {
        // 打印，查看 validator 的类型
        System.out.println(validator);

        // 创建 UserAddDTO 对象
        UserUpdateStatusDTO statusDTO = new UserUpdateStatusDTO();
        statusDTO.setStatus(false);
        // 校验
        Set<ConstraintViolation<UserUpdateStatusDTO>> result = validator.validate(statusDTO,UserUpdateStatusDTO.Group01.class);
        // 打印校验结果
        for (ConstraintViolation<UserUpdateStatusDTO> constraintViolation : result) {
            // 属性:消息
            System.out.println(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
        }
    }

    @Test
    public void testGroup2Validator() {
        // 打印，查看 validator 的类型
        System.out.println(validator);

        // 创建 UserAddDTO 对象
        Employee employee = new Employee();
        employee.setName("");
        employee.setBadgeCode("");
        employee.setGender("1");
        employee.setBirthDate(new Date());
        employee.setIdCardNumber("421023199009102017");
        employee.setEmail("421@qq.com");
        employee.setPhoneNumber("18171223517");
        Salary salary =new Salary();
        salary.setName("小明");
        salary.setMoney(8000);
        salary.setMonth(4);
        salary.setYear(2020);
        employee.setSalary(salary);

        // 校验
        Set<ConstraintViolation<Employee>> result = validator.validate(employee,Employee.AGroup.class);

        // 校验
        Set<ConstraintViolation<Employee>> result2 = validator.validate(employee,Employee.BGroup.class);

        // 打印校验结果
        for (ConstraintViolation<Employee> constraintViolation : result) {
            // 属性:消息
            System.out.println(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
        }
    }

}
