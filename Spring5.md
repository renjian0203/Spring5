# Spring5框架学习

## 1.Spring概念

### 1.1简介

1.Spring是一个轻量级（需要的jre包较少）的开源的JavaEE框架

2.用于解决企业应用开发的复杂性

3.Spring中两个核心部分：IOC和Aop

IOC：控制反转，把创建对象的过程交给spring进行管理

Aop：面向切面编程，不改变源代码的情况下，增强相应代码的功能

4.Spring框架的特点：

①方便解耦，简化开发

②Aop编程支持

③方便程序测试

④方便与其他框架进行整合

⑤方便进行事务操作

⑥降低API开发难度

## 2.IOC容器

## 2.1 IOC操作bean对象（基于XML方式）

### 2.1.1 IOC基本概念

1.基本概念：  ioc（控制反转），把对象创建和对象之间的调用过程交给Spring进行管理，（IOC可以）降低耦合度

2.底层原理：xml解析，反射，工厂模式

①IOC底层原理（进一步降低耦合度）

![image-20220305160959447](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220305160959447.png)

②IOC接口（两个）

​         1.IOC思想基于IOC容器实现，IOC容器底层就是对象工厂

​           （BeanFactory）：IOC容器的基本实现，是Spring内部使用的接口，一般不提供给开发人员使用

​                             *加载配置文件的时候是不会创建对象，在获取的时候才创建

​              (ApplicationContext)：BeanFactory的一个子接口。相比父接口，他提供了更多、更强大的功能。一般是由开发者使用的

​                             *加载配置文件的时候就会将（配置文件中）对象创建

​             为啥还是推荐使用第二个：一般Spring框架都会结合服务器使用，是保证在服务器启动时将对象创建，节约时间

​        2.ApplicationContext接口实现类介绍：![image-20220305162253047](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220305162253047.png)

​                 两个类的区别在于：第一个需要给xml配置文件的绝对路径（盘符），第二个是相对路径

### 2.1.2 IOC操作bean对象

​      * Bean管理（基于xml）

​                 定义：==1.基于xml方式创建对象，==

![image-20220305163006607](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220305163006607.png)

​							<bean>标签常用属性介绍：

​												*id：唯一标识  *class：类的全路径  name：与id相同，但他可以包含特殊字符  

                           Spring解析xml文件创建对象是调用相应的无参构造方法创建

​           	==2.基于xml注入属性（给类的属性赋值）==

​                    DI：依赖注入，就是注入属性

 			  3.第一种注入方式：使用set方法进行注入

​									![image-20220305170321858](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220305170321858.png)

​						--set方法注入的另一种写法:使用p名称空间注入，可以简化xml配置方式的代码：

先添加一个名称空间								![image-20220305170755084](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220305170755084.png)

```java
<bean id="book" class="com.renjain.spring.Book" p:name="倚天屠龙记" p:num="584">

</bean>
```

​					第二种种方式：使用有参构造进行注入

​									![image-20220305170337637](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220305170337637.png)

​            ==4.IOC操作Bean管理（xml注入其他属性：集合，对象）== 

​					（1）字面量:就是属性的一个值

​										*null值：value值不写，另外用一个null标签

​										*属性值包含特殊符号：<![CDATA[内容]]>

​			==5.注入属性-外部bean==

​               从dao层引入对象就叫做引入外部bean：ref就能给引用的属性进行赋值，ref就是注入外部bean 

```xml
<bean id="userService" class="com.renjain.spring.service.UserService">
   <!--         注入userDao对象-->
    <property name="userdao" ref="userDao"></property>
</bean>
<bean id="userDao" class="com.renjain.spring.dao.UserDaoImpl"></bean>
```

​			==6.注入属性-内部bean==

```xml
<bean id="emp" class="com.renjain.spring.bean.Emp">
    <property name="name" value="张三"></property>
    <property name="sex" value="男"></property>
    <property name="dept">
        <bean id="dept" class="com.renjain.spring.bean.Dept">
            <property name="name" value="业务部门"></property>
        </bean>
    </property>
</bean>
```

​			==7.注入属性-级联赋值==

```xml
写法一：
<!--级联赋值-->
    <bean id="emp" class="com.renjain.spring.bean.Emp">
<!--        设置两个属性值-->
        <property name="name" value="张三"></property>
        <property name="sex" value="男"></property>
        <property name="dept" ref="dept"></property>
    </bean>
    <bean id="dept" class="com.renjain.spring.bean.Dept">
        <property name="name" value="安保部门"></property>
    </bean>
```

 

```xml
写法二：
<!--级联赋值-->
    <bean id="emp" class="com.renjain.spring.bean.Emp">
<!--        设置两个属性值-->
        <property name="name" value="张三"></property>
        <property name="sex" value="男"></property>
        <property name="dept" ref="dept"></property>
        <property name="dept.name" value="技术部"></property>
    </bean>
    <bean id="dept" class="com.renjain.spring.bean.Dept">
        <property name="name" value="安保部门"></property>
    </bean>
```

​          ==8.注入数组类型属性==

```xml
<property name="course">
    <array>
        <value>数学</value>
        <value>外语</value>
        <value>日语</value>
    </array>
</property>
```

 		==9.注入List结合类型属性==

```xml
<property name="list">
    <list>
        <value>美国</value>
        <value>日本</value>
        <value>中国</value>
    </list>
</property>
```

