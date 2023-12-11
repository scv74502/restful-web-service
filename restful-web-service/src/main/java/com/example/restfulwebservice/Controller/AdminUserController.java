package com.example.restfulwebservice.Controller;

import com.example.restfulwebservice.bean.AdminUser;
import com.example.restfulwebservice.bean.User;
import com.example.restfulwebservice.dao.UserDaoService;
import com.example.restfulwebservice.bean.HelloWorldBean;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.bean.UserV2;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users =  service.findAll();

        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;

        for (User user: users){
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);
        return mapping;
    }

    // GET admin/users/{Integer id} -> /admin/v1/users/{Integer id}
//    @GetMapping("/v1/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=1")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser();
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            // 여러 속성 한번에 복사 가능 (원본, 복사할 곳)
            BeanUtils.copyProperties(user, adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);  // "UserInfo" 필터를 적용시킴

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);
        return mapping;
    }

    // GET admin/users/{Integer id} -> /admin/v2/users/{Integer id}
//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
//    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
//    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
//        User user = service.findOne(id);
//        if (user == null){
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//
//        // User -> UserV2
//        UserV2 userV2 = new UserV2();
//        BeanUtils.copyProperties(user, userV2); // id, name, joinDate, password, ssn
//        userV2.setGrade("VIP");
//
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//                .filterOutAllExcept("id", "name", "joinDate", "grade");
//        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
//
//        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
//        mapping.setFilters(filters);
//
//        return mapping;
//    }
}
