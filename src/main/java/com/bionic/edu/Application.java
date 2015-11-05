package com.bionic.edu;

import com.bionic.edu.crud.UserDao;
import com.bionic.edu.crud.UserService;
import com.bionic.edu.entity.Usr;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by maxim on 11/5/15.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = context.getBean(UserService.class);
        Usr usr = new Usr("Max", "solomkinmv", "solomkinmv@gmail.com", "1111", "");
//        System.out.println(usr);
//        userService.create(usr);
//        us.create(usr);
//        System.out.println(usr);
        System.out.println(userService.get(1));
//        System.out.println(userService.read(3));
    }
}
