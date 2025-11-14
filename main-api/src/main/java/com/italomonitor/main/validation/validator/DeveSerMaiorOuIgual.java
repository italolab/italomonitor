package com.italomonitor.main.validation.validator;

import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.exception.ValidationException;
import com.italomonitor.main.validation.Validator;

public class DeveSerMaiorOuIgual implements Validator {

    private final String fieldName;
    private final String fieldValue;
    private final int value;

    public DeveSerMaiorOuIgual( String fieldName, String fieldValue, int value ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.value = value;
    }

    @Override
    public void validate() {
        if ( fieldValue == null )
            throw new ValidationException( Errors.VALUE_LESS_THAN, fieldName, ""+value );

        try {
            double v = Double.parseDouble( fieldValue );
            if ( v < value )
                throw new ValidationException( Errors.VALUE_LESS_THAN, fieldName, ""+value );
        } catch ( NumberFormatException e ) {
            throw new ValidationException( Errors.VALUE_LESS_THAN, fieldName, ""+value );
        }
    }

}
