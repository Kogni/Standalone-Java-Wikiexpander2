package Control;

import java.awt.Color;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;

public final class HTML{

	/**
	 * Utility method to format a color to HTML RGB color format (e.g. #FF0000 for Color.red).
	 * @param color The color.
	 * @return the HTML RGB color string.
	 */
	public static final String format( Color c ) {
		String r = (c.getRed() < 16) ? "0" + Integer.toHexString( c.getRed() ) : Integer.toHexString( c.getRed() );
		String g = (c.getGreen() < 16) ? "0" + Integer.toHexString( c.getGreen() ) : Integer.toHexString( c.getGreen() );
		String b = (c.getBlue() < 16) ? "0" + Integer.toHexString( c.getBlue() ) : Integer.toHexString( c.getBlue() );
		return "#" + r + g + b;
	}

	/**
	 * Utility method to convert HTML to text.
	 * @param html The string containing HTML.
	 * @return a String containing the derived text .
	 */
	public static final String html2text( String html ) {
		EditorKit kit = new HTMLEditorKit();
		Document doc = kit.createDefaultDocument();
		doc.putProperty( "IgnoreCharsetDirective", Boolean.TRUE );
		try {
			Reader reader = new StringReader( html );
			kit.read( reader, doc, 0 );
			return doc.getText( 0, doc.getLength() );
		}
		catch ( Exception e ) {
			return "";
		}
	}

}
