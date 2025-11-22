package italo.italomonitor.agente.exception;

public interface Errors {

	public final static String CONFIG_FILE_NOT_FOUND = "Arquivo de configurações não encontrado em: '$1'";
	public final static String APPLICATION_FILE_NOT_FOUND = "Arquivo '$1' não encontrado.";
	public final static String CONFIG_IO_ERROR = "Erro de leitura do arquivo de configurações.";
	public final static String FILE_IO_ERROR = "Erro de leitura do arquivo '$1'.";
	
	public final static String REQUIRED_PROPERTY = "Defina a propriedade '$1' no arquivo de configurações.";
	public final static String PROPERTY_NOT_FOUND = "Propriedade '$1' não encontrada no '$2'.";
	
	public final static String PROPERTY_INVALID_FORMAT = "A propriedade '$1' está em formato inválido.";
	
	public final static String MAINAPI_INDISPONIVEL = "Servidor MainAPI indisponível.";
	
}
