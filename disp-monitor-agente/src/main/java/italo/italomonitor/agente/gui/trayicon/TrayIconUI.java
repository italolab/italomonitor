package italo.italomonitor.agente.gui.trayicon;

public interface TrayIconUI {

	public void setTrayIconGUIListener( TrayIconGUIListener listener );
	
	public void displayErrorMessage( String message );
	
	public void displayInfoMessage( String message );
	
}
