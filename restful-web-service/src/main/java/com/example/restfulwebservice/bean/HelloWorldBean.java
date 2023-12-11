package com.example.restfulwebservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
public class HelloWorldBean {
    private String message;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    //@JsonIgnoreProperties(value = {"password", "ssn"})
    //@JsonFilter("UserInfo")
    public static class User {
        private Integer id;

        @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
        private String name;

        // 현재 시간보다 과거 시간만 사용할 수 있도록 제한함
        @Past
        private Date joinDate;

        private String password;
        private String ssn;
    }
}
