package italo.italomonitor.agente.gui.output;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import italo.italomonitor.agente.gui.GUIDriver;
import italo.italomonitor.agente.gui.GUIException;

public class OutputGUI extends JFrame implements OutputUI, WindowListener {

	private static final long serialVersionUID = 1L;
	
	private JEditorPane editorPane;
	
	private OutputGUIListener listener;
	
	public OutputGUI( GUIDriver drv ) throws GUIException {
		editorPane = new JEditorPane();
		editorPane.setEditable( false ); 
		editorPane.setPreferredSize( new Dimension( 640, 480 ) );
		editorPane.setContentType( "text/html" ); 
		editorPane.setBackground( Color.BLACK );
		
		JPanel panel = new JPanel();
		panel.setLayout( new GridLayout() );
		panel.add( new JScrollPane( editorPane ) );
		
		super.getContentPane().setLayout( new GridLayout() );
		super.getContentPane().add( panel );
				
		super.addWindowListener( this );

		super.setIconImage( drv.getImageLoader().getIcon() );
		
		super.setTitle( "Saída de Italo Monitor" );
		super.setDefaultCloseOperation( drv.isSystemTraySupported() ? JDialog.HIDE_ON_CLOSE : JDialog.EXIT_ON_CLOSE ); 
		super.pack();
		super.setLocationRelativeTo( this );
		super.setResizable( false );
		super.setVisible( false );		
	}
	
	public void printInfo( String text ) {
		this.print( text, Color.WHITE ); 
	}
				
	public void printError( String text ) {
		this.print( text, Color.YELLOW ); 
	}
		
	public void print( String text, Color color ) {
		SwingUtilities.invokeLater( () -> { 
			try {
				StyledDocument doc = (StyledDocument) editorPane.getDocument();
				
				SimpleAttributeSet attrs = new SimpleAttributeSet();
				StyleConstants.setBold( attrs, false );
				StyleConstants.setForeground( attrs, color );
				StyleConstants.setFontSize( attrs, 14 );
				
				doc.insertString( doc.getLength(), text+"\n", attrs );
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} );	
	}
	
	public void setOutputGUIListener( OutputGUIListener listener ) {
		this.listener = listener;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if ( listener != null )
			listener.saindoOnClick();
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
	
}
