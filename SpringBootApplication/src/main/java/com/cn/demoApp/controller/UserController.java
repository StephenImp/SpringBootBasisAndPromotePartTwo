package com.cn.demoApp.controller;

import com.cn.demoApp.entity.User;
import com.cn.demoApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

   @Autowired
   private UserService userService;

    @RequestMapping(value = "/getUser/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable int id){
        return  userService.getUserById(id);
    }
    @RequestMapping(value = "/getUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }
    @RequestMapping(value = "/updateUser",method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }
    @RequestMapping(value = "/deleteUser/{id}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

}
