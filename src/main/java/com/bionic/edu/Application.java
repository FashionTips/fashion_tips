package com.bionic.edu;

import com.bionic.edu.crud.UserService;
import com.bionic.edu.entity.Usr;

/**
 * Created by maxim on 11/5/15.
 */
public class Application {
    public static void main(String[] args) {
        UserService us = new UserService();
//        Usr usr = new Usr("Max", "solomkinmv", "solomkinmv@gmail.com", "1111", "");
//        System.out.println(usr);
//        us.add(usr);
//        System.out.println(usr);
        System.out.println(us.get(3));
    }
}
