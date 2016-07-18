package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

import Control.Controller;

public class GUI_Forside extends JFrame implements ActionListener, MouseMotionListener{
	Controller	Class_Controller;

	JPanel		MainPanel;
	JPanel		ActionPanel;
	JPanel		MessagePanel;
	JTextArea	MessageText;

	JLabel		Currency;

	public GUI_Forside( Brain_Forside Brain_Forside ) {
		super( "WikiExpander" );
		System.out.println( this.getClass().toString() + " started" );
		setSize( 600, 500 );
		setBackground( new Color( (0), (255), (0) ) );

		MainPanel = new JPanel();
		MainPanel.setSize( this.getSize() );
		MainPanel.setBackground( new Color( (0), (255), (0) ) );
		MainPanel.setLayout( new BoxLayout( this.MainPanel, BoxLayout.LINE_AXIS ) );

		MessagePanel = new JPanel();
		MessagePanel.setLayout( new BoxLayout( this.MessagePanel, BoxLayout.PAGE_AXIS ) );
		MessagePanel.setBackground( new Color( (255), (0), (0) ) );
		MessagePanel.setMinimumSize( new Dimension( this.getSize() ) );
		MainPanel.add( this.MessagePanel );

		MessageText = new JTextArea( 10, 1 );
		MessageText.setEditable( false );
		JScrollPane scrollPane = new JScrollPane( MessageText );
		MessagePanel.add( scrollPane );

		MainPanel.setVisible( true );
		this.add( MainPanel );
		this.setVisible( true );

		try {
			AddProgressMessage( "GUI loaded" );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public void SettOpp() {
		MainPanel.setVisible( true );
		this.setVisible( true );
		//LagGUI();
	}

	public void actionPerformed( ActionEvent e ) {

	}

	public void mouseMoved( MouseEvent e ) {
	}

	public void mouseDragged( MouseEvent e ) {
	}

	public void AddProgressMessage( String Message ) {
		//System.out.println( this.getClass().toString() + " AddProgressMessage " + Message );

		try {
			Date Idag = new Date();
			Message = Message.substring( 0, Math.min( 200, Message.length() ) );
			MessageText.append( Idag.getHours() + "." + Idag.getMinutes() + ":" + Idag.getSeconds() + " " + Message + "\n" );
			if ( MessageText.getLineCount() > 29 ) {
				Element root = MessageText.getDocument().getDefaultRootElement();
				Element first = root.getElement( 0 );
				try {
					MessageText.getDocument().remove( first.getStartOffset(), first.getEndOffset() );
				}
				catch ( BadLocationException e ) {
				}
			}
			MessageText.selectAll();

			MessageText.setCaretPosition( MessageText.getDocument().getLength() );
		}
		catch ( Exception e ) {
		}
	}

}
