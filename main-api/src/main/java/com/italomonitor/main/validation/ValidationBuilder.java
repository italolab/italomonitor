package com.italomonitor.main.validation;

import java.util.ArrayList;
import java.util.List;

import com.italomonitor.main.validation.validator.DeveSerInteiroOuAsterisco;
import com.italomonitor.main.validation.validator.DeveSerMaiorOuIgual;
import com.italomonitor.main.validation.validator.DeveSerMaiorQueZero;
import com.italomonitor.main.validation.validator.EMailValidator;
import com.italomonitor.main.validation.validator.RequiredValidator;

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
    
    public ValidationBuilder deveSerMaiorOuIgual( int value ) {
    	validators.add( new DeveSerMaiorOuIgual( fieldName, fieldValue, value ) );
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
