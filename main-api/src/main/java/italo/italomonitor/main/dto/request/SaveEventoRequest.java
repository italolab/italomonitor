package italo.italomonitor.main.dto.request;

import java.util.Date;

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
public class SaveEventoRequest {

    private int sucessosQuant;

    private int falhasQuant;

    private int quedasQuant;

    private int tempoInatividade;

    private int duracao;

    private Date criadoEm;

    private Long dispositivoId;
    
}
