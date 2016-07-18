
package Control;

import java.net.MalformedURLException;
import java.net.URL;
import GUI.Brain_Forside;
import Objects.Object_Ord;
import Objects.Object_Ordbok;
import Objects.Object_Webpage;
import Threads.Thread_LinkScanner;
import Threads.Thread_PhraseSearcher;
public class Controller {

    //Brain_Links Class_Brain_Links;
    public final Brain_PageManager Class_Brain_PageManager;
    private final Brain_IO Class_Brain_IO;
    private final Brain_Ordbok Class_Brain_Ordbok;
    //Thread_LinkScanner Threads[];
    private int ThreadsRunning = 0;
    private int ThreadsStarted = 0;
    private int ThreadsCompleted = 0;
    public final Object_Ordbok OrdBok;
    boolean Doneloading = false;
    public boolean DoneLoadingPhrases = false;
    boolean GoogledPhrases = false;
    public final int InterestBorder = 30;
    public final String startSite = "https://hjernebark.atlassian.net/wiki/display/BIOL/_Labels+all+spaces";
    private Thread_LinkScanner ScannerThread;
    private final Brain_Forside class_Brain_Forside;

    public Controller() {
	//System.out.println( this.getClass().toString() + " started" );
	long TidA = System.currentTimeMillis();
	class_Brain_Forside = new Brain_Forside(this);
	Class_Brain_IO = new Brain_IO(this);
	PrintAction(this.getClass().toString() + " Creating program");
	//PrintAction( this.getClass().toString() + " Loading saved phrases" );
	OrdBok = new Object_Ordbok(this);
	Class_Brain_PageManager = new Brain_PageManager(this);
	Class_Brain_Ordbok = new Brain_Ordbok(this);
	int tickInterval = 40;
	TimeKeeper class_TimeKeeper = new TimeKeeper(this, tickInterval);
	//System.out.println( this.getClass().toString() + " created all brains. Now preparing for searching." );
	long TidB = System.currentTimeMillis();
	PrintAction(this.getClass().toString() + " Startup took " + Double.toString((double) ((TidB - TidA) / 1000)) + "s.");
	if (OrdBok.FoundSavedLabels == 0) {
	    SaveURL(startSite, "Controller", 10000);
	} else {
	    PrintAction(this.getClass().toString() + " Skipper æ laste ned nye labels, siden det allerede er lagret "
		    + OrdBok.FoundSavedLabels + " labels fra tidligere.");
	}
	SaveWordList(OrdBok.Ord);
	//PrintAction( this.getClass().toString() + " Done loading saved phrases" );
	//PrintAction( this.getClass().toString() + " Googling phrases" );
	TidA = System.currentTimeMillis();
	PrintAction(this.getClass().toString() + " Next step is loading URLS. Estimated time is " + (OrdBok.FoundSavedLabels * 0.000717)
		+ " minutes");
	Class_Brain_IO.Load_URLs();
	TidB = System.currentTimeMillis();
	PrintAction(this.getClass().toString() + " Loading URLs took " + Double.toString((double) (((TidB - TidA) / 100) / 60))
		+ "/10 mins");
	TidA = System.currentTimeMillis();
	if (Class_Brain_IO.loadedURLs == 0) {
	    PrintAction(this.getClass().toString() + " Next step is googling labels. Estimated time is "
		    + (OrdBok.FoundSavedLabels * 0.00217) + " minutes.");
	    GoogleOrdliste(); //lagrer URL's for alle ord i ordlista, saa det er klart til aa soekes opp
	} else {
	    PrintAction(this.getClass().toString() + " Skipper å google labels, siden det allerede er lagret " + Class_Brain_IO.loadedURLs
		    + " urls fra tidligere.");
	}
	TidB = System.currentTimeMillis();
	PrintAction(this.getClass().toString() + " Googling labels took " + Double.toString((double) (((TidB - TidA) / 100) / 60))
		+ "/10 mins.");
	//PrintAction( this.getClass().toString() + " Done loading saved URLs" );
	Doneloading = true; //OrderNewSearch, SearchURL og SearchPhrase funker ikke faar denne er true
	PrintAction(this.getClass().toString() + " Now starting regular searches");
	TimeTick("IO");
    }

    public void TimeTick(String Sender) {
	//System.out.println( this.getClass().toString() + " TimeTick started" );
	if (Sender.equals("LinkThread completed")) {
	    ThreadsRunning--;
	    ThreadsCompleted++;
	    //System.out.println( "ThreadsCompleted="+ThreadsCompleted );
	}
	Class_Brain_PageManager.OrderNewSearch();
    }

    public void PrintAction(String actionMessage) {
	try {
	    class_Brain_Forside.AddProgressMessage(actionMessage);
	    Class_Brain_IO.PrintActionLog(actionMessage);
	} catch (Exception e) {
	    this.CastErrors(e);
	}
    }

