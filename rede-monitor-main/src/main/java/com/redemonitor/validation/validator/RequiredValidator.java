package com.redemonitor.validation.validator;

import com.redemonitor.validation.Validator;
import com.redemonitor.exception.Errors;
import com.redemonitor.exception.ValidationException;

public class RequiredValidator implements Validator {

    private final String fieldName;
    private final String fieldValue;

    public RequiredValidator( String fieldName, String fieldValue ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public void validate() {
        if ( fieldValue == null )
            throw new ValidationException( Errors.REQUIRED_FIELD, fieldName );
        if ( fieldValue.isBlank() )
            throw new ValidationException( Errors.REQUIRED_FIELD, fieldName );
    }
}
