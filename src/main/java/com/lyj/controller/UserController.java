package com.lyj.controller;

import com.lyj.model.User;
import com.lyj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/1/29.
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/getUser")
    public User getUser(int id){
        return userService.getUser(id);
    }

    @RequestMapping("/updateUser")
    public User updateUser(User user){
        return userService.updateUser(user);
    }

    @RequestMapping("/deleteUser")
    public void deleteUser(int id){
        userService.deleteUser(id);
    }

    @RequestMapping("/getUserByName")
    public User getUserByName(String name){
        return userService.getUserByName(name);
    }

}
