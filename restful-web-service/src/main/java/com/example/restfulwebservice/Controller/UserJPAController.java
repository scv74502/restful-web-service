package com.example.restfulwebservice.Controller;

import com.example.restfulwebservice.bean.User;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.exception.UsersAndCountResponse;
import com.example.restfulwebservice.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.now;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJPAController {

    private UserRepository userRepository;

    public UserJPAController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // /jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/usersAndCount")
    public UsersAndCountResponse retrieveAllUsersAndCount(){
        List<User> users = userRepository.findAll();
        int count = users.size();
        System.out.println(new UsersAndCountResponse(count, users));

        return new UsersAndCountResponse(count, users);
    }

    // jpa/users/{id}
    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUsersById(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }

        EntityModel entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder lintTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(lintTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    // /jpa/users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        if(user.getJoinDate() == null)
            user.setJoinDate(new Date());
        User savedUser = userRepository.save(user);
        // USER CREATED
        // /users/4

        // 생성된 User의 URI를 저장 후 반환하기
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