​		==10.注入Map集合==

```xml
<property name="maps">
    <map>
        <entry key="用户名" value="renjian"></entry>
        <entry key="性别" value="男"></entry>
    </map>
</property>
```

​		==11.在集合中注入对象属性==

```xml
<property name="courses">
    <list>
        <ref bean="chinese"></ref>
        <ref bean="math"></ref>
    </list>
</property> 

<bean id="chinese" class="com.renjain.spring.collectionType.Course">
        <property name="name" value="语文"></property>
    </bean>

    <bean id="math" class="com.renjain.spring.collectionType.Course">
        <property name="name" value="高数"></property>
    </bean>
```

​			==12.将相关的集合属性注入抽取为公共属性（就任何对象都可以用）==

```xml
<!-- 第一步先引入名称空间-->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:util="http://www.springframework.org/schema/util"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
<!--第二步-->
    <!--        1. 提取list集合类型属性注入-->
    <util:list id="bookList">
        <value>《简爱》</value>
        <value>《海底两万里》</value>
        <value>《放学后》</value>
    </util:list>
<!--2.提取list集合类型属性注入使用-->
    <bean id="book" class="com.renjain.spring.collectionType.Book">
        <property name="list" ref="bookList"></property>
    </bean>
```

​			

==13.Spring中的内置Bean(Factorybean)==

​				**<u>注意:Spring有两种类型的Bean:一种是我们自己创建的普通bean,第二钟bean(FactoryBean).</u>**

​						 区别在于:普通bean在标签中定义为什么类,返回的就是什么类型,而FactoryBean相当于一个bean工厂,可以返回其他类型的bean.

​			==14.工厂bean的创建==

​				第一步:创建类,将这个类作为工厂bean,实现接口FactoryBean

​				第二步:实现接口里面的方法,在实现的方法中定义返回的bean类型

### 2.1.3 bean的作用域

①在Spring里面,可以设置创建的bean实例是单实例还是多实例

②在Spring里面,默认是单实例对象

③如何设置是单实例还是多实例

​				在spring配置文件bean标签里面有属性(scope),用于设置单实例还是多实例:

​					1.默认值:singleton,表示单实例对象 

​					2.prototype,表示多实例对象

​				==两者的区别在于:单实例对象在Spring加载配置文件时就会创建单实例对象,而非单实例对象,不是加载配置文件时候创建,在调用getBean方法时创建多实例对象==

### 2.1.4 bean的生命周期

#### 2.1.4.1 bean的简单生命周期

1.生命周期:从对象创建到销毁的过程

2.bean的生命周期:

​	①通过构造器创建bean实例(无参构造)

​	②为bean的属性设置值和对其他bean引用 (调用set方法)

​	③调用bean里的初始化方法(需要进行配置:bean对应的属性) 

​	④bean可以使用,对象获取到了

​	⑤当容器关闭时,调用bean的销毁方法(需要进行配置销毁方法:bean对应的属性)

#### 2.1.4.2 bean的完整生命周期

2.bean的生命周期:

​	①通过构造器创建bean实例(无参构造)

​	②为bean的属性设置值和对其他bean引用 (调用set方法)

​	==③把bean实例传递到bean后置处理器的方法==

​	③调用bean里的初始化方法(需要进行配置:bean对应的属性) 

​	==④把bean实例传递bean后置 处理器的方法==

​	④bean可以使用,对象获取到了

​	⑤当容器关闭时,调用bean的销毁方法(需要进行配置销毁方法:bean对应的属性)

处理器创建方法:创建一个普通类==实现BeanPostProcessor接口==,将该类到xml配置相关的属性

### 2.1.5 xml自动装配

==实际运用中通常使用注解方式实现自动装配==

​	1.手动装配:在xml文件中,创建相应的bean对象后,根据其相应的属性给其类的属性进行赋值

​	2.自动装配:根据指定装配规则(属性名称或者属性类型),Spring自动将匹配的属性进行注入

​	3.自动装配的一些注意事项:

​			①bean标签中的autowire属性设置自动装配的类型:①autowire="byName",②autowire="byType"

```xml
<bean autowire="byName" id="emp" class="com.renjain.spring.autowire.Emp">
```

在通过byName属性进行自动注入时,被注入对象的id属性需与需要注入的类的属性名相同:

```java
public class Emp {
    private Dept dept;

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "dept=" + dept +
                '}';
    }
}
```

```xml
<bean autowire="byType" id="emp" class="com.renjain.spring.autowire.Emp"> 
</bean>
    <bean id="dept" class="com.renjain.spring.autowire.Dept"></bean>
```

通过byType属性进行注入:当有该类型的其他id时,无法识别该注入哪个属性	

```xml
<bean autowire="byType" id="emp" class="com.renjain.spring.autowire.Emp"></bean>
```

```xml
<!--此时将会报错-->
<bean autowire="byType" id="emp" class="com.renjain.spring.autowire.Emp">
</bean>
    <bean id="dept" class="com.renjain.spring.autowire.Dept"></bean>
<bean id="dept1" class="com.renjain.spring.autowire.Dept"></bean>
```

### 2.1.6 引入外部属性文件

第一步：先创建一个context的名称空间

第二步：通过相应的标签引入外部文件

~~~xml
<context:property-placeholder location="jdbc.properties"></context:property-placeholder>
~~~

