package italo.italomonitor.main.dto.request;

import java.util.ArrayList;
import java.util.List;

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
public class NoAdminSaveEmpresaRequest {

    private String nome;
    private String emailNotif;
    private String telegramChatId;
    private double porcentagemMaxFalhasPorLote;
    private int minTempoParaProxNotif;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "nome", nome )
                    .required()
                    .build()
        );

        validators.addAll(
                ValidationBuilder.of( "email de notificação", emailNotif )
                    .email()
                    .build()
        );
        
        validators.addAll(
                ValidationBuilder.of( "id de chat do telegram", telegramChatId )                   
                    .build()
        );

        validators.addAll(
                ValidationBuilder.of( "porcentagem máxima de falhas por lote", String.valueOf( porcentagemMaxFalhasPorLote ) )
                    .required()
                    .deveSerMaiorQueZero()
                    .build()
        );
        
        validators.addAll(
        		ValidationBuilder.of( "tempo min. para prox. notif", String.valueOf( minTempoParaProxNotif ) )
        			.deveSerMaiorOuIgual( 60 )
        			.build()
        );

        validators.forEach( Validator::validate );
    }

}

