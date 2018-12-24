package com.javatrials.Helpers;

import com.javatrials.Entity.Student;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class StudentAdapter {

  public static Student ToStudent(Map<String, AttributeValue> values){
    return new Student(
      Integer.parseInt(values.get("id").n()),
      values.get("name").s(),
      values.get("course").s(),
      values.get("last_modified").s()
    );
  }

  public static Map<String, AttributeValue> ToDynamoMap(Student student){
    return new HashMap<String, AttributeValue>() {
      {
        put("id", AttributeValue.builder().n(((Integer) student.getId()).toString()).build());
        put("name", AttributeValue.builder().s(student.getName()).build());
        put("course", AttributeValue.builder().s(student.getCourse()).build());
        put("last_modified", AttributeValue.builder().s(student.getDateModified()).build());
      }
    };
  }

}
