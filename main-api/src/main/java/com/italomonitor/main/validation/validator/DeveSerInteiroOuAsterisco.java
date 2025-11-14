package com.italomonitor.main.validation.validator;

import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.exception.ValidationException;
import com.italomonitor.main.validation.Validator;

public class DeveSerInteiroOuAsterisco implements Validator {

    private final String fieldName;
    private final String fieldValue;

    public DeveSerInteiroOuAsterisco( String fieldName, String fieldValue ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public void validate() {
        if ( fieldValue == null )
            throw new ValidationException( Errors.NOT_JOKER_AND_NOT_INT, fieldName );

        try {
            Integer.parseInt( fieldValue );
        } catch ( NumberFormatException e ) {
            if ( !fieldValue.equals( "*" ) )
                throw new ValidationException( Errors.NOT_JOKER_AND_NOT_INT, fieldName );
        }
    }

}
