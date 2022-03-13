package com.renjian.spring.day01.service;


import com.renjian.spring.day01.dao.Userdao;

public class UserService {
    private Userdao userdao;

    public void setUserdao(Userdao userdao) {
        this.userdao = userdao;
    }

    public void add(){
        System.out.println("add().....");
        //原始方式
        //        Userdao userdao = new UserDaoImpl();
        //        userdao.update();
        //现如今通过xml配置完成
        userdao.update();
    }
}
