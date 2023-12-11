package com.example.restfulwebservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})  // 한번에 여러 properties를 jsonignore에 포함
public class User {
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다")
    private Date joinDate;

    // 1. 각 어노테이션에 @JsonIgnore 어노테이션
    private String password;
    private String ssn;
}