第三步：将相应的value改为${配置文件中的属性名 }取出相应的值

```xml
<property name="driverClassName" value="${prop.driverClass}"></property>
```



## 2.2 IOC Bean管理（基于注解）

​	①注解：注解是代码特殊标记，格式：@注解名称（属性名称=属性值，.....）

​	②注解可以放在方法、属性、类上..

​	③使用注解的目的：简化xml配置

### 2.2.1Spring针对Bean管理中创建对象提供的注解

①@Component ：普通的注解，一般都可以使用

②@Service ：一般用在业务逻辑层	

③@Controller ：一般用在控制层

④@Repository ：一班用在Dao层（持久层）

​	==注意：四个注解可以使用，只是编程习惯而已，功能都是一样的，都可以用来创建bean对象==

### 2.2.2基于注解的方式创建对象

​	第一步：引入一个aop的相关依赖（jar包）：spring-aop-5.2.6.RELEASE.jar

​	第二步： 开启组件扫描：告诉Spring这个组件哪些类需要进行注解开发，让他去扫描这些类，只有开启扫描后，Spring在知道去哪些包中去寻找相应的注解：

​	第三步：创建类，在类上边添加对象注解

```java
//在注解里面value属性可以胜省略，默认值就是将类名的首字母改为小写
//@Service：value值=userService
@Service(value = "userService")  //与之前bean标签中id=”userService“，class写法是一样的
public class UserService {
    public void add(){
        System.out.println("add()......");
    }

}
```

​	执行的原理：当执行解析xml文件（加载配置文件）时，扫描到

 ~~~ xml
 <context:component-scan base-package="com.renjain"></context:component-scan>//开启组件扫描
 ~~~

就开始扫描相应包下的类，然后进行注解检查，如果有注解，就将对象进行创建，然后输出。

==开启组件扫描的方式：==

​	第一步：在配置文件中引入相应的名称空间：context，

​	第二步：通过相应的属性设置扫描的范围：

```xml
<!--    开启组件扫描:
        1.如果扫描多个包，多个包之间用逗号隔开
        2.扫描包的上层目录
-->
    <context:component-scan base-package="com.renjain"></context:component-scan>
```

==开启组件扫描的其他细节：==

```xml
<!--    示例1
   use-default-filters="false"表示现在不使用默认的filter，自己配置filter
   context:include-filter,设置扫描哪些内容，type="annotation"表示只扫描带注解的类
    expression="org.springframework.stereotype.Controller"表示扫描Controller注解的类
-->
    <context:component-scan base-package="com.renjain.spring.dao" use-default-filters="false">
        <context:include-filter type="annotation"
                                expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
```

```xml
<!--示例二
扫描包下所有的类，
       context:exclude-filter 表示不包含即type="annotation"
       expression="org.springframework.stereotype.Controller"，表示，不扫描该包下带Controller注解的类
-->
    <context:component-scan base-package="com.renjain.spring.dao">
        <context:exclude-filter type="annotation"
                                expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
```

### 2.2.3基于注解的方式实现属性注入

配置相应的context名称空间，然后开启组件扫描和设置扫描的范围后：

第一步：先将需要进行注解方式创建对象的类添加上相应的注解

第二步：让后在某个需注入值的属性上添加相应的注入属性的注解

```java
//在注解里面value属性可以胜省略，默认值就是将类名的首字母改为小写
@Service(value = "userService")  //与之前bean标签中id=”userService“，class写法是一样的
public class UserService {

//    定义dao类型的属性
//    不需要添加set方法
//    添加注入属性注解
    @Autowired //根据类型进行注入
    private UserDao userDao;
    public void add(){
        System.out.println("add()......");
        userDao.add();
    }

}
```

第三步：读取配置文件

与属性注入有关的四个注解：

①@AutoWired：根据属性类型进行自动装配

②Qualifier  ：根据属性名称进行注入

​	 该注解需要与@AutoWired结合使用，

​		==被注入的类或者需要的类：==

```java
@Repository
public class UserDaoImpl implements UserDao{
    @Override
    public void add() {

        System.out.println("成功了！！！");
    }
}
```

```java
//在注解里面value属性可以胜省略，默认值就是将类名的首字母改为小写
@Service(value = "userService")  //与之前bean标签中id=”userService“，class写法是一样的
public class UserService {

//    定义dao类型的属性
//    不需要添加set方法
//    添加注入属性注解
    @Autowired //根据类型进行注入
    @Qualifier("userDaoImpl")//根据名称进行注入
    private UserDao userDao;
    public void add(){
        System.out.println("add()......");
        userDao.add();
    }

}
```

当通过类型进行注入时，倘若有多种该类型不同名的变量，此时Spring就无法的值知具体注入哪个属性，这时，通过名称即@Qualifier("userDaoImpl")，参数是具体名称，则Spring便知道了具体注入哪个属性。

③Resource：  可以根据类型注入，也可以根据名称注入，但是这个注解时javax包下的，并非Spring框架下的

```java
//   @Resource //根据类型进行注入
@Resource(name = "userDaoImpl")
    private UserDao userDao;
    public void add(){
        System.out.println("add()......");
        userDao.add();
    }
```

④@value：注入普通类型属性

```java
@Value(value = "abc")
private String name;
```

### 2.3.4 完全注解开发

