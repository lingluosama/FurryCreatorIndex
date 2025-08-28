package org.rookie.data.converter;


import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class MapperUtils {

    @Named("longToLocalDateTime")
    public LocalDateTime longToLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Named("integerToBoolean")
    public Boolean integerToBoolean(Integer value) {
        if (value == null) {
            return null;
        }
        return value == 1;
    }
}
