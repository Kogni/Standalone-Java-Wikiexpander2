package Threads;

import java.util.StringTokenizer;

import Control.Controller;

class Thread_URLFinder extends Thread{

	private final Controller		Class_Controller;
	private final Thread_Searcher	Owner;
	long			ThreadstartTime	= System.currentTimeMillis();
	private final String			Data;

    public Thread_URLFinder( Controller Class_Controller, String Data, Thread_Searcher thread_Searcher, String URLsource ) {

		this.Class_Controller = Class_Controller;
		this.Owner = thread_Searcher;
		this.Data = Data;
        String URLsource1 = URLsource;
		try {
			//run();
		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
		}
	}

	public void run() {

		try {
			SplitLine1( Data );

		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
		}

	}

	private void SplitLine1( String Pagecontent ) {

		StringTokenizer token = new StringTokenizer( Pagecontent );

		String Frase;
		while ( token.hasMoreTokens() ) {
			Frase = token.nextToken(); //<- Henter 1 og 1 string fra pagecontent
			Frase = OnlyURLS( Frase ); //<-sletter strings som ikke inneholder noen url, og returnerer f.o.m http dersom stringen inneholder en url
			Frase = CleanRightofURL( Frase ); // <-Sletter alt p� h�yresiden av Frase, som ikke er en del av url.
			Frase = OnlyURLS( Frase );
			if (!Frase.equals("")) { //<- betyr at Frase inneholder en url

				this.Class_Controller.SaveURL( Frase, "Thread searching", Owner.RelationValue );

			}
		}

	}

	private String OnlyURLS( String source ) {

		int idx = source.indexOf( "http://" );
		if ( idx == -1 ) {
			return "";
		}
		else {
			String Sub = source.substring( idx, source.length() );
			Sub = CleanRightofURL( Sub );
			Sub = IgnoreURLS( Sub );
			return Sub
					+ " ";
		}
	}

	private String IgnoreURLS( String source ) {

		if ( source.equals( "http:" ) ) {
			source = "";
			return source;
		}

		if ( source.indexOf( ".png" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".png" ) == (source.length() - ".png".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".gif" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".gif" ) == (source.length() - ".gif".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".jpg" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".jpg" ) == (source.length() - ".jpg".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".css" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".css" ) == (source.length() - ".css".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".js" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".js" ) == (source.length() - ".js".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".rdf" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".rdf" ) == (source.length() - ".rdf".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".pdf" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".pdf" ) == (source.length() - ".pdf".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".ico" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".ico" ) == (source.length() - ".ico".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".php" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".php" ) == (source.length() - ".php".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".php4" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".php4" ) == (source.length() - ".php4".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".py" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".py" ) == (source.length() - ".py".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".dtd" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".dtd" ) == (source.length() - ".dtd".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".epl" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".epl" ) == (source.length() - ".epl".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".jspa" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".jspa" ) == (source.length() - ".jspa".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".txt" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".txt" ) == (source.length() - ".txt".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".swf" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".swf" ) == (source.length() - ".swf".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".asp" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".asp" ) == (source.length() - ".asp".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".aspx" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".aspx" ) == (source.length() - ".aspx".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".mil" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".mil" ) == (source.length() - ".mil".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".mvc" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".mvc" ) == (source.length() - ".mvc".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".g" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".g" ) == (source.length() - ".g".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".shtml" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".shtml" ) == (source.length() - ".shtml".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		if ( source.indexOf( ".mv" ) == -1 ) {
		}
		else {
			if ( source.indexOf( ".mv" ) == (source.length() - ".mv".length()) ) {
				source = "";
				return source;
			}
			else {

			}
		}

		return source;
	}

	private String CleanRightofURL( String source ) {

		if ( source.indexOf( ":http" ) > -1 ) {

			source = source.substring( 0, source.indexOf( ":http" ) );

		}

		if ( source.indexOf( ";" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( ";" ) );
		}

		if ( source.indexOf( "?" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "?" ) );
		}

		if ( source.indexOf( "\"" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "\"" ) );
		}

		if ( source.indexOf( "#" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "#" ) );
		}

		if ( source.indexOf( " " ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( " " ) );
		}

		if ( source.indexOf( "///" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "///" ) );
		}

		if ( source.indexOf( "=" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "=" ) );
		}

		if ( source.indexOf( ")" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( ")" ) );
		}

		if ( source.indexOf( "<" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "<" ) );
		}

		if ( source.indexOf( ">" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( ">" ) );
		}

		if ( source.indexOf( "'" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "'" ) );
		}

		if ( source.indexOf( "&" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "&" ) );
		}

		if ( source.indexOf( ".." ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( ".." ) );
		}

		if ( source.indexOf( "(" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "(" ) );
		}

		if ( source.indexOf( ")" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( ")" ) );
		}

		if ( source.indexOf( "[" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "[" ) );
		}

		if ( source.indexOf( "]" ) == -1 ) {
		}
		else {
			source = source.substring( 0, source.indexOf( "]" ) );
		}

		return source;
	}

}