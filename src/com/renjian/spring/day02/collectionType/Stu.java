package com.renjian.spring.day02.collectionType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Stu {
    //1.数组类型的属性
    private String[] course;

    //2.list集合类型属性
    private List<String> list;

    //3.map集合类型属性
    private Map<String,String> maps;

    //4.set集合类型属性
    private Set<String> sets;

    //5.集合中放入对象
    private List<Course> courses;

    public void setCourse(String[] course) {
        this.course = course;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public void setSets(Set<String> sets) {
        this.sets = sets;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "course=" + Arrays.toString(course) +
                ", list=" + list +
                ", maps=" + maps +
                ", sets=" + sets +
                ", courses=" + courses.toString() +
                '}';
    }
}
