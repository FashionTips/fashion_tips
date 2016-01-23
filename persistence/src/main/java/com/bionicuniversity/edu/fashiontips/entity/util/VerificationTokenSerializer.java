package com.bionicuniversity.edu.fashiontips.entity.util;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author Alexandr Laktionov
 */
public class VerificationTokenSerializer extends JsonSerializer<VerificationToken> {

    public static final String EMPTY = "";

    @Override
    public void serialize(VerificationToken verificationToken, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeObjectField("email", verificationToken.getEmail());
        gen.writeObjectField("token", EMPTY);
        gen.writeEndObject();
    }
}
