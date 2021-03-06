package com.ledzion.bicycleservice.repository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class LocalDateToDateConverter implements Converter<LocalDate, Date> {

    @Override
    public Date convert(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
