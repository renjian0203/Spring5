package com.renjian.spring.day01;

/**
 * 使用有参数的构造方法进行属性注入
 */
public class Orders {
    private String oname;
    private String address;

    //有参构造
    public Orders(String oname, String address) {
        this.oname = oname;
        this.address = address;
    }

    public String getOname() {
        return oname;
    }

    public String getAddress() {
        return address;
    }
}
