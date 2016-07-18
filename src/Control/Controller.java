package Control;

import java.net.MalformedURLException;
import java.net.URL;

import GUI.Brain_Forside;
import Objects.Object_Ord;
import Objects.Object_Ordbok;
import Objects.Object_Webpage;
import Threads.Thread_LinkScanner;
import Threads.Thread_PhraseSearcher;

public class Controller{

	//Brain_Links Class_Brain_Links;
	public Brain_PageManager	Class_Brain_PageManager;
	Brain_IO					Class_Brain_IO;
	Brain_Ordbok				Class_Brain_Ordbok;

	TimeKeeper					Class_TimeKeeper;
	int							TickInterval		= 40;
	//Thread_LinkScanner Threads[];
	int							ThreadsRunning		= 0;
	int							ThreadsStarted		= 0;
	int							ThreadsCompleted	= 0;

	public Object_Ordbok		OrdBok;

	boolean						Doneloading			= false;

	public int					InterestBorder		= 30;

	Thread_LinkScanner			ScannerThread;

	Brain_Forside				class_Brain_Forside;

	public Controller() {
		//System.out.println( this.getClass().toString() + " started" );
		class_Brain_Forside = new Brain_Forside();
		Class_Brain_IO = new Brain_IO( this );
		//PrintAction( this.getClass().toString() + " Loading saved phrases" );
		OrdBok = new Object_Ordbok( this );
		Class_Brain_PageManager = new Brain_PageManager( this );
		Class_Brain_Ordbok = new Brain_Ordbok( this );
		Class_TimeKeeper = new TimeKeeper( this, TickInterval );
		//System.out.println( this.getClass().toString() + " created all brains. Now preparing for searching." );
		SaveURL( "https://hjernebark.atlassian.net/wiki/display/PSYK/_Psykologi+labels", "Controller", 10000 );
		SaveWordList( OrdBok.Ord );
		//PrintAction( this.getClass().toString() + " Done loading saved phrases" );
		//PrintAction( this.getClass().toString() + " Googling phrases" );
		GoogleOrdliste(); //lagrer URL's for alle ord i ordlista, så det er klart til å søkes opp
		Class_Brain_IO.Load_URLs();
		//PrintAction( this.getClass().toString() + " Done loading saved URLs" );
		Doneloading = true; //OrderNewSearch, SearchURL og SearchPhrase funker ikke før denne er true

		TimeTick( "IO" );
	}

	public void TimeTick( String Sender ) {
		//System.out.println( this.getClass().toString() + " TimeTick started" );
		if ( Sender.equals( "LinkThread completed" ) ) {
			ThreadsRunning--;
			ThreadsCompleted++;
			//System.out.println( "ThreadsCompleted="+ThreadsCompleted );
		}
		Class_Brain_PageManager.OrderNewSearch();

	}

	public void PrintAction( String actionMessage ) {
		try {
			class_Brain_Forside.AddProgressMessage( actionMessage );
			Class_Brain_IO.PrintActionLog( actionMessage );
		}
		catch ( Exception e ) {
			this.CastErrors( e );
		}
	}

	public void SearchPhrase( Object_Ord temp ) {
		//System.out.println( this.getClass().toString() + " SearchPhrase started. " );
		//System.out.println( "Doneloading=" + Doneloading + " temp=" + temp );
		if ( Doneloading == false ) {
			return;
		}

		if ( temp == null ) {
			return;
		}
		else if ( temp.Ordet.toString().equals( "" ) ) {
			return;
		}
		long TidA = System.currentTimeMillis();
		temp.Set_Searched();
		long TidB = System.currentTimeMillis();

		//PrintAction( this.getClass().toString() + " Searching for phrase " + temp.Ordet );
		Thread thread = new Thread_PhraseSearcher( this, temp );
		long TidC = System.currentTimeMillis();

		ThreadsRunning++;
		ThreadsStarted++;
		long TidD = System.currentTimeMillis();

	}

