package com.example.restfulwebservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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

    @Schema(title="사용자 이름", description = "사용자 이름을 입력합니다. 최소 두글자 이상 입력해야 합니다")
    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요")
    private String name;

    @Schema(title="사용자 등록일", description = "사용자 등록일을 입력합니다. 입력하지 않으면 현재 날짜가 지정됩니다")
    @ColumnDefault("now()")
    @Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다")
    private Date joinDate;

    // 1. 각 어노테이션에 @JsonIgnore 어노테이션
    @Schema(title="사용자 비밀번호", description = "사용자 비밀번호를 입력합니다.")
    private String password;

    @Schema(title="사용자 주민번호", description = "사용자 주민번호를 입력합니다.")
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(Integer id, @Size(min = 2, message = "name은 2글자 이상 입력해야 합니다.") String name, @Past Date joinDate,
                String password, String ssn){
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
