package italo.italomonitor.agente.gui.output;

import java.awt.Color;

public interface OutputUI {
	
	public void setVisible( boolean visible );
					
	public void printError( String text );
		
	public void printInfo( String text );

	public void print( String text, Color color );
	
}
