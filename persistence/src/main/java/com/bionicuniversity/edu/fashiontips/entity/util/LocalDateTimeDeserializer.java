package com.bionicuniversity.edu.fashiontips.entity.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for deserialization date from front-end.
 * It's used in the annotations as @JsonDeserialize(using = LocalDateTimeDeserializer.class)
 * on the LocalDateTime fields
 *
 * @author Sergiy
 * @since 17.12.2015
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    /**
     * Converting date from String to LocalDateTime using the ISO 8601 format
     */
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}