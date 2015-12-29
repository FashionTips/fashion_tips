package com.bionicuniversity.edu.fashiontips.entity.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * JSON serializer to convert the Java 8 {@code LocalDate} instance.
 *
 * @author Maksym Dolia
 * @since 20.12.2015.
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DateTimeFormatter.ISO_LOCAL_DATE.format(date));
    }
}
