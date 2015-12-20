package com.bionicuniversity.edu.fashiontips.entity.util;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * JSON deserializer to properly convert User in entities, where the user is an author of them, like
 * {@code Post}, {@code Comment}, etc.
 *
 * @author Maksym Dolia
 * @since 20.12.2015.
 */
public class AuthorDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        User user = new User();
        user.setLogin(parser.getValueAsString());
        return user;
    }
}
