package Control;

public class TextHandler{

	public static int InneholderSeparertOrd( String Ord, String Tekst ) {
		if ( Tekst == null ) {
			return -1;
		}
		if ( Ord == null ) {
			return -1;
		}
		if ( Tekst.indexOf( Ord ) == -1 ) {
			return -1;
		}
		Ord = Ord.toLowerCase();
		Tekst = Tekst.toLowerCase();
		if ( Ord.equals( Tekst ) == true ) {
			return 1;
		}
		if ( Tekst.contains( Ord ) == false ) {
			return -1;
		}

		int TekstStart = Tekst.indexOf( Ord );
		int TekstEnd = Tekst.indexOf( Ord ) + Ord.length();
		String charForan = Tekst.substring( Math.max( 0, (TekstStart - 1) ), TekstStart );
		//System.out.println( this.getClass().toString()+" InneholderSeparertOrd TekstEnd="+TekstEnd+" Tekst.length()="+Tekst.length() );
		String charBak = Tekst.substring( TekstEnd, Math.min( TekstEnd + 1, Tekst.length() ) );

		boolean StartBra = false;
		boolean SluttBra = false;

		if ( charForan.indexOf( " " ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.indexOf( "<dt>" ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.indexOf( "\t" ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.indexOf( "<" ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.indexOf( ">" ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.indexOf( ";" ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.indexOf( "&" ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.indexOf( "-" ) > -1 ) {
			StartBra = true;
		}
		else if ( charForan.equals( " " ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "\t" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "\"" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "<" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( ">" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "-" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "," ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( ":" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "(" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( ")" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "/" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "+" ) ) {
			StartBra = true;
		}
		else if ( charForan.equals( "," ) ) {
			StartBra = true;
		}

		if ( charBak.indexOf( " " ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( "." ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( "," ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( ":" ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( ";" ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( "</dd>" ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( "\t" ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( "<" ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( ">" ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.indexOf( "&" ) > -1 ) {
			SluttBra = true;
		}
		else if ( charBak.equals( " " ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "\t" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "\"" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "<" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( ">" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "-" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "(" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( ")" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "/" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "'" ) ) {
			SluttBra = true;
		}
		else if ( charBak.equals( "+" ) ) {
			SluttBra = true;
		}

		//System.out.println( this.getClass().toString()+" InneholderSeparertOrd charForan="+charForan+" StartBra="+StartBra+" charBak="+charBak+" SluttBra="+SluttBra );

		if ( StartBra == true && SluttBra == true ) {
			return 1;
		}
		else {
			if ( Ord.substring( Ord.length() - 1, Ord.length() ).equals( "s" ) == false ) { //sjekke for flertallsutgave av ordet
				return InneholderSeparertOrd( Ord + "s", Tekst );
			}
			else {
				return -1;
			}
		}
	}
}
