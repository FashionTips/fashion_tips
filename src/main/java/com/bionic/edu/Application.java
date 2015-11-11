package com.bionic.edu;

import com.bionic.edu.entity.Post;
import com.bionic.edu.service.PostService;
import com.bionic.edu.service.UserService;
import com.bionic.edu.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by maxim on 11/5/15.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = context.getBean(UserService.class);
        PostService postService = context.getBean(PostService.class);
        User u1 = new User("User1", "user1", "user1@example.com", "1111", "");
        User u2 = new User("User2", "user2", "user1@example.com", "1111", "");
        User u3 = new User("User3", "user3", "user1@example.com", "1111", "");
        userService.save(u1);
        userService.save(u2);
        userService.save(u3);
        Post p1 = new Post("descr1", 1, new Date(), u1);
        Post p2 = new Post("descr2", 2, new Date(), u1);
        Post p3 = new Post("descr3", 3, new Date(), u1);
        postService.save(p1);
        postService.save(p2);
        postService.save(p3);

        userService.getAll().forEach(System.out::println);
        postService.getUserPosts(u1.getId()).forEach(System.out::println);
    }
}
