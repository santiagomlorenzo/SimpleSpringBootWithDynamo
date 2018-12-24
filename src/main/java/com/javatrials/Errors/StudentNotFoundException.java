package com.javatrials.Errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(int id) {
        super(String.format("Student %d not found", id));
    }

    public StudentNotFoundException() {
        super(String.format("Student not found"));
    }

}

