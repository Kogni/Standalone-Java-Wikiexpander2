
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import Control.Controller;
public class Brain_Forside {

    private GUI_Forside Class_GUI_Forside;

    public int Fisker, Fisker_Voksne, Fisker_Yngel, Penger = 900;

    double Temp;

    int DoegnTeller;
    int YngleTeller;
    int MoveTeller;

    private boolean RunTimer = true;

    public Brain_Forside(Controller controller) {
	System.out.println(this.getClass().toString() + " started");
	SettOppGUI();
        int updateTeller = 0;
	Timeren();
    }

    void SettOppGUI() {
	System.out.println(this.getClass().toString() + " SettOppGUI");
	Class_GUI_Forside = new GUI_Forside(this);
	Class_GUI_Forside.SettOpp();
	Class_GUI_Forside.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Pause() {
        RunTimer = !RunTimer;
    }

    void Timeren() {
	int delay = 10; //milliseconds
	ActionListener taskPerformer = new ActionListener() {

	    public void actionPerformed(ActionEvent evt) {
		if (RunTimer) {
		}
	    }
	};
	new Timer(delay, taskPerformer).start();
    }

    public void AddProgressMessage(String actionMessage) {
	Class_GUI_Forside.AddProgressMessage(actionMessage);
    }
}
