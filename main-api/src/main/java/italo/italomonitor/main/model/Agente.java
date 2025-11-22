package italo.italomonitor.main.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="agente")
public class Agente {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 private String chave;
	 
	 private String nome;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date ultimoEnvioDeEstadoEm;
	 
	 @ManyToOne(fetch=FetchType.EAGER)
	 @JoinColumn(name="empresa_id")
	 private Empresa empresa;
	 
	 @OneToMany(mappedBy = "agente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 private List<Dispositivo> dispositivos;
	
}
