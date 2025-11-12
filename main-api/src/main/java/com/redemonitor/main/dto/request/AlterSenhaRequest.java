package com.redemonitor.main.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.redemonitor.main.validation.ValidationBuilder;
import com.redemonitor.main.validation.Validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AlterSenhaRequest {

	private String novaSenha;
	
	public void validate() {
        List<Validator> validators = new ArrayList<>();
        
        validators.addAll(
                ValidationBuilder.of( "nova senha", novaSenha )
                        .required()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

	
}
