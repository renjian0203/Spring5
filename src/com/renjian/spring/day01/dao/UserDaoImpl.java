package com.renjian.spring.day01.dao;

public class UserDaoImpl implements Userdao {
    @Override
    public void update() {
        System.out.println("外部bean注入成功！！！！");
    }
}
