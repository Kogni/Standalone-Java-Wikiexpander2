package Threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import Control.Controller;
import Control.HTML;
import Control.TextHandler;
import Objects.Object_Ord;
import Objects.Object_Webpage;

public class Thread_LinkScanner extends Thread_Searcher{

	Controller			Class_Controller;

	Object_Webpage		LinkToCheck;
	long				ThreadstartTime		= System.currentTimeMillis();
	public int			Status;

	URL					url;
	String				Filnavn				= "URLs.txt";

	Thread_TimeKeeper	Timer;
	StringBuffer		DataImported		= new StringBuffer();

	int					SaveValue			= 0;
	int					RelatedWordsFound	= 0;

	public Thread_LinkScanner( Controller Class_Controller, Object_Webpage LinkToCheck ) {
		this.Class_Controller = Class_Controller;
		this.LinkToCheck = LinkToCheck;
		long TidA = System.currentTimeMillis();
		long TidB = System.currentTimeMillis();
		long ThreadendTime = System.currentTimeMillis();
		Status = 0;
	}

	public void run() {
		Scan();
	}

	private void Scan() {
		Status = 1;
		try {
			//Class_Controller.PrintAction( this.getClass().toString() + " opening URL " + LinkToCheck.Get_URL().toString() );
			Status = 12;
			URL url = LinkToCheck.Get_URL();
			Thread_TimeKeeper Timer = new Thread_TimeKeeper( this );
			Timer.start();
			Status = 13;
			URLConnection conn = url.openConnection();
			conn.setRequestProperty( "User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)" );
			BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
			String str;
			Status = 14;
			//Class_Controller.PrintAction( this.getClass().toString() + " getting contents on " + LinkToCheck.Get_URL().toString() );
			while ( (str = in.readLine()) != null ) {
				String newLine = str;
				//Class_Controller.PrintAction( this.getClass().toString() + " Before CleanHTML: " + newLine );
				newLine = CleanHTML( newLine );
				//Class_Controller.PrintAction( this.getClass().toString() + " AfterCleanHTML: " + newLine );
				DataImported.append( str ); //this CANNOT be cleansed for html!
				if ( newLine.length() > 3 ) {
					if ( LinkToCheck.Get_URL().toString().contains( "hjernebark.atlassian" ) ) {
						FillWordListFromPage( str ); //må kjøres med UNCLEANSED HTML
					}
					FindSaveWordlist( newLine ); //kjøres med cleansed tekst. Finner ut antall relaterte ord funnet
				}
			}
			//Class_Controller.PrintAction( this.getClass().toString() + " done getting contents." );
			Status = 15;
			//Class_Controller.PrintAction( this.getClass().toString() + " scanning text on " + LinkToCheck.Get_URL().toString() );
			ScanStrings( DataImported );//skal sjekke om url er tilstrekkelig relatert
			Status = 2;
			if ( LinkToCheck.Get_URL().toString().contains( "hjernebark.atlassian" ) ) {
				this.Class_Controller.GoogleOrdliste(); //google ordlista etter at ordlista blir fylt i linja over
			}
		}
		catch ( FileNotFoundException T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " getting contents failed with " + T.getClass() );
			LinkToCheck.Set_SelfRelationValue( LinkToCheck.Get_SelfRelationValue() - 10 );
		}
		catch ( UnknownHostException T ) {
			LinkToCheck.Set_SelfRelationValue( LinkToCheck.Get_SelfRelationValue() - 10 );
			Class_Controller.PrintAction( this.getClass().toString() + " getting contents failed with " + T.getClass() );
		}
		catch ( ConnectException T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " getting contents failed with " + T.getClass() );
			LinkToCheck.Set_SelfRelationValue( LinkToCheck.Get_SelfRelationValue() - 10 );
		}
		catch ( IOException T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " getting contents failed with " + T.getClass() );
			LinkToCheck.Set_LinkedRelationValue( LinkToCheck.Get_LinkedRelationValue() - 10 );
			if ( LinkToCheck.Get_LinkedRelationValue() < this.Class_Controller.InterestBorder ) {
				LinkToCheck.Set_LinkedRelationValue( Class_Controller.InterestBorder );
			}
			this.Class_Controller.CastErrors( T );
		}
		catch ( IllegalArgumentException T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " getting contents failed with " + T.getClass() );
			LinkToCheck.Set_SelfRelationValue( LinkToCheck.Get_SelfRelationValue() - 10 );

		}
		catch ( Exception T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " getting contents failed with " + T.getClass() );
			this.Class_Controller.CastErrors( T );
			LinkToCheck.Set_SelfRelationValue( LinkToCheck.Get_SelfRelationValue() - 10 );
		}
		Status = 3;
		//Class_Controller.PrintAction( "Done scanning " + LinkToCheck.Get_URL().toString() );
	}

	private void ScanStrings( StringBuffer buffer ) {//skal sjekke om url er tilstrekkelig relatert
		//Class_Controller.PrintAction( this.getClass().toString() + " Scanning text on " + LinkToCheck.Get_URL().toString() + " " + LinkToCheck.Get_URL().toString().contains( "hjernebark.atlassian" ) );
		long StartTime = System.currentTimeMillis();
		String Buffer = buffer.toString().toLowerCase();
		Status = 151;

		Status = 152;
		try {
			//Class_Controller.PrintAction( this.getClass().toString() + " Tries to estimate relation value for " + LinkToCheck.Get_URL().toString() );
			Status = 1521;
			LinkToCheck.Set_SelfRelationValue( RelatedWordsFound * 100 );
			Status = 1522;
			Thread thread = new Thread_URLFinder( Class_Controller, Buffer, this, LinkToCheck.Get_URL().toString() );
			thread.start();
			Status = 1523;
			long EndTime = System.currentTimeMillis();
			if ( RelatedWordsFound * 100 > Class_Controller.InterestBorder ) {
				//Class_Controller.PrintAction( this.getClass().toString() + " Link is relevant: " + LinkToCheck.Get_URL().toString() + " " + RelatedWordsFound );
				Status = 1524;
				Status = 1525;
				if ( (RelatedWordsFound > 0) ) {
					SaveToFile();
					Class_Controller.PrintAction( this.getClass().toString() + " Related sentences found: " + RelatedWordsFound );
				}
				else {
					//Class_Controller.PrintAction( this.getClass().toString() + " Nothing interesting found there." );
				}
				Status = 1526;
			}
			else {
				//Class_Controller.PrintAction( this.getClass().toString() + " No related words found here" );
			}
			Status = 153;

		}
		catch ( Exception T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " Failed to estimate relation value on " + LinkToCheck.Get_URL().toString() );
			this.Class_Controller.CastErrors( T );
		}
		long EndTime = System.currentTimeMillis();
		Status = 154;
	}

	private void FindSaveWordlist( String newLine ) { //check if the line contains any saved phrases from ordbok
		Object_Ord[] TempOrdbok = this.Class_Controller.GetOrdliste();
		Status = 15231;
		for ( int X = 0; X < TempOrdbok.length; X++ ) {
			Status = 15232;
			if ( (TempOrdbok[X] != null) && (!TempOrdbok[X].Ordet.equals( "" )) ) {
				Status = 15233;
				//Class_Controller.PrintAction( this.getClass().toString() + " FindSaveWordlist -" + TempOrdbok[X].Ordet + "- " + TempOrdbok[X].RelationValue );
				if ( TempOrdbok[X].RelationValue != 0 ) {
					Status = 15234;
					Status = 15235;
					FindSaveSentence( newLine, TempOrdbok[X].Ordet );
					Status = 15236;
				}
			}
			else {
				X = TempOrdbok.length;
			}
		}
		Status = 15237;
	}

	private void FindSaveSentence( String newLine, String Ordet ) {
		//Class_Controller.PrintAction( this.getClass().toString() + " FindSaveSentence " + newLine );
		Status = 152351;
		newLine = newLine.replaceAll( "\n", ". " );
		while ( TextHandler.InneholderSeparertOrd( Ordet, newLine ) > -1 ) {
			Status = 152352;
			int punktum_start = newLine.indexOf( "." );
			if ( punktum_start < 0 ) {
				FindSaveWord( newLine, Ordet );
				return;
			}
			String sentence = newLine.substring( 0, punktum_start + 1 );
			if ( sentence.length() > 5 ) {
				Status = 152353;
				FindSaveWord( sentence, Ordet );
				Status = 152354;
			}
			newLine = newLine.substring( sentence.length() );
			Status = 152355;
		}
		Status = 152356;
	}

	private void FindSaveWord( String sentence, String Ordet ) {
		//Class_Controller.PrintAction( this.getClass().toString() + " FindSaveWord " + sentence );
		Status = 1523531;
		if ( TextHandler.InneholderSeparertOrd( Ordet, sentence ) > -1 ) {
			Status = 1523532;
			if ( (sentence.length() > 5) && (TextHandler.InneholderSeparertOrd( Ordet, sentence ) > -1) ) {
				RelatedWordsFound++;
				this.Class_Controller.SaveSentence( sentence, Ordet );
				//Class_Controller.PrintAction( "Found relevance: " + Ordet );
			}
		}
		Status = 1523533;
	}

	private void FillWordListFromPage( String newLine ) { //her skal alle fraser bli plukket opp for å puttes i ordboka
		//Class_Controller.PrintAction( this.getClass().toString() + " FillWordListFromPage -" + newLine + "-" );
		String string = newLine.toString();
		while ( string.toString().contains( "<li><a class=\"label\" rel=\"nofollow\" href=\"" ) ) {
			int start = string.indexOf( "<li><a class=\"label\" rel=\"nofollow\" href=\"" );
			string = string.substring( start + "<li><a class=\"label\" rel=\"nofollow\" href=\"".length() );
			start = string.indexOf( "\">" );//der frasen egentlig begynner
			if ( start == -1 ) {
				return;
			}
			string = string.substring( start + 2 );

			int slutt = string.indexOf( "</a></li>" );//der frasen slutter
			if ( slutt < 0 ) {

				return;
			}

			String phrase = string.substring( 0, slutt );
			//Class_Controller.PrintAction( "Plukket ut phrase: " + phrase );
			if ( phrase.length() > 20 ) {
			}
			else if ( phrase.length() < 2 ) {
			}
			else {
				this.Class_Controller.SaveWord( phrase, 30 );
			}
			string = string.substring( string.indexOf( phrase ) );
		}
	}

	public int countOccurrences( String haystack, String needle ) {
		int count = 0;
		int lastIndex = haystack.indexOf( needle, 0 );

		while ( lastIndex != -1 ) {

			haystack = haystack.substring( (lastIndex + needle.length()), haystack.length() );
			lastIndex = haystack.indexOf( needle, 0 );

			if ( lastIndex != -1 ) {
				count++;
			}
		}
		return count;
	}

	private void FindRelationValue( String Buffer ) {
		//Class_Controller.PrintAction( this.getClass().toString() + " FindRelationValue" );
		try {
			RelationValue = 0;
			Object_Ord[] TempOrdbok = this.Class_Controller.GetOrdliste();
			for ( int X = 0; X < TempOrdbok.length; X++ ) {
				if ( TempOrdbok[X] != null ) {
					if ( TempOrdbok[X].RelationValue != 0 ) {
						int Bonus = 0;
						int Occurence1 = countOccurrences( Buffer, TempOrdbok[X].Ordet.toLowerCase() );
						int Occurence2 = countOccurrences( Buffer, " " + TempOrdbok[X].Ordet.toLowerCase() + " " );
						int Occurence3 = countOccurrences( Buffer, TempOrdbok[X].Ordet.toLowerCase().replaceAll( " ", "" ) );
						int Occurence4 = countOccurrences( Buffer, TempOrdbok[X].Ordet.toLowerCase().replaceAll( " ", "-" ) );

						Bonus = Bonus + (Math.min( Occurence1, 5 ) * TempOrdbok[X].RelationValue);
						Bonus = Bonus + (Math.min( Occurence2, 5 ) * TempOrdbok[X].RelationValue * 2);
						Bonus = Bonus + (Math.min( Occurence3, 5 ) * TempOrdbok[X].RelationValue);
						Bonus = Bonus + (Math.min( Occurence4, 5 ) * TempOrdbok[X].RelationValue);
						RelationValue = RelationValue + Bonus;
					}
				}
				else {
					X = TempOrdbok.length;
				}
			}
		}
		catch ( Exception T ) {
			this.Class_Controller.CastErrors( T );
		}
	}

	private void FindSaveValue( String Buffer ) {
		//Class_Controller.PrintAction( this.getClass().toString() + " FindSaveValue" );
		try {
			SaveValue = 0;
			Object_Ord[] TempOrdbok = this.Class_Controller.GetOrdliste();
			for ( int X = 0; X < TempOrdbok.length; X++ ) {
				if ( TempOrdbok[X] != null ) {
					if ( TempOrdbok[X].SaveValue != 0 ) {
						int Bonus = 0;
						int Occurence1 = countOccurrences( Buffer, TempOrdbok[X].Ordet.toLowerCase() );
						int Occurence2 = countOccurrences( Buffer, " " + TempOrdbok[X].Ordet.toLowerCase() + " " );
						int Occurence3 = countOccurrences( Buffer, TempOrdbok[X].Ordet.toLowerCase().replaceAll( " ", "" ) );
						int Occurence4 = countOccurrences( Buffer, TempOrdbok[X].Ordet.toLowerCase().replaceAll( " ", "-" ) );

						Bonus = Bonus + (Math.min( Occurence1, 5 ) * TempOrdbok[X].SaveValue);
						Bonus = Bonus + (Math.min( Occurence2, 5 ) * TempOrdbok[X].SaveValue * 2);
						Bonus = Bonus + (Math.min( Occurence3, 5 ) * TempOrdbok[X].SaveValue);
						Bonus = Bonus + (Math.min( Occurence4, 5 ) * TempOrdbok[X].SaveValue);
						SaveValue = SaveValue + Bonus;
					}
				}
				else {
					X = TempOrdbok.length;
				}
			}
		}
		catch ( Exception T ) {
			this.Class_Controller.CastErrors( T );
		}
	}

	private String CleanHTML( String source ) {

		if ( source.contains( "no\",\"jam\":0,\"jsonp\":true,\"msgs\"" ) ) {
			return "";
		}
		source = HTML.html2text( source );
		source = replaceAll( source, "Ã¥", "å" );
		source = replaceAll( source, "Ã¸", "ø" );
		source = replaceAll( source, "Ã¦", "æ" );
		source = replaceAll( source, "â€™", "'" );
		source = replaceAll( source, "â€œ", "\"" );

		if ( source.length() < 6 ) {
			source = "";
		}

		return source;

	}

	private String replaceAll( String source, String toReplace, String replacement ) {
		int idx = source.lastIndexOf( toReplace );
		if ( idx != -1 ) {
			StringBuffer ret = new StringBuffer( source );
			ret.replace( idx, idx + toReplace.length(), replacement );
			while ( (idx = source.lastIndexOf( toReplace, idx - 1 )) != -1 ) {
				ret.replace( idx, idx + toReplace.length(), replacement );
			}
			source = ret.toString();
		}

		return source;
	}

	private void SaveToFile() {
		//Class_Controller.PrintAction( this.getClass().toString() + " SaveToFile" );
		try {

			String Filnavn = "URLs.txt";
			File filen;
			filen = new File( Filnavn );
			if ( !filen.exists() ) {
				filen.createNewFile();
			}

			PrintStream utfil;
			FileOutputStream appendFilen = new FileOutputStream( Filnavn, true );
			utfil = new PrintStream( appendFilen );

			//Class_Controller.PrintAction( "--> Looks interesting: " + LinkToCheck.Get_URL().toString() );
			System.out.println( System.currentTimeMillis() + " ---> Seems related: " + LinkToCheck.Get_URL().toString() + " Score: " + LinkToCheck.Get_SelfRelationValue() );
			utfil.println( "" + LinkToCheck.Get_URL().toString() + "" );

			utfil.close();
		}
		catch ( IOException T ) {
			if ( T.getMessage().equals( "Access is denied" ) ) {
				SaveToFile();
			}
			else {
				this.Class_Controller.CastErrors( T );
			}
		}
		catch ( Exception T ) {
			this.Class_Controller.CastErrors( T );
		}
	}

}