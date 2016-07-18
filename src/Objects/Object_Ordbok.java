package Objects;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import Control.Controller;

public class Object_Ordbok extends Object_Ordbok_Old{

	Controller	Class_Controller;

	String		Filnavn		= "Labels.txt";
	File		filen;
	boolean		DoneLoading	= false;

	public Object_Ordbok( Controller Class_Controller ) {
		//System.out.println( this.getClass().toString() + " started" );
		this.Class_Controller = Class_Controller;
		Load();
	}

	public void Load() {
		try {
			//Class_Controller.PrintAction( this.getClass().toString() + " Loading phrases from file " + Filnavn );
			filen = new File( Filnavn );
			if ( !filen.exists() ) {
				filen.createNewFile();
			}

			FileInputStream fstream2 = new FileInputStream( filen );
			DataInputStream in2 = new DataInputStream( fstream2 );
			int X = 0;
			while ( in2.available() > 0 ) {
				SaveWord( in2.readLine(), 100 );
			}
			//Class_Controller.PrintAction( this.getClass().toString() + " Done loading from file " + Filnavn );
			DoneLoading = true;
		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
		}
	}

	public void SaveWord( String phrase, int Value ) {
		if ( phrase.length() < 3 ) {
			return;
		}
		phrase = phrase.replaceAll( "_", " " );
		int count = 0;
		for ( int x = 0; x < Ord.length; x++ ) {
			count++;
			if ( Ord[x] != null ) {
				if ( Ord[x].Ordet.equals( "" ) ) {
					//Class_Controller.PrintAction( "Adding new phrase: " + phrase );
					Ord[x].Ordet = phrase;
					Ord[x].SaveValue = Value;
					Ord[x].RelationValue = Value;
					SavePhrase( phrase );
					return;

				}
				else {
					if ( Ord[x].Ordet.equals( phrase ) ) {
						return;
					}
				}
			}
			else {
			}
		}
		//Class_Controller.PrintAction( this.getClass().toString() + " SaveWord could not save phrase, array too small! " + count );
	}

	public void SavePhrase( String phrase ) {
		//Class_Controller.PrintAction( this.getClass().toString() + " SavePhrase " + phrase );
		int Count = 0;
		if ( DoneLoading == false ) {
			return;
		}

		try {
			filen = new File( Filnavn );
			if ( !filen.exists() ) {
				filen.createNewFile();
			}

			PrintStream utfil;
			FileOutputStream appendFilen = new FileOutputStream( Filnavn, true );
			utfil = new PrintStream( appendFilen );

			utfil.println( phrase );
			utfil.close();
			Class_Controller.PrintAction( this.getClass().toString() + " Saved phrase " + phrase );
		}
		catch ( IOException T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " failed to save phrase " + phrase + " " + T.getClass() );
			Class_Controller.CastErrors( T );
		}
		catch ( Exception T ) {
			Class_Controller.PrintAction( this.getClass().toString() + " failed to save phrase " + phrase + " " + T.getClass() );
			Class_Controller.CastErrors( T );
		}
	}

}
