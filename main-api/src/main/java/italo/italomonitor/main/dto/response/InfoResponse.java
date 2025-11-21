package italo.italomonitor.main.dto.response;

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
public class InfoResponse {

	private long totalMemory;
	
	private long freeMemory;
	
	private long maxMemory;
	
	private double usoCpu;
	
	private int availableProcessors;
		
}
