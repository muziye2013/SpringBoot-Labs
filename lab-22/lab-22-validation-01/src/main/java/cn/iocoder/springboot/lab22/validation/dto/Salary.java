package cn.iocoder.springboot.lab22.validation.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Salary {

    @NotNull(message = "月份必填!")
    private int month;

    @NotNull(message = "年份必填!")
    private int year;

    @NotNull(message = "薪金必填!")
    private int money;

    @NotNull(message = "员工姓名必填!")
    private String name;
}