即：不需要xml文件，开启组件扫描也通过注解方式做到

==步骤：==

第一步：创建配置类，替代xml配置文件

@Configuration  //告诉Spring该类作为配置类，代替xml配置文件

@ComponentScan(basePackages = "com.renjain.spring") //相当于开启组件扫描

```java
@Configuration  //告诉Spring该类作为配置类，代替xml配置文件
@ComponentScan(basePackages = "com.renjain.spring")
public class SpringConfig {
    
}
```

第二步:将相应的属性加上相关注入属性注解，之后读取配置文件，获取对象：

AnnotationConfigApplicationContext(SpringConfig.class);使用的是该类，参数传入的是配置类的字节码文件

```java
@org.junit.Test
public void test(){
    //加载配置类
    ApplicationContext context=
            new AnnotationConfigApplicationContext(SpringConfig.class);
    UserService userService = context.getBean("userService", UserService.class);
    System.out.println(userService);
    userService.add();
}
```

## 3.Aop 

​	==什么是AOP：面向切面编程，利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各个部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。==

  通俗的来讲，就是在不修改源代码的前提下，添加新的功能

### 3.1Aop底层原理

​		==Aop底层使用的=是动态代理实现不修改源代码的情况下，增强相应的功能：==

​		AOP底层的动态代理有两个种情况：①有接口的情况 ②没有接口的情况

​		所有通过动态代理时创建的代理对象都不是new出的，但是功能和当前需要增强的方法相同，可以在这个基础上添加新功能

#### 3.1.1 动态代理（有接口的情况）

​		有接口的情况下使用的是==JDK动态代理==

​				有接口的情况下创建接口实现类的代理对象，代理对象来增强之前的方法![image-20220308201140231](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220308201140231.png)

==JDK动态代理：==

需要使用Proxy这个类，在java.lang.reflect.Proxy

![image-20220308202321559](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220308202321559.png)

​	1.调用newProxyInstance方法

​		方法的三个参数：

​				第一个：类加载器

​				第二个：增强方法所在的类，这个类实现的接口，支持多个接口

​				第三个：实现这个接口InvocationHandler（匿名内部类或者新创建一个类实现该接口）,创建代理对象，写增强方法

==示例代码如下：==

```java
public class JDKProxy {
    public static void main(String[] args) {
//       创建接口实现类代理对象
        Class [] interfaces={UserDao.class};
//        匿名内部类的方式实现
//        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), inter, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        });

        UserDaoImpl userDao = new UserDaoImpl();
        UserDao dao = (UserDao)Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        String add = dao.update("name");
        System.out.println("result:"+add);

    }
    //创建代理对象代码
    static class UserDaoProxy implements InvocationHandler{

        //1.把创建的是谁的代理对象，把谁传过来，写成Object是了程序的通用性
        //有参构造
        private Object obj;
        public UserDaoProxy(Object obj){
            this.obj=obj;
        }


        //编写增强的代码
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           //方法执行之前
            System.out.println("方法执行之前...."+method.getName()+"传递的参数.."+ Arrays.toString(args));

            //被增强的方法执行
            Object invoke = method.invoke(obj, args);


            //方法执行之后
            System.out.println("方法执行之后...."+obj);

            return invoke;
        }
    }
}
```

  main方法就表示在实际可运用中涉及的方法增强：

#### 3.1.2动态代理（没有接口的情况）

​		没有接口的情况下使用的是==CGLIB动态代理==

​			1.先创建一个当前类子类的代理对象![image-20220308201659775](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220308201659775.png)

### 3.2 AOP主要术语

​	1.连接点：

​			在需要增强的类中，哪些方法可以增强；哪些方法就成为链接点

​	2.切入点：

​			实际被增强的方法，称为切入点

​	3.通知（增强）：

​			实际增强的逻辑部分，通知有多种类型，主要的五种：

​				①前置通知

​				②后置通知

​				③环绕通知	

​				④异常通知

​				⑤最终通知   finally类似

​	4.切面：是一个动作，把通知应用到切入点的过程

### 3.3AOP操作（准备）

​	1.Spring框架一般都是基于AspectJ实现AOP操作

​			*AspectJ不是Spring组成成分，是独立的AOP框架，一般来讲把Aspect J和Spring框架一起使用，进行AOP操作

​	2.基于Aspect J实现AOP操作

​		①基于XML配置文件

​					

​		②基于注解方式（使用较多）

​					

​	3.引入相关的依赖之后，设置切入点表达式

​			①切入点表达式作用：知道对哪个类中的哪个方法进行增强

​			②切入点表达式的语法结构：

​							==execution( [权限修饰符] [返回类型] [类全路径] [方法名称] [参数列表] )==

​		举例一： 对com.renjian.dao.BookDao类的add方法进行增强

​							execution(* com.renjian.dao.BookDao.add(...)):返回类型可以省略



​		举例二： 对com.renjian.dao.BookDao类的所有方法进行增强

​							execution(* com.renjian.dao.BookDao.*(...)):返回类型可以省略



​		举例三： 对com.renjian.dao的所有类的所有方法进行增强

​							execution(* com.renjian.dao.* .*(...)):返回类型可以省略

### 3.4基于注解的方式实现对（AspectJ）的操作

​	1.创建类，定义方法

```java
//被增强类
public class User {
    public void add(){
        System.out.println("add.....");
    }
}
```

