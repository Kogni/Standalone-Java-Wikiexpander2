package Threads;

import java.util.StringTokenizer;

import Control.Controller;

public class Thread_URLFinder extends Thread{

	Controller		Class_Controller;
	Thread_Searcher	Owner;
	long			ThreadstartTime	= System.currentTimeMillis();
	String			Data;
	String			URLsource;

	public Thread_URLFinder( Controller Class_Controller, String Data, Thread_Searcher thread_Searcher, String URLsource ) {
		//System.out.println( this.getClass().toString() + " started");

		this.Class_Controller = Class_Controller;
		this.Owner = thread_Searcher;
		this.Data = Data;
		this.URLsource = URLsource;
		try {
			//run();
		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
		}
	}

	public void run() {
		//System.out.println( this.getClass().toString() + " run started" );
		try {
			SplitLine1( Data );

		}
		catch ( Exception T ) {
			Class_Controller.CastErrors( T );
		}

	}

	private void SplitLine1( String Pagecontent ) {
		//System.out.println( this.getClass().toString() + " SplitLine1 started" );
		StringTokenizer token = new StringTokenizer( Pagecontent );
		int count = 0;
		String Frase;
		while ( token.hasMoreTokens() ) {
			Frase = token.nextToken(); //<- Henter 1 og 1 string fra pagecontent
			Frase = OnlyURLS( Frase ); //<-sletter strings som ikke inneholder noen url, og returnerer f.o.m http dersom stringen inneholder en url
			Frase = CleanRightofURL( Frase ); // <-Sletter alt på høyresiden av Frase, som ikke er en del av url.
			Frase = OnlyURLS( Frase );
			if ( Frase.equals( "" ) == false ) { //<- betyr at Frase inneholder en url
				count++;

				this.Class_Controller.SaveURL( Frase, "Thread searching", Owner.RelationValue );

			}
		}
		//System.out.println( this.getClass().toString() + " SplitLine1 finished. Links found=" + count );
	}

	private void SplitLine2( String Line ) {
		//System.out.println( this.getClass().toString() + " SplitLine2 started" );
		StringTokenizer token = new StringTokenizer( Line );
		int count = 0;
		while ( token.hasMoreTokens() ) {

			Line = token.nextToken();
			Line = OnlyURLS( Line );
			Line = CleanRightofURL( Line );
			if ( Line.equals( "" ) == false ) {
				count++;
				System.out.println( "SplitLine2: " + Line + " splits: " + (count - 1) + " " + this.getName() );
			}

		}
		//System.out.println( this.getClass().toString() + " SplitLine2 finished" );
	}

	private String CleanHTML( String source ) {
		//System.out.println( this.getClass().toString() + " CleanHTML started" );
		source = replaceAll( source, "Ã¥", "å" );
		source = replaceAll( source, "Ã¸", "ø" );
		source = replaceAll( source, "<p>", "" );
		source = replaceAll( source, "</p>", "" );
		source = replaceAll( source, "<strong>", "" );
		source = replaceAll( source, "</strong>", "" );
		source = replaceAll( source, "<br>", "" );
		source = replaceAll( source, "</br>", "" );
		source = replaceAll( source, "<br", "" );
		source = replaceAll( source, "<html>", "" );
		source = replaceAll( source, "</html>", "" );
		source = replaceAll( source, "<body>", "" );
		source = replaceAll( source, "</body>", "" );
		source = replaceAll( source, "src=", "" );
		source = replaceAll( source, "<script>", "" );
		source = replaceAll( source, "</script>", "" );
		source = replaceAll( source, "xmlns=", "" );
		source = replaceAll( source, "profile=", "" );
		source = replaceAll( source, "href=", "" );
		source = replaceAll( source, "content=", "" );
		source = replaceAll( source, "action=", "" );
		source = replaceAll( source, "value=", "" );

		source = replaceAll( source, "/>", "" );
		source = replaceAll( source, ">", "" );
		source = replaceAll( source, "'", "" );
		source = replaceAll( source, "\"", "" );
		source = replaceAll( source, ")", "" );

		return source;
	}

	private String replaceAll( String source, String toReplace, String replacement ) {
		//System.out.println( this.getClass().toString() + " replaceAll started" );
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

	private String OnlyURLS( String source ) {
		//System.out.println( this.getClass().toString() + " OnlyURLS started. source=" + source );
		int idx = source.indexOf( "http://" );
		if ( idx == -1 ) {
			return "";
		}
		else {
			String Sub = source.substring( idx, source.length() );
			Sub = CleanRightofURL( Sub );
			Sub = IgnoreURLS( Sub );
			return Sub + " ";
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
		//System.out.println( this.getClass().toString() + " CleanRightofURL started" );
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