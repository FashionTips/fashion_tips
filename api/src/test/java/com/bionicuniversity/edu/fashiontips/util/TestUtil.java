package com.bionicuniversity.edu.fashiontips.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Class holds convenient common methods for test classes.
 *
 * @author Maksym Dolia
 * @since 16.12.2015.
 */
public class TestUtil {

    private TestUtil() {
        // don't let anyone instantiate this class
    }


    /**
     * Convert object to JSON.
     *
     * @param obj object to be converted
     * @return JSON as string
     * @throws IOException
     */
    public static String json(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