    public void SearchPhrase(Object_Ord temp) {
	//System.out.println( this.getClass().toString() + " SearchPhrase started. " );
	//System.out.println( "Doneloading=" + Doneloading + " temp=" + temp );
	if (!Doneloading) {
	    return;
	}
	if (temp == null) {
	    return;
	} else if (temp.Ordet.equals("")) {
	    return;
	}
	temp.Set_Searched();
	//PrintAction( this.getClass().toString() + " Searching for phrase " + temp.Ordet );
	Thread thread = new Thread_PhraseSearcher(this, temp);
	ThreadsRunning++;
	ThreadsStarted++;
    }

    public void SearchURL(Object_Webpage temp) {
	PrintAction(this.getClass().toString() + " SearchURL started. " + temp.Get_URL());
	if (!Doneloading) {
	    return;
	}
	if (!this.Class_Brain_PageManager.isUrlValid(temp.Get_URL().toString())) {
	    return;
	}
	temp.Set_Searched();
	if ((ScannerThread != null) && ScannerThread.isAlive()) {
	    System.out.println(this.getClass().toString() + " " + ScannerThread.Status);
	}
	//PrintAction( this.getClass().toString() + " Scanning next URL: " + temp.Get_URL().toString() );
	ScannerThread = new Thread_LinkScanner(this, temp);
	ScannerThread.run();
	//System.out.println( this.getClass().toString() + " SearchURL created search thread for link" );
	ThreadsRunning++;
	ThreadsStarted++;
	this.Class_Brain_PageManager.Soeker = false;
    }

    public void SaveURL(String line, String Sender, int RelatedWordsFound) {
	if (!Class_Brain_PageManager.isUrlValid(line)) {
	    return;
	}
	URL Adresse;
	try {
	    Adresse = new URL(line);
	    Object_Webpage New = new Object_Webpage(Adresse, RelatedWordsFound, 0);
	    if (!Class_Brain_PageManager.isAdded(New)) {
		//PrintAction( "Adding new URL " + line );
		Class_Brain_PageManager.InsertLink(line, RelatedWordsFound);
	    }
	} catch (MalformedURLException e) {
	}
    }

    public Object_Ord[] GetOrdliste() {
	return this.Class_Brain_Ordbok.Get_Ordliste();
    }

    void SaveWordList(Object_Ord[] ord) {
	Class_Brain_Ordbok.SaveWordList(ord);
    }

    public void SaveWord(String phrase) {
	//PrintAction(this.getClass().toString() + " SaveWord " + phrase + " Value=" + 30);
	OrdBok.SaveWord(phrase, 30);
    }

    public void GoogleOrdliste() {
	Class_Brain_Ordbok.GoogleOrdliste();
    }

    public void SaveSentence(String sentence, String Phrase, int value) {
	this.Class_Brain_IO.SavePhrase(Phrase, sentence, value);
    }

    public void SaveDatabase(Object_Webpage UpdatedPage) {
	if (Class_Brain_IO != null) {
	    if (Doneloading) {
		System.out.println("ber om ï¿½ lagre database ");
		//PrintAction( this.getClass().toString() + " Saving URLs to file" );
		Class_Brain_IO.SaveAll(Class_Brain_PageManager.GetLinksToSave());
	    }
	}
    }

    public void CastErrors(Exception T) {
	PrintAction(this.getClass().toString() + this.getClass().toString() + " CastErrors started");
	PrintAction(this.getClass().getName());
	PrintAction("Throwable message: " + T.getMessage());
	PrintAction("Throwable cause: " + T.getCause());
	PrintAction("Throwable class: " + T.getClass());
	PrintAction("Origin stack " + 1 + ": ");
	PrintAction("Class: " + T.getStackTrace()[0].getClassName());
	PrintAction("Method: " + T.getStackTrace()[0].getMethodName());
	PrintAction("Line: " + T.getStackTrace()[0].getLineNumber());
	PrintAction("Origin stack " + 1 + ": ");
	PrintAction("Class: " + T.getStackTrace()[1].getClassName());
	PrintAction("Method: " + T.getStackTrace()[1].getMethodName());
	PrintAction("Line: " + T.getStackTrace()[1].getLineNumber());
	for (int y = 2; y < T.getStackTrace().length; y++) {
	    PrintAction(" ");
	    PrintAction("Origin stack " + y + ": ");
	    PrintAction("Class: " + T.getStackTrace()[y].getClassName());
	    PrintAction("Method: " + T.getStackTrace()[y].getMethodName());
	    PrintAction("Line: " + T.getStackTrace()[y].getLineNumber());
	}
    }
}
