package com.mcw.cora.mybatis.entity;

/**
 * @Author yibi
 * Date 2019/5/28 21:32
 * Version 1.0
 **/
public class Student {
    private Integer id;
    private String studentName;
    private Integer studentAge;

    public Student(String studentName, Integer studentAge) {
        this.studentName = studentName;
        this.studentAge = studentAge;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(Integer studentAge) {
        this.studentAge = studentAge;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
