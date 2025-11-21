package italo.italomonitor.main.dto.request;

import java.util.ArrayList;
import java.util.List;

import italo.italomonitor.main.enums.UsuarioPerfil;
import italo.italomonitor.main.validation.ValidationBuilder;
import italo.italomonitor.main.validation.Validator;
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
public class UpdateUsuarioRequest {

    private String nome;

    private String email;

    private String username;

    private UsuarioPerfil perfil;
    
    // Pode ser -1 para nenhuma empresa
    private Long empresaId;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "nome", nome )
                    .required()
                    .build()
        );

        validators.addAll(
                ValidationBuilder.of( "e-mail", email )
                    .required()
                    .email()
                    .build()
        );

        validators.addAll(
                ValidationBuilder.of( "username", username )
                    .required()
                    .build()
        );
        
        validators.addAll(
        		ValidationBuilder.of( "perfil", perfil.name() )
        			.required()
        			.build() 
        );

        validators.forEach( Validator::validate );
    }


}