​	2.创建增强类,编写增强逻辑

```java
//增强的类
public class UserProxy {
    public void before(){
        System.out.println("before....");
    }
}
```

​	3.进行通知的配置

​			①在Spring配置文件中，开启组件扫描

```java
<!--    开启注解扫描-->
    <context:component-scan base-package="com.renjain.spring.aopanno">

    </context:component-scan>
```

​			②使用注解创建User和USerProxy对象

​				利用IOC容器的注解创建对象：打上相应的注解

​			③在增强类的上边添加注解@Aspect：表示让这个类生成代理对象

​			④在Spring配置文件中开启生成代理对象

```java
<!--    开启Apsect生成代理对象
就是到类中寻找@Apsect注解，如果有就生成代理对象
-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```

​	4.配置不同类型的通知：设置该增强的逻辑代码相对于原方法何时执行

​				①在增强类的里面，在作为通知方法上边添加通知类型(@before等)注解并且使用切入点表达式配置相应增强的位置 

```java
//增强的类
@Component
@Aspect //生成代理对象
public class UserProxy {
   //  前置通知
    @Before(value = "execution(* com.renjain.spring.aopanno.User.add(..))")
   //@Before注解表示作为前置通知
    public void before(){
        System.out.println("before........");
    }

    //最终通知
    @After(value = "execution(* com.renjain.spring.aopanno.User.add(..))")
    public void after(){
        System.out.println("after......");
    }

    //返回通知(后置通知)
    @AfterReturning(value = "execution(* com.renjain.spring.aopanno.User.add(..))")
    public void afterReturning(){
        System.out.println("AfterReturning......");
    }

    //异常通知
    @AfterThrowing(value = "execution(* com.renjain.spring.aopanno.User.add(..))")
    public void afterThrowing(){
        System.out.println("AfterThrowing......");
    }

    //环绕通知
    @Around(value = "execution(* com.renjain.spring.aopanno.User.add(..))")
    public void arund(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前......");

        //被增强的方法执行
        proceedingJoinPoint.proceed();

        System.out.println("环绕之后......");
    }
}
```

最终简单涉及到的xml配置文件中的内容为：

```xml
<!--    开启注解扫描-->
    <context:component-scan base-package="com.renjain.spring.aopanno">
    </context:component-scan>

<!--    开启Apsect生成代理对象
就是到类中寻找@Apsect注解，如果有就生成代理对象
-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```

​	5.相同切入点抽取

​			相同切入点：也就是切入点表达式相同，所增强的方法相同

```java
//相同切入点抽取
@Pointcut("execution(* com.renjain.spring.aopanno.User.add(..))")
public void samePoint(){

}
//之后，如果相同切入点，就直接在相应注解中调用相应的方法即可
```

​	6.有多个增强类同时对一个切入点进行增强时的判优问题

​	此时@Order（数值）可以为同时增强类的@Order注解中设置相应的参数，数字越小，优先级越高，

​		==需要注意的是：每个增强方法都会执行，只是顺序不同==

### 3.5 基于XML配置文件的方式实现对（AspectJ）的操作

​		1.创建两个类，增强类和被增强类，创建相应的方法

```java
//被增强类
public class Book {
    public void buy(){
        System.out.println("buy.............");
    }
}
```

```java
//增强类
public class BookProxy {
    public void before(){
        System.out.println("before.........");
    }
}
```

​		2.在spring配置文件中创建两个对象

```xml
<!--    创建对象-->
    <bean id="book" class="com.renjain.spring.aopXML.Book"></bean>
    <bean id="bookProxy" class="com.renjain.spring.aopXML.BookProxy"></bean>
```

​		3.在Spring配置文件中配置切入点

```xml
<!--   配置AOP增强-->
    <aop:config>
<!--      配置切入点  -->
        <aop:pointcut id="p" expression="execution(* com.renjain.spring.aopXML.Book.buy(..))"/>
<!--        配置切面-->
            <aop:aspect ref="bookProxy">
<!--                增强额逻辑代码运用的具体方法-->
                <aop:before method="before" pointcut-ref="p"></aop:before>
            </aop:aspect>

    </aop:config>
```

### 3.6 AOP完全注解开发

1.先创建增强类和被增强类，利用IOC的注解形式创建对象，并且为加强类添加@Aspect注解，该注解代表该类需要创建代理对象

```java
//被增强类
@Component
public class Book {
    public void buy(){
        System.out.println("我是固有的方法......");
    }
}
```

```java
//增强类
@Component
@Aspect
public class BookProxy {

    //设置切入点
    @Before(value = "execution(* com.renjain.spring.priactice.wanquanzhujie.Book.buy(..))")
    public void before(){
        System.out.println("我是前置增强方法......");
    }

}
```

2.创建替代配置文件的类，@ComponentScan(basePackages = {"com.renjain.spring.priactice"})
设置扫描的范围

==@EnableAspectJAutoProxy(proxyTargetClass = true) 设置开启生成代理对象==

```java
@Component
@ComponentScan(basePackages = {"com.renjain.spring.priactice"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Config {
}
```

3.完全注解开发中，让spring开启组件扫描的方式不同

```java
ApplicationContext context=
        new AnnotationConfigApplicationContext(Config.class);
Book book = context.getBean("book", Book.class);
book.buy();
```

### 3.7 完全XML文件开发