	public void SearchURL( Object_Webpage temp ) {
		//System.out.println( this.getClass().toString() + " SearchURL started. " + temp.Get_URL() );
		if ( Doneloading == false ) {
			return;
		}

		if ( temp == null ) {
			return;
		}
		else if ( temp.Get_URL().toString().equals( "" ) ) {
			return;
		}
		long TidA = System.currentTimeMillis();
		temp.Set_Searched();
		long TidB = System.currentTimeMillis();
		if ( (ScannerThread != null) && ScannerThread.isAlive() ) {
			System.out.println( this.getClass().toString() + " " + ScannerThread.Status );
		}

		//PrintAction( this.getClass().toString() + " Scanning next URL: " + temp.Get_URL().toString() );
		ScannerThread = new Thread_LinkScanner( this, temp );
		ScannerThread.run();
		//System.out.println( this.getClass().toString() + " SearchURL created search thread for link" );
		long TidC = System.currentTimeMillis();

		ThreadsRunning++;
		ThreadsStarted++;
		long TidD = System.currentTimeMillis();
		this.Class_Brain_PageManager.Soeker = false;
	}

	public void SaveURL( String line, String Sender, int RelatedWordsFound ) {
		//System.out.println( this.getClass().toString() + " SaveURL started. URL=" + line );
		if ( line.equals( "" ) ) {
			return;
		}
		else if ( line.equals( "http:/" ) ) {
			return;
		}
		else if ( line.equals( "http:" ) ) {
			return;
		}
		URL Adresse;
		try {
			Adresse = new URL( line );
			Object_Webpage New = new Object_Webpage( Adresse, RelatedWordsFound, 0 );
			//Object_Website Rootsite = Class_Brain_PageManager.FigureRootsite( New, Sender );
			if ( Class_Brain_PageManager.isAdded( New ) == false ) {
				//PrintAction( "Adding new URL " + line );
				Class_Brain_PageManager.InsertLink( line, RelatedWordsFound, "controller.SaveURL" );
			}
		}
		catch ( MalformedURLException e ) {
		}
	}

	public Object_Ord[] GetOrdliste() {
		//System.out.println( this.getClass().toString() + " GetOrdliste started" );
		return this.Class_Brain_Ordbok.Get_Ordliste();
	}

	public void SaveWordList( Object_Ord[] ord ) {
		Class_Brain_Ordbok.SaveWordList( ord );
	}

	public void SaveWord( String phrase, int Value ) {
		PrintAction( this.getClass().toString() + " SaveWord" + phrase + " Value=" + Value );
		OrdBok.SaveWord( phrase, Value );
	}

	public void GoogleOrdliste() {
		Class_Brain_Ordbok.GoogleOrdliste();
	}

	public void SaveSentence( String sentence, String Phrase ) {
		this.Class_Brain_IO.SavePhrase( Phrase, sentence );
	}

	public void SaveDatabase( Object_Webpage UpdatedPage ) {
		if ( Class_Brain_IO != null ) {
			if ( Doneloading == true ) {
				System.out.println( "ber om å lagre database " );
				//PrintAction( this.getClass().toString() + " Saving URLs to file" );
				Class_Brain_IO.SaveAll( Class_Brain_PageManager.GetLinksToSave() );
			}
		}
	}

	public void CastErrors( Exception T ) {
		System.out.println( this.getClass().toString() + " CastErrors started" );
		System.err.println( this.getClass().getName() );
		System.err.println( "Throwable message: " + T.getMessage() );
		System.err.println( "Throwable cause: " + T.getCause() );
		System.err.println( "Throwable class: " + T.getClass() );

		System.err.println( "Origin stack " + 1 + ": " );
		System.err.println( "Class: " + T.getStackTrace()[0].getClassName() );
		System.err.println( "Method: " + T.getStackTrace()[0].getMethodName() );
		System.err.println( "Line: " + T.getStackTrace()[0].getLineNumber() );

		System.err.println( "Origin stack " + 1 + ": " );
		System.err.println( "Class: " + T.getStackTrace()[1].getClassName() );
		System.err.println( "Method: " + T.getStackTrace()[1].getMethodName() );
		System.err.println( "Line: " + T.getStackTrace()[1].getLineNumber() );

		for ( int y = 2; y < T.getStackTrace().length; y++ ) {
			System.err.println( " " );
			System.err.println( "Origin stack " + y + ": " );
			System.err.println( "Class: " + T.getStackTrace()[y].getClassName() );
			System.err.println( "Method: " + T.getStackTrace()[y].getMethodName() );
			System.err.println( "Line: " + T.getStackTrace()[y].getLineNumber() );
		}
	}

}
