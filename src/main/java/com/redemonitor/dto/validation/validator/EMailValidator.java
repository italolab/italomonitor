package com.redemonitor.dto.validation.validator;

import com.redemonitor.dto.validation.Validator;
import com.redemonitor.exception.Errors;
import com.redemonitor.exception.ValidationException;

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

        if ( !fieldValue.matches( "\\w+\\.{0,1}\\w+\\@{1}\\w+\\.{1}\\w+" ) )
            throw new ValidationException( Errors.INVALID_EMAIL, fieldName );
    }

}
