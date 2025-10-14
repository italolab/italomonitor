package com.redemonitor.dto.validation.validator;

import com.redemonitor.dto.validation.Validator;
import com.redemonitor.exception.Errors;
import com.redemonitor.exception.ValidationException;

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
