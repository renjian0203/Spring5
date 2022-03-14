package com.renjian.spring.day02.bean;

public class Orders {
    private String oName;

    public Orders() {
        System.out.println("1.无参构造执行...");
    }

    public void setoName(String oName) {
        System.out.println("2.set方法执行...");
        this.oName = oName;
    }

    public void initMethod(){
        System.out.println("4.初始化方法执行...");
    }

    public void distoryMethod(){
        System.out.println("7.销毁方法执行...");
    }
}