```java
//被增强类
public class Book {
    public void add(){
        System.out.println("我是原有方法");
    }
}
```

```java
//增强类
public class Proxy {
    public void wide(){
        System.out.println("我是增强方法！！！");
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

<!--    1.配置context名称空间-->

<!--    2.创建相应的对象-->
    <bean id="book" class="com.renjain.spring.priactice.doxml.Book"></bean>
    <bean id="proxy" class="com.renjain.spring.priactice.doxml.Proxy"></bean>
<!--    3.设置aop增强切点-->
    <aop:config>
        <aop:pointcut id="way" expression="execution(* com.renjain.spring.priactice.doxml.Book.add(..))"/>
<!--        配置切面-->
        <aop:aspect ref="proxy">
            <aop:before method="wide" pointcut-ref="way"></aop:before>
        </aop:aspect>
    </aop:config>

</beans>
```

需要注意的是，第三步中的设置切点中的id不能与创建对象时的id重名，否则在进行配置前面时会报错

## 4.JdbcTemplate

### 4.1JdbcTemplate简介

​		1.式Spring框架对JDBC进行的封装，使用JdbcTemplate可以方便实现对数据库的操作

### 4.2 使用JdbcTemplate操作数据库

​			==准备工作：==

​		1.	导入相关的jar包：

​				![image-20220310153538630](C:\Users\ren jian\AppData\Roaming\Typora\typora-user-images\image-20220310153538630.png)

​	2.在配置文件中‘配置相关的信息:数据库连接池，JdbcTemplate等

```xml
    <context:component-scan base-package="com.renjain.spring"></context:component-scan>

    <!-- 数据库连接池 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
          destroy-method="close">
        <property name="url" value="jdbc:mysql:///renjian" />
        <property name="username" value="root" />
        <property name="password" value="mmpmmp123" />
        <property name="driverClassName" value="com.mysql.jdbc.Driver" />
    </bean>

<!--    创建JdbcTemplate对象-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
<!--        注入DataSource,注意使用的是set方法进行注入-->
        <property name="dataSource" ref="dataSource"></property>
     </bean>
```

### 4.3 JdbcTemplate中操作数据的相关方法

​		==模拟MVC架构==

```java
//dao层接口：
public interface UserDao {

    public void insert(User user);

    public void  update(User user);

    public void  delete(String id);

    public void  onlyOne();

    public void  reObj(String id);

    public void  reObjs();

    public void batchAdd(List<Object[]> list);

    public void batchUpdate(List<Object[]> list);

    public void batchDelete(List<Object[]> list);
}
```

```java
//dao层接口相关实现
@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(User user) {
        String sql="insert into t_book values(?,?,?)";
        int update = jdbcTemplate.update(sql, user.getUser_id(), user.getUsername(), user.getUser_status());
        System.out.println("插入数据："+update);
    }

    @Override
    public void update(User user) {
        String sql = "update t_book set username=?,user_status=? where user_id=?";
        int update = jdbcTemplate.update(sql, user.getUsername(), user.getUser_status(), user.getUser_id());
        System.out.println("修改数据："+update);
    }

    @Override
    public void delete(String id) {
        String sql = "delete from t_book where user_id=?";
        int update = jdbcTemplate.update(sql,id);
        System.out.println("按id删除："+update);
    }

    @Override
    public void onlyOne() {
        String sql = "select count(*) from t_book";
        Integer integer = jdbcTemplate.queryForObject(sql, int.class);
        System.out.println("求表中总记录数："+integer);
    }

    @Override
    public void reObj(String id) {
        String sql = "select * from t_book where user_id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        System.out.println("查询返回一个对象："+user);
    }

    @Override
    public void reObjs() {
        String sql = "select *from t_book";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        System.out.println("返回一个集合："+users);
    }

    @Override
    public void batchAdd(List<Object[]> list) {
        String sql="insert into t_book values(?,?,?)";
        int[] ints = jdbcTemplate.batchUpdate(sql, list);
        System.out.println(Arrays.toString(ints));
    }

    @Override
    public void batchUpdate(List<Object[]> list) {
        String sql = "update t_book set username=?,user_status=? where user_id=?";
        int[] ints = jdbcTemplate.batchUpdate(sql, list);
        System.out.println(Arrays.toString(ints));

    }

    @Override
    public void batchDelete(List<Object[]> list) {
        String sql = "delete from t_book where user_id=?";
        jdbcTemplate.batchUpdate(sql,list);
    }
}
```

```java
//service类
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

//    插入相关信息
    public void insert(User user){
        userDao.insert(user);
    }
//    根据主键修改相关信息
    public void update(User user){
        userDao.update(user);
    }
//    根据主键删除信息
    public void delete(String id){
        userDao.delete(id);
    }
//    返回单个值：总记录条数
    public void onlyOne(){
        userDao.onlyOne();
    }
//    返回一行记录，JdbcTemplate自动将其封装成一个对象
    public void reObj(String id){
        userDao.reObj(id);
    }
//    返回集合类型，即返回多个对象
   public void reObjs(){
       userDao.reObjs();
   }

//   批量添加数据
    public void batchAdd(List<Object []> list){
        userDao.batchAdd(list);
    }

//    批量修改数据
    public void batchUpdate(List<Object []> list){
        userDao.batchUpdate(list);
    }

//    批量删除数据
    public void batchDelete(List<Object []> list){
        userDao.batchDelete(list);
    }
}
```

