package GUI;

//import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

//import javax.swing.JLabel;

public class Brain_Forside{

	public GUI_Forside	Class_GUI_Forside;
	//public Panel_Startup	Class_GUI_Forside;
	public int			Fisker, Fisker_Voksne, Fisker_Yngel, Penger = 900;
	double				Temp;
	int					UpdateTeller, DoegnTeller, YngleTeller, MoveTeller;
	//int TimerInterval = 100;
	public boolean		RunTimer	= true;

	public Brain_Forside() {
		System.out.println( this.getClass().toString() + " started" );
		SettOppGUI();
		UpdateTeller = 0;
		Timeren();
	}

	public void SettOppGUI() {
		System.out.println( this.getClass().toString() + " SettOppGUI" );
		Class_GUI_Forside = new GUI_Forside( this );
		//Class_GUI_Forside = new Panel_Startup( null );
		Class_GUI_Forside.SettOpp();
		//Class_GUI_Forside.MainPanel.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Class_GUI_Forside.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

	}

	public void Pause() {
		if ( RunTimer == true ) {
			RunTimer = false;
		}
		else {
			RunTimer = true;
		}
	}

	public void Timeren() {
		int delay = 10; //milliseconds

		ActionListener taskPerformer = new ActionListener(){
			public void actionPerformed( ActionEvent evt ) {

				if ( RunTimer == true ) {
				}
			}
		};

		new Timer( delay, taskPerformer ).start();
	}

	public void AddProgressMessage( String actionMessage ) throws Exception {
		Class_GUI_Forside.AddProgressMessage( actionMessage );
	}

}
