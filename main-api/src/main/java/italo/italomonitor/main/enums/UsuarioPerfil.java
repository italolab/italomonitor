package italo.italomonitor.main.enums;

public enum UsuarioPerfil implements EnumOption {
	ADMIN, USUARIO;
	
	public String label() {
		switch( this ) {
			case ADMIN: return "Administrador";
			case USUARIO: return "Usuário";
			default:
				return "Desconhecido.";
		}
	}
}