```java
//相关实体类
public class User {
    private String user_id;
    private String username;
    private String user_status;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getUser_status() {
        return user_status;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", user_status='" + user_status + '\'' +
                '}';
    }

    public User() {
    }

    public User(String user_id, String username, String user_status) {
        this.user_id = user_id;
        this.username = username;
        this.user_status = user_status;
    }
}
```

总结：接口不能直接new对象，所以不用在接口上方加上自动创建对象的注解。

### 4.4 JdbcTemplate中的事务

1. 事务简介：事务是数据库操作的基本单元，逻辑上的一组操作，要么都成功，如果有一个失败，那么所有操作均败。
2. 典型场景：银行转账
3. 四大特性：①原子性②一致性③隔离性④持久性

#### 4.4.1 Spring事务管理

​	1、事务理论上可以加到（web层，service层，dao层）中 的任何一层，但一般都添加到Service层（业务逻辑层）

​	2、Spring中的事务一般有两种方式：编程式事务（很不方便）和声明式事务管理（常用）。

​	3、声明式事务管理：

​			①基于注解方式；

​			②基于xml配置文件方式；

​	4、 在Spring进行声明式事务管理，底层使用AOP

​	5、Spring 事务管理API

​			①Spring提供一个接口，代表事务管理器，这个接口针对不同的框架提供不同的实现类，这个接口：PlatformLoggingMXBean

#### 4.4.2注解方式实现声明式事务管理

​	1、在spring配置文件中配置事务管理器

​	2、在spring配置文件中，开启事务注解

​			①在spring配置文件中引入名称空间tx

​			②使用配置的名称空间开启事务注解

​	3、在service类上面或者service类里面的方法上面添加事务的注解

​				①@Transactional这个注解可以添加到类上边，也可以添加到具体方法上边

​				②如果将@Transactional注解添加到类上边，表示，该类的所有的方法都添加事务

​				③如果将@Transactional注解添加到单个方法上，表示只是这一个方法开启事务



```java
DataSourceTransactionManager该类用于创建事务管理器
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
                            http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

	//配置数据库连接池
    <!-- 数据库连接池 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
          destroy-method="close">
        <property name="url" value="jdbc:mysql:///renjian" />
        <property name="username" value="root" />
        <property name="password" value="mmpmmp123" />
        <property name="driverClassName" value="com.mysql.jdbc.Driver" />
    </bean>
    
<!--    创建事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<!--        注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>

<!--    开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
</beans>
```

```java
@Service
@Transactional
public class UserService {

    //注入dao
    @Autowired
    private UserDao userDao;
    public void accountMoney(){
//       try{

           //1.开启事务
           //renjian少100；
               userDao.reduceMoney();

           //2.执行业务逻辑

           //模拟异常
           int i=10/0;

           //zhang多100；
           userDao.addMoney();

           //没有异常，提交事务

//       }catch (Exception e){

           //4.出现异常，事务回滚

//       }
    }
}
```

最后在需要开启事务的或者方法上面打上@Transactional注释

#### 4.4.3声明事务管理参数配置

​	1、上述是在service类上面添加注解@Transactional，在这个注解里面可以配置事务相关参数

​	2、常用参数说明：

​		①propagation:事务传播行为

​				传播行为：多事务方法直接进行调用，这个过程中事务是如何进行管理的，简单说来就是当一个事务方法调用另外一个事务方法，或一个加了事务的事务方法调用没加事务的方法时，该怎么操作，就叫做事务传播行为。![事务传播行为](D:\Java\框架学习\笔记\笔记\分析图\事务\事务传播行为.bmp)

​				事务方法：对数据库表数据进行变化的操作：查询操作不算事务方法。				

​		②isolation：事务的隔离级别

![事务隔离级别](D:\Java\框架学习\笔记\笔记\分析图\事务\事务隔离级别.bmp)

​					事务存在一个特性叫做隔离性，指的是多事务操作之间不会互相影响，如果不考虑隔离性会产生很多问题：三个读问题：脏读，不可重复读，虚读（幻读）。 

​				通过设置事务的隔离级别就可以解决此类问题。以下为设置传播行为是REQUIRED，隔离级别为可重复读REPEATABLE_READ

```java
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
public class UserService {
```

​		③timeout：超时时间，即设置事务多长时间内必须提交，超过该时间还未提交，进行事务的回滚操作。

​				默认超时间为-1，设置时间是以秒为单位进行计算

```java
@Service
@Transactional(timeout = -1,propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
```

​		④readOnly：是否只读，读：查询操作，写：添加修改删除操作

​				readOnly默认值为false，表示既可以进行查询，又可以进行添加修改删除操作，可以手动设置值为true，之后只能查操作。

​		⑤rollbackFor：回滚，设置出现哪些异常进行事务回滚

​		⑥noRollbackFor：不回滚，设置出现哪些异常不进行事务回滚

#### 4.4.4 xml配置文件方式实现声明事务管理

​	1、配置事务管理器

```xml
<!-- 数据库连接池 -->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
      destroy-method="close">
    <property name="url" value="jdbc:mysql:///renjian" />
    <property name="username" value="root" />
    <property name="password" value="mmpmmp123" />
    <property name="driverClassName" value="com.mysql.jdbc.Driver" />
</bean>

<!--   1. 创建事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<!--        注入数据源-->
        <property name="dataSource" ref="dataSource"></property>
    </bean>

```

