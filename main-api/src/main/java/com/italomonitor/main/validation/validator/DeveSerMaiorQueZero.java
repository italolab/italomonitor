package com.italomonitor.main.validation.validator;

import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.exception.ValidationException;
import com.italomonitor.main.validation.Validator;

public class DeveSerMaiorQueZero implements Validator {

    private final String fieldName;
    private final String fieldValue;

    public DeveSerMaiorQueZero( String fieldName, String fieldValue ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public void validate() {
        if ( fieldValue == null )
            throw new ValidationException( Errors.VALUE_LESS_OR_EQUALS_THAN_ZERO, fieldName );

        try {
            double value = Double.parseDouble( fieldValue );
            if ( value <= 0 )
                throw new ValidationException( Errors.VALUE_LESS_OR_EQUALS_THAN_ZERO, fieldName );
        } catch ( NumberFormatException e ) {
            throw new ValidationException( Errors.VALUE_LESS_OR_EQUALS_THAN_ZERO, fieldName );
        }
    }

}
