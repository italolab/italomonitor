package italo.italomonitor.main.validation.validator;

import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.exception.ValidationException;
import italo.italomonitor.main.validation.Validator;

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
