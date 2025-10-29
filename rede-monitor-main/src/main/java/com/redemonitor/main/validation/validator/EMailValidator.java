package com.redemonitor.main.validation.validator;

import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.exception.ValidationException;
import com.redemonitor.main.validation.Validator;

public class EMailValidator implements Validator {

    private final String fieldName;
    private final String fieldValue;

    public EMailValidator( String fieldName, String fieldValue ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public void validate() {
        if ( fieldValue == null )
            return;
        if ( fieldValue.isBlank() )
            return;

        if ( !fieldValue.matches( "\\w+\\.{0,1}\\w+\\@{1}\\w+\\.{1}\\w+(\\.{1}\\w+){0,1}" ) )
            throw new ValidationException( Errors.INVALID_EMAIL, fieldName );
    }

}
