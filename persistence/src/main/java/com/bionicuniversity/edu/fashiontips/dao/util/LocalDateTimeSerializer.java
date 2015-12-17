package com.bionicuniversity.edu.fashiontips.dao.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for serialization date for front-end.
 * It's used in the annotations @JsonSerialize(using = LocalDateTimeSerializer.class)
 * on the LocalDateTime fields
 *
 * @author Sergiy
 * @since 17.12.2015
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    /**
     * Converting date from LocalDateTime to String using the ISO 8601 format
     */
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        String dateInISO8601 = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime);
        jgen.writeString(dateInISO8601);
    }
}