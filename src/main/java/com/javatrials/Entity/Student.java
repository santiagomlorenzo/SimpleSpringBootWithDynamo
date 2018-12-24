package com.javatrials.Entity;

public class Student {

    private int id;
    private String name;
    private String course;
    private String dateModified;

    public Student(int id, String name, String course, String dateModified) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.dateModified = dateModified;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getDateModified() {
        return dateModified;
    }
}
