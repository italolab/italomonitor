package com.redemonitor.main.components;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateUtil {

    public Date localDateToDate( LocalDate localDate ) {
        return Date.from( localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant() );
    }

    public LocalDateTime dateToLocalDateTime( Date date ) {
        return date.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }

}
