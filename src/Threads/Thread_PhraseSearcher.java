package Threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import Control.Controller;
import Objects.Object_Ord;
import Objects.Object_Webpage;

public class Thread_PhraseSearcher extends Thread_Searcher{

	Controller			Class_Controller;

	Object_Webpage		LinkToCheck;
	long				ThreadstartTime	= System.currentTimeMillis();

	String				phrase;
	Thread_TimeKeeper	Timer;
	StringBuffer		DataImported	= new StringBuffer();

	int					SaveValue		= 0;

	public Thread_PhraseSearcher( Controller Class_Controller, Object_Ord Phrase ) {
		//System.out.println( this.getClass().toString() + " started" );
		this.Class_Controller = Class_Controller;
		this.phrase = Phrase.Ordet;
		URL Adresse;
		try {
			Adresse = new URL( "https://www.google.no/search?q=testing&ie=utf-8&oe=utf-8&aq=t&rls=org.mozilla:en-US:official&client=firefox-a&channel=sb&gfe_rd=cr&ei=3jMMVJ62J5K-wAO21YDgBQ" );
			this.LinkToCheck = new Object_Webpage( Adresse, 2, 1 );

			long TidA = System.currentTimeMillis();

			long TidB = System.currentTimeMillis();
			long ThreadendTime = System.currentTimeMillis();
		}
		catch ( MalformedURLException e ) {
			Class_Controller.CastErrors( e );
		}

	}

	public void run() {

		Search();
	}

	private void Search() {
		//System.out.println( this.getClass().toString() + " Search started" );
		try {
			URL url = LinkToCheck.Get_URL();

			Thread_TimeKeeper Timer = new Thread_TimeKeeper( this.getThread_Searcher() );
			Timer.start();

			URLConnection conn = url.openConnection();
			conn.setRequestProperty( "User-Agent", "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)" );
			BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
			String str;

			while ( (str = in.readLine()) != null ) {

				DataImported.append( str );
			}

			ScanStrings( DataImported );

		}
		catch ( FileNotFoundException T ) {
			LinkToCheck.Set_SelfRelationValue( 0 );

		}
		catch ( UnknownHostException T ) {
			LinkToCheck.Set_SelfRelationValue( 0 );

		}
		catch ( IOException T ) {
			System.out.println( this.getClass().toString() + " Search failed! " + LinkToCheck.Get_URL() );
			LinkToCheck.Set_SearchFailed();
			LinkToCheck.Set_LinkedRelationValue( LinkToCheck.Get_LinkedRelationValue() - 10 );
			if ( LinkToCheck.Get_LinkedRelationValue() < this.Class_Controller.InterestBorder ) {
				LinkToCheck.Set_LinkedRelationValue( Class_Controller.InterestBorder );
			}
		}
		catch ( IllegalArgumentException T ) {
			LinkToCheck.Set_SelfRelationValue( 0 );

		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
			LinkToCheck.Set_SelfRelationValue( 0 );
		}

	}

	private void ScanStrings( StringBuffer buffer ) {
		//System.out.println( this.getClass().toString() + " ScanStrings started" );
		long StartTime = System.currentTimeMillis();
		String Buffer = buffer.toString().toLowerCase();

		try {
			FindRelationValue( Buffer );
			LinkToCheck.Set_SelfRelationValue( RelationValue );

			Thread thread = new Thread_URLFinder( Class_Controller, Buffer, this.getThread_Searcher(), LinkToCheck.Get_URL().toString() );
			thread.start();

			long EndTime = System.currentTimeMillis();
			//System.out.println( System.currentTimeMillis() + " Web page relation value updated: " + LinkToCheck.Get_URL().toString() + " value=" + LinkToCheck.Get_SelfRelationValue() + " Time (ms)=" + (EndTime - StartTime) );
			if ( RelationValue > Class_Controller.InterestBorder ) {
				System.out.println( System.currentTimeMillis() + " ---> Check out " + LinkToCheck.Get_URL().toString() + " Score: " + LinkToCheck.Get_SelfRelationValue() + " Time (ms) : " + (EndTime - StartTime) );
				FindSaveValue( Buffer );
				System.out.println( System.currentTimeMillis() + " Web page save value updated: " + LinkToCheck.Get_URL().toString() + " value=" + SaveValue );
				if ( SaveValue >= 30 ) {
					SaveToFile();
				}
			}

		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
		}
		long EndTime = System.currentTimeMillis();

	}

	public int countOccurrences( String haystack, String needle ) {
		//System.out.println( this.getClass().toString() + " countOccurrences started" );

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
		//System.out.println( this.getClass().toString() + " FindRelationValue started" );
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
			Class_Controller.CastErrors( T );
		}

	}

	private void FindSaveValue( String Buffer ) {
		//System.out.println( this.getClass().toString() + " FindSaveValue started" );
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
			Class_Controller.CastErrors( T );
		}
	}

	private void SaveToFile() {
		//System.out.println( this.getClass().toString() + " SaveToFile started" );
		try {

			String Filnavn = "URLsAppendfile.txt";
			File filen;
			;
			filen = new File( Filnavn );
			if ( !filen.exists() ) {
				filen.createNewFile();
			}

			PrintStream utfil;
			FileOutputStream appendFilen = new FileOutputStream( Filnavn, true );
			utfil = new PrintStream( appendFilen );

			System.out.println( System.currentTimeMillis() + " ---> Check out " + LinkToCheck.Get_URL().toString() + " Score: " + LinkToCheck.Get_SelfRelationValue() );
			utfil.println( "" + LinkToCheck.Get_URL().toString() + "" );

			utfil.close();
		}
		catch ( IOException T ) {
			if ( T.getMessage().equals( "Access is denied" ) ) {
				SaveToFile();
			}
			else {
				Class_Controller.CastErrors( T );
			}
		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
		}
	}

}