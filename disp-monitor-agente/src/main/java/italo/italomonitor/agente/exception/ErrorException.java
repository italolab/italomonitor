package italo.italomonitor.agente.exception;

public class ErrorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String mensagem;
	
	public ErrorException( String message, String... params ) {
		mensagem = message;
		for( int i = 0; i < params.length; i++ )
			mensagem = mensagem.replace( "$"+(i+1), params[ i ] );
	}
	
	public String getMessage() {
		return mensagem;
	}

}
