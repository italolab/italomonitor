package com.italomonitor.main.components.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	private ZoneId zone = ZoneId.of( "America/Recife" );
	
    public Date localDateToDate( LocalDate localDate ) {
        return Date.from( localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant() );
    }

    public LocalDateTime dateToLocalDateTime( Date date ) {
        return date.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
    }

    public String dateTimeFormat( LocalDateTime date ) {
    	OffsetDateTime offset = date.atZone( ZoneId.systemDefault() ).toOffsetDateTime();
    	return offset.atZoneSameInstant( zone ).format( DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm:ss" ) );
    }
            
}
