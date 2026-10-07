package com.example.lmsapp;

import com.example.lmsapp.dao.UserDAO;
import com.example.lmsapp.models.User;

public class CreateAdminUser {
    public static void main(String[] args) {
//        User user=new User();
//        user.setName("admin");;
//        user.setEmail("admin@gmail.com");
//        user.setPassword("admin123");
//        user.setRole("admin");
//
//        UserDAO.save(user);

        User user=UserDAO.findById(1);
        System.out.println(user.getName());
    }
}
