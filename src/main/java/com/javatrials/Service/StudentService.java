package com.javatrials.Service;

import com.javatrials.Homes.StudentHome;
import com.javatrials.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {

    @Autowired
    private StudentHome studentHome;

    public Collection<Student> getStudents() {
        return studentHome.getStudents();
    }

    public Student getStudent(int id) {
        return studentHome.getStudent(id);
    }

    public void createStudent(Student student) {
        studentHome.createStudent(student);
    }

    public void deleteStudent(Integer studentId) {
        Student student = getStudent(studentId);
        studentHome.deleteStudent(student);
    }

    public void updateStudent(Student student) {
        studentHome.updateStudent(student);
    }

}
