package com.skypro.hw_maven.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid data entered")
public class InvalidEmployeeRequestExceptions extends RuntimeException {

}
