package com.renjian.spring.day01.testDemo;

import com.renjian.spring.day01.Book;
import com.renjian.spring.day01.Orders;
import com.renjian.spring.day01.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring5 {
    @Test
    public void testAdd(){
        //1.加载SPring的配置文件
        ApplicationContext context =
                new ClassPathXmlApplicationContext("bean1.xml");
        //2.获取配置创建对象
        User user = context.getBean("user", User.class);

        System.out.println(user);
        user.add();




    }
    @Test
    public void testBook(){
        ApplicationContext context1 =
                new ClassPathXmlApplicationContext("com/renjian/spring/day01/bean1.xml");
        Book book = context1.getBean("book",Book.class);
        System.out.println(book);
        System.out.println(book.name+book.num);
        System.out.println(book.address);
    }
    @Test
    public void testOrders(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/renjian/spring/day01/bean1.xml");
        Orders order = context.getBean("order", Orders.class);
        System.out.println(order);
        System.out.println(order.getOname()+order.getAddress());
    }

}
