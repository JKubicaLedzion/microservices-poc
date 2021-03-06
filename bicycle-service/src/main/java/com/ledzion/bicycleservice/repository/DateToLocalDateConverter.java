package com.ledzion.bicycleservice.repository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateToLocalDateConverter implements Converter<Date, LocalDate> {

    @Override
    public LocalDate convert(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
