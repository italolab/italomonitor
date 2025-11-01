package com.redemonitor.main.validation;

import java.util.ArrayList;
import java.util.List;

import com.redemonitor.main.validation.validator.DeveSerInteiroOuAsterisco;
import com.redemonitor.main.validation.validator.DeveSerMaiorQueZero;
import com.redemonitor.main.validation.validator.EMailValidator;
import com.redemonitor.main.validation.validator.RequiredValidator;

public class ValidationBuilder {

    private final List<Validator> validators = new ArrayList<>();
    private final String fieldName;
    private final String fieldValue;

    public ValidationBuilder( String fieldName, String fieldValue ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public static ValidationBuilder of( String fieldName, String fieldValue ) {
        return new ValidationBuilder( fieldName, fieldValue );
    }

    public ValidationBuilder required() {
        validators.add( new RequiredValidator( fieldName, fieldValue ) );
        return this;
    }

    public ValidationBuilder email() {
        validators.add( new EMailValidator( fieldName, fieldValue ) );
        return this;
    }

    public ValidationBuilder deveSerMaiorQueZero() {
        validators.add( new DeveSerMaiorQueZero( fieldName, fieldValue ) );
        return this;
    }

    public ValidationBuilder deveSerInteiroOuAsterisco() {
        validators.add( new DeveSerInteiroOuAsterisco( fieldName, fieldValue ) );
        return this;
    }

    public List<Validator> build() {
        return validators;
    }

}
