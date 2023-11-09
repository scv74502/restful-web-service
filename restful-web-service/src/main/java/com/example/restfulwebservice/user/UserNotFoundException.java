package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 2XX -> OK
// 4xx -> Client requested unvalid request
// 5XX -> Server side error
@ResponseStatus(HttpStatus.NOT_FOUND)   // 400번대 에러로 전달하는 어노테이션
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
