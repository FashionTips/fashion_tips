package com.bionicuniversity.edu.fashiontips.entity.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * JSON deserializer to convert the Java 8 {@code LocalDate} instance.
 *
 * @author Maksym Dolia
 * @since 20.12.2015.
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        return LocalDate.parse(parser.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
