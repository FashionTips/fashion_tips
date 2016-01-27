package com.bionicuniversity.edu.fashiontips.entity.util;

import com.bionicuniversity.edu.fashiontips.entity.VerificationToken;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by slav9nin on 25.01.2016.
 */
public class VerificationTokenDeserializer extends JsonDeserializer<VerificationToken> {
    @Override
    public VerificationToken deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}
