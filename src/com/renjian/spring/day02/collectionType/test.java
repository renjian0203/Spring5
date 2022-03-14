package com.renjian.spring.day02.collectionType;

import com.renjian.spring.day02.autowire.Emp;
import com.renjian.spring.day02.bean.Orders;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("com/renjian/spring/day02/bean1.xml") ;
        Stu student = context.getBean("student", Stu.class);
        System.out.println(student.toString());

    }
    @Test
    public void test2(){
        ApplicationContext context=
                new ClassPathXmlApplicationContext("com/renjian/spring/day02/bean2.xml");
        Book book1 = context.getBean("book", Book.class);
        Book book2 = context.getBean("book",Book.class);
        System.out.println(book1);
        System.out.println(book2);

    }
    @Test
    public void test3(){
        ApplicationContext context=
                new ClassPathXmlApplicationContext("com/renjian/spring/day02/bean3.xml");
        Course course = context.getBean("myBean", Course.class);
        System.out.println(course);
    }
    @Test
    public void test4(){
        ApplicationContext context=
                new ClassPathXmlApplicationContext("com/renjian/spring/day02/bean4.xml");
        Orders orders = context.getBean("orders", Orders.class);
        System.out.println("6.获取到bean对象...");
        System.out.println(orders);
        ((ClassPathXmlApplicationContext)context).close();
    }
    @Test
    public void test5(){
       ApplicationContext context=
       new ClassPathXmlApplicationContext("com/renjian/spring/day02/bean5.xml");
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp);
    }
}
