package com.renjian.spring.day01.bean;

public class Emp {
//    表示当前员工属于那个部门
    private Dept dept;
    private String name;
    private String sex;

    public Dept getDept() {
        return dept;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setSex(String sex) {

        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "dept=" + dept.getName() +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
