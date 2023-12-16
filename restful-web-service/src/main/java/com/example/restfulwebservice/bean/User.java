package com.example.restfulwebservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})  // 한번에 여러 properties를 jsonignore에 포함
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
@Table(name ="users")
public class User {
    @Schema(title="사용자 ID", description = "사용자 ID는 자동으로 생성됩니다(Auto Increment)")
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요")
    private String name;

    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다")
    private Date joinDate;

    // 1. 각 어노테이션에 @JsonIgnore 어노테이션
    private String password;
    private String ssn;
}
