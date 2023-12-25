package com.example.restfulwebservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    // 게시물:사용자는 N:1 관계
    // JsonIgnore 통해서 정보를 가져오지 않음
    @ManyToOne(fetch = FetchType.LAZY)  // FetchType.LAZY는 지연 로딩, 사용자 데이터를 필요한 시점에 가져옴. 즉시 로딩 안함
    @JsonIgnore
    private User user;
}
