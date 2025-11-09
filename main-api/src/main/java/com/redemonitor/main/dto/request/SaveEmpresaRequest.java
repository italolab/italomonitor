package com.redemonitor.main.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.redemonitor.main.validation.ValidationBuilder;
import com.redemonitor.main.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaveEmpresaRequest {

    private String nome;
    private String emailNotif;
    private String telegramChatId;
    private double porcentagemMaxFalhasPorLote;
    private int maxDispositivosQuant;
    private int minTempoParaProximoEvento;
    private int diaPagto;
    private boolean temporario;
    private int usoTemporarioPor;

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
                ValidationBuilder.of( "id do chat telegram", telegramChatId )
                    .build()
        );

        validators.addAll(
                ValidationBuilder.of( "porcentagem máxima de falhas por lote", String.valueOf( porcentagemMaxFalhasPorLote ) )
                    .required()
                    .deveSerMaiorQueZero()
                    .build()
        );
        
        validators.addAll(
        		ValidationBuilder.of( "Quantidade máxima de dispositivos", String.valueOf( maxDispositivosQuant ) )
        			.required()
        			.deveSerMaiorQueZero() 
        			.build()
        ); 
        
        validators.addAll(
        		ValidationBuilder.of( "Tempo min. para próximo evento", String.valueOf( minTempoParaProximoEvento ) )        			
        			.build()
        ); 
        
        validators.addAll(
        		ValidationBuilder.of( "dia de pagamento", String.valueOf( diaPagto ) )
        			.required()
        			.deveSerMaiorQueZero()
        			.build()
        );
        
        validators.addAll(
        		ValidationBuilder.of( "uso temporário em dias", String.valueOf( usoTemporarioPor ) )
        			.build()
        );

        validators.forEach( Validator::validate );
    }

}