​	2、配置通知

```xml
<!--        2.配置通知-->
    <tx:advice id="txadvice">
<!--        配置事务的一些相关参数-->
        <tx:attributes>
<!--            指定那种规则的方法上添加事务-->
            <tx:method name="accountMoney" propagation="REQUIRED"/>
<!--            <tx:method name="account*"/>-->
        </tx:attributes>
    </tx:advice>
    
```



​	3、配置切入点和切面

```xml
<!--        3.配置切入点和切面-->
    <aop:config>
<!--        配置切入点-->
        <aop:pointcut id="pt" expression="execution(* com.renjain.spring.service.UserService.*(..))"/>
        <aop:advisor advice-ref="txadvice" pointcut-ref="pt"></aop:advisor>
    </aop:config>
```

#### 4.4.5 完全注解方式实现声明事务管理

​	==主要是配置类==

@Bean注解表示创建实例的注解

```java
@Configuration  //表示该类为配置类的注解
@ComponentScan(basePackages ={"com.renjain.spring"}) //设置扫描范围
@EnableTransactionManagement //该注解为：开启书事务注解
public class SpringConfig {
    //配置数据库连接池对象
    @Bean
    public DruidDataSource getDruidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///renjian");
        dataSource.setUsername("root");
        dataSource.setPassword("mmpmmp123");
        return dataSource;
    }
    
    //配置JdbcTemplate对象，需要注意的是，参数为数据库连接池，可以直接获取的原因是，通过IOC容器获得，因为，前一个方法已经创建过，此时的数据库连接池对象，在IOC容器中
     @Bean
    public JdbcTemplate getJdbcTemplate(DruidDataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
    //配置DataSourceTransactionManager事务管理器对象
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DruidDataSource dataSource){
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }
}
```



## 6.Spring5的新特性

### 6.1 基本介绍

​		1、 整个Spring 5框架的代码基于Java8，运行时兼容JDK9，许多不建议使用的类和方法在代码库中删除

​		2、Spring 5.0框架自带了通用恶的日志封装，也可以整合其他的日志框架。

​			①Spring5 已经移除了Log4jConfigListener，官方建议使用Log4j2

​		3、Spring5框架整合Log4j2

​			①引入相关的jar包

​			②创建log4j2.xml配置文件（名字是固定的，不能写成其他）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<!--Configuration后面的status用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，可以看到log4j2内部各种详细输出-->
<configuration status="INFO">
    <!--先定义所有的appender-->
    <appenders>
        <!--输出日志信息到控制台-->
        <console name="Console" target="SYSTEM_OUT">
            <!--控制日志输出的格式-->
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </console>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <!--root：用于指定项目的根日志，如果没有单独指定Logger，则会使用root作为默认的日志输出-->
    <loggers>
        <root level="info">
            <appender-ref ref="Console"/>
        </root>
    </loggers>
</configuration>
```

​			手动输出日志信息：

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLog {
    private static final Logger log= LoggerFactory.getLogger(UserLog.class);

    public static void main(String[] args) {
        log.info("hello");
        log.debug("哈哈");
    }
}
```

### 6.2 Spring5框架核心容器增强

​		1、 @Nullable注解可以使用在方法上，属性上，参数上，表示方法返回值可以为空，即若不会出现空指针异常等情况	

​		2、支持函数式风格的注册对象，并且把对象交给Spring进行管理

```java
//1.创建GenericApplicationContext对象
GenericApplicationContext context=new GenericApplicationContext();
//2.调用context的方法进行对象注册
context.refresh();
context.registerBean("user", User.class,()->new User());
//3.获取在Spring中注册的对象
User user = (User)context.getBean("user");
System.out.println(user);
```

​		3、Junit5测试框架单元整合

​				①整合Junit4

​						首先引入相关依赖，然后创建测试类使用注解方式实现，但是Spring5只支持整合log4j2，log4j，需要将spring版本降低到4

```java
//指定现在所用的Junit版本
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean1.xml") //加载配置文件
public class Junit4t {


    @Autowired
    private UserService userService;

    @Test
    public void test(){
        userService.accountMoney();
    }
    //以下代码就不用再写了
     ApplicationContext context=
                new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
       
```

​				如此一来就不用在手动创建配置类，获取对象了。				

​				②Spring5整合Junit5(只是换了不同的注解而已)

```java
//指定现在所用的Junit版本
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:bean1.xml")
public class Junit5t {


    @Autowired
    private UserService userService;

    @Test
    public void test(){
        userService.accountMoney();
    }

}
```

```java
//指定现在所用的Junit版本
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:bean1.xml")
@SpringJUnitConfig(locations = "classpath:bean1.xml") //使用这一个注解，替代上述的两个注解
public class Junit5t {


    @Autowired
    private UserService userService;

    @Test
    public void test(){
        userService.accountMoney();
    }

}
```

### 6.3 Spring5-webflux(针对前端)

#### 6.3.1 Webflux基本概念



#### 6.3.2 响应式编程

#### 6.3.3 Webflux执行流程和核心API

#### 6.3.4 Spring中如何实现Webflux

​	1.基于注解的编程模型实现



​	2.基于函数式编程模型实现
