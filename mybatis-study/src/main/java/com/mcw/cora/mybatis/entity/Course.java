package com.mcw.cora.mybatis.entity;

/**
 * @Author yibi
 * Date 2019/5/28 21:33
 * Version 1.0
 **/
public class Course {

    private Integer id;
    private String courseName;


    public Course(String courseName) {
        this.courseName = courseName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
