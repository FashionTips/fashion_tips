package com.bionicuniversity.edu.fashiontips.entity.util;

import com.bionicuniversity.edu.fashiontips.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 *  JSON serializer to properly convert User in entities, where the user is an author of them, like
 * {@code Post}, {@code Comment}, etc.
 *
 * @author Maksym Dolia
 * @since 19.12.2015.
 */
public class AuthorSerializer extends JsonSerializer<User> {

    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(user.getLogin());
    }

}
