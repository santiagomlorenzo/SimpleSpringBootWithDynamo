package com.javatrials.Errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentBadRequestException extends RuntimeException {

    public StudentBadRequestException() {
        super(String.format("Bad Request for Students Endpoints"));
    }

}
