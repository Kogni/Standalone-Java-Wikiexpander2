package Control;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import Objects.Object_Ord;
import Objects.Object_Ordbok_Old;

public class Brain_Ordbok{

	Controller		Class_Controller;
	Object_Ord[]	Ordliste;

	public Brain_Ordbok( Controller controller ) {
		System.out.println( this.getClass().toString() + " started" );
		this.Class_Controller = controller;

		Ordliste = new Object_Ord[9999999];

		Object_Ordbok_Old Tempordbok = Class_Controller.OrdBok;
	}

	public Object_Ord[] Get_Ordliste() {
		return Ordliste;
	}

	public void SaveWordList( Object_Ord[] ord ) {
		this.Ordliste = ord;
	}

	public void GoogleOrdliste() {
		Ordliste = Class_Controller.OrdBok.Ord;
		int Count = 0;
		for ( int x = 0; x < Ordliste.length; x++ ) {
			if ( Ordliste[x] != null ) {
				if ( Ordliste[x].Ordet.equals( "" ) == false ) {
					Count++;
					if ( Ordliste[x].RelationValue > 0 ) {
						Class_Controller.SearchPhrase( Ordliste[x] );

						URL New = null;
						try {
							String tempurl = "http://www.google.no/search?q=" + Ordliste[x].Ordet;
							tempurl = tempurl.replaceAll( " ", "+" );
							New = new URL( tempurl );
							URLConnection myURLConnection = New.openConnection();
							myURLConnection.connect();
						}
						catch ( MalformedURLException e ) { // new URL() failed
						}
						catch ( Exception T ) {
							Class_Controller.CastErrors( T );
						}
						if ( New != null ) {
							Class_Controller.SaveURL( New.toString(), "Ordbok", 100 );
						}
						else {
							System.err.println( this.getClass().getName() );
						}

						New = null;
						try {
							New = new URL( "http://www.google.no/search?q=" + Ordliste[x].Ordet.replaceAll( " ", "" ) );
							URLConnection myURLConnection = New.openConnection();
							myURLConnection.connect();
						}
						catch ( MalformedURLException e ) { // new URL() failed
						}
						catch ( Exception T ) {
							Class_Controller.CastErrors( T );
						}
						if ( New != null ) {
							Class_Controller.SaveURL( New.toString(), "Ordbok", 999 );
						}
						else {
							System.err.println( this.getClass().getName() );
						}

						New = null;
						try {
							New = new URL( "http://www.google.no/search?q=" + Ordliste[x].Ordet.replaceAll( " ", "-" ) );
							URLConnection myURLConnection = New.openConnection();
							myURLConnection.connect();
						}
						catch ( MalformedURLException e ) { // new URL() failed
						}
						catch ( Exception T ) {
							Class_Controller.CastErrors( T );
						}
						if ( New != null ) {
							Class_Controller.SaveURL( New.toString(), "Ordbok", 999 );
						}
						else {
							System.err.println( this.getClass().getName() );
						}
					}
				}
			}
		}
	}

}
