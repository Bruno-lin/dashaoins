package com.dsmh.common.mapstruct;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author TeamScorpio
 * @since 2022/3/12
 */
@Mapper(componentModel = "spring")
public interface InstantMapper {


    default LocalDateTime toLocalDateTime(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    default Instant fromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

}
