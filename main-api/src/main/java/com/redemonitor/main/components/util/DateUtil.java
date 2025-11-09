package com.redemonitor.main.components.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtil {

    public Date localDateToDate( LocalDate localDate ) {
        return Date.from( localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant() );
    }

    public LocalDateTime dateToLocalDateTime( Date date ) {
        return date.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }

    public String dateTimeFormat( LocalDateTime date ) {
    	return DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm:ss" ).format( date );
    }
    
}
