package com.example.restfulwebservice.dao;

import com.example.restfulwebservice.bean.HelloWorldBean;
import com.example.restfulwebservice.bean.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "one", new Date(), "pass1", "000101-1111111"));
        users.add(new User(2, "two", new Date(), "pass2", "000101-1111111"));
        users.add(new User(3, "three", new Date(), "pass3", "000101-1111111"));
    }

    private static int usersCount = 3;

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if (user.getId() == null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        for (User user : users){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){
        // iterator 개념 숙지해 두기
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()){
            User user = iterator.next();

            if (user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    public User modifyById(int id, String name) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId() == id) {
                user.setName(name);
                return user;
            }
        }
        return null;
    }
}
