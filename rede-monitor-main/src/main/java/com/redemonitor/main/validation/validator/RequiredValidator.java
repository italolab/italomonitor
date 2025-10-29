package com.redemonitor.main.validation.validator;

import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.exception.ValidationException;
import com.redemonitor.main.validation.Validator;

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
