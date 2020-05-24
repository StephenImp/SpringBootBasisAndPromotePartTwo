package com.cn.demoApp.service;

import com.cn.demoApp.dao.UserMapper;
import com.cn.demoApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(int id){
        return userMapper.getUserById(id);
    };
    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }
    public void saveUser(User user){
        userMapper.saveUser(user);
    }
    public void updateUser(User user){
        userMapper.updateUser(user);
    }
    public void deleteUser(int id){
        userMapper.deleteUser(id);
    }

}
