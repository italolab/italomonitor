package italo.italomonitor.main.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Entity
@Table(name="empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String emailNotif;
    
    private String telegramChatId;

    private double porcentagemMaxFalhasPorLote;
    
    private int maxDispositivosQuant;
    
    private int minTempoParaProxNotif;
    
    private int diaPagto;
    
    private boolean temporario;
    
    private int usoTemporarioPor;
    
    private boolean bloqueada;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaNotifEm;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date criadoEm;
    
    @Temporal(TemporalType.DATE)
    private Date usoRegularIniciadoEm;
    
    @Temporal(TemporalType.DATE)
    private Date pagoAte;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dispositivo> dispositivos;
    
    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Agente> agentes;
    
}
