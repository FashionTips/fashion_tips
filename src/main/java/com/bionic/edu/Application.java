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
        User u1 = new User("User1", "user1", "user1@example.com", "1111", "");
        User u2 = new User("User2", "user2", "user1@example.com", "1111", "");
        User u3 = new User("User3", "user3", "user1@example.com", "1111", "");
        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
        userService.getAll().forEach(System.out::println);
    }
}
