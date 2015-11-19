package com.bionic.edu.service.util;

/**
 * Created by Sergiy on 16.11.2015
 * Project: fashion-tips
 */
public final class ClassNameUtil {
    private ClassNameUtil(){}
    public static String getCurrentClassName(){
            return new RuntimeException().getStackTrace()[1].getClassName();
    }
}
