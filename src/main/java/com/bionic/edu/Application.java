package com.bionic.edu;

import com.bionic.edu.service.UserService;
import com.bionic.edu.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by maxim on 11/5/15.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = context.getBean(UserService.class);
//        User user = new User("Max", "solomkinmv", "solomkinmv@gmail.com", "1111", "");
//        for (User user1: userService.getAll()) {
//            System.out.println(user1);
//        }
    }
}
