package com.renjian.spring.day01.testDemo;

import com.renjian.spring.day01.bean.Emp;
import com.renjian.spring.day01.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBean {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/renjian/spring/day01/bean2.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/renjian/spring/day01/bean3.xml");
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp.toString());
    }
    @Test
    public void test3(){
        ApplicationContext context  = new ClassPathXmlApplicationContext("com/renjian/spring/day01/bean4.xml");
        Emp emp = context.getBean("emp",Emp.class);
        System.out.println(emp.toString());
    }
}
