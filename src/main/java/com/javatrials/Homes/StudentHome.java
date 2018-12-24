package com.javatrials.Homes;

import com.javatrials.Entity.Student;
import com.javatrials.Helpers.StudentAdapter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudentHome extends Home {

  public StudentHome() {
    super("Students");
  }

  public Collection<Student> getStudents () {
    Collection<Map<String, AttributeValue>> returned_items = this.GetAll();

    return returned_items.stream().map(it -> StudentAdapter.ToStudent(it))
      .collect(Collectors.toList());
  }

  public Student getStudent(int id) {
    Map<String, AttributeValue> result = this.GetOne(id);
    return StudentAdapter.ToStudent(result);
  }

  public void deleteStudent(Student student) {
    Delete(GenerateKey(student));
  }

  public void createStudent(Student student) {
    Create(StudentAdapter.ToDynamoMap(student));
  }

  public void updateStudent(Student newStudent) {
    Update(GenerateKey(newStudent),GenerateValues(newStudent));
  }

  private Map<String,AttributeValue> GenerateKey(Student student) {
    Integer id = student.getId();
    return new HashMap<String,AttributeValue>(){
      {
        put("id", AttributeValue.builder().n(id.toString()).build());
        put("last_modified", AttributeValue.builder().s(student.getDateModified()).build());
      }
    };
  }

  private Map<String, AttributeValueUpdate> GenerateValues(Student student){

    Map<String, AttributeValueUpdate> values = new HashMap<>();

    addNameToValues(values, student);
    addCourseToValues(values, student);

    return values;
  }

  private void addNameToValues(Map<String, AttributeValueUpdate> values, Student student) {
    addToValues("name", student.getName(), values);
  }

  private void addCourseToValues(Map<String, AttributeValueUpdate> values, Student student) {
    addToValues("course", student.getCourse(), values);
  }
}
