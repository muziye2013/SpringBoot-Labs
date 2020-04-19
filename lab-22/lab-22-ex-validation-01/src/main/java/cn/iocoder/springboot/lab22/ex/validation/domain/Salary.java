package cn.iocoder.springboot.lab22.ex.validation.domain;

import javax.validation.constraints.NotNull;

public class Salary {

    @NotNull(message = "月份必填!")
    private int month;

    @NotNull(message = "年份必填!")
    private int year;

    @NotNull(message = "薪金必填!")
    private int money;

    @NotNull(message = "员工姓名必填!")
    private String name;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
