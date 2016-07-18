
package Control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
public class TextHandler {

    //static String url;
    public static int InneholderSeparertOrd(String Ord, String Tekst) {
	if (Tekst == null) {
	    return -1;
	}
	if (Ord == null) {
	    return -1;
	}
	if (Tekst.indexOf(Ord) == -1) {
	    return -1;
	}
	Ord = Ord.toLowerCase();
	Tekst = Tekst.toLowerCase();
	if (Ord.equals(Tekst)) {
	    return 1;
	}
	if (!Tekst.contains(Ord)) {
	    return -1;
	}
	int TekstStart = Tekst.indexOf(Ord);
	int TekstEnd = Tekst.indexOf(Ord) + Ord.length();
	String charForan = Tekst.substring(Math.max(0, (TekstStart - 1)), TekstStart);
	String charBak = Tekst.substring(TekstEnd, Math.min(TekstEnd + 1, Tekst.length()));
	boolean StartBra = false;
	boolean SluttBra = false;
	if (charForan.indexOf(" ") > -1) {
	    StartBra = true;
	} else if (charForan.indexOf("<dt>") > -1) {
	    StartBra = true;
	} else if (charForan.indexOf("\t") > -1) {
	    StartBra = true;
	} else if (charForan.indexOf("<") > -1) {
	    StartBra = true;
	} else if (charForan.indexOf(">") > -1) {
	    StartBra = true;
	} else if (charForan.indexOf(";") > -1) {
	    StartBra = true;
	} else if (charForan.indexOf("&") > -1) {
	    StartBra = true;
	} else if (charForan.indexOf("-") > -1) {
	    StartBra = true;
	} else if (charForan.equals(" ")) {
	    StartBra = true;
	} else if (charForan.equals("\t")) {
	    StartBra = true;
	} else if (charForan.equals("")) {
	    StartBra = true;
	} else if (charForan.equals("\"")) {
	    StartBra = true;
	} else if (charForan.equals("<")) {
	    StartBra = true;
	} else if (charForan.equals(">")) {
	    StartBra = true;
	} else if (charForan.equals("-")) {
	    StartBra = true;
	} else if (charForan.equals(",")) {
	    StartBra = true;
	} else if (charForan.equals(":")) {
	    StartBra = true;
	} else if (charForan.equals("(")) {
	    StartBra = true;
	} else if (charForan.equals(")")) {
	    StartBra = true;
	} else if (charForan.equals("/")) {
	    StartBra = true;
	} else if (charForan.equals("+")) {
	    StartBra = true;
	} else if (charForan.equals(",")) {
	    StartBra = true;
	}
	if (charBak.indexOf(" ") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf(".") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf(",") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf(":") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf(";") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf("</dd>") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf("\t") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf("<") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf(">") > -1) {
	    SluttBra = true;
	} else if (charBak.indexOf("&") > -1) {
	    SluttBra = true;
	} else if (charBak.equals(" ")) {
	    SluttBra = true;
	} else if (charBak.equals("\t")) {
	    SluttBra = true;
	} else if (charBak.equals("")) {
	    SluttBra = true;
	} else if (charBak.equals("\"")) {
	    SluttBra = true;
	} else if (charBak.equals("<")) {
	    SluttBra = true;
	} else if (charBak.equals(">")) {
	    SluttBra = true;
	} else if (charBak.equals("-")) {
	    SluttBra = true;
	} else if (charBak.equals("(")) {
	    SluttBra = true;
	} else if (charBak.equals(")")) {
	    SluttBra = true;
	} else if (charBak.equals("/")) {
	    SluttBra = true;
	} else if (charBak.equals("'")) {
	    SluttBra = true;
	} else if (charBak.equals("+")) {
	    SluttBra = true;
	} else if (charBak.equals("|")) {
	    SluttBra = true;
	}
	if (StartBra && SluttBra) {
	    return 1;
	} else {
	    if (!Ord.substring(Ord.length() - 1, Ord.length()).equals("s")) { //sjekke for flertallsutgave av ordet
		return InneholderSeparertOrd(Ord + "s", Tekst);
	    } else {
		return -1;
	    }
	}
    }

    public static String getExtractedTextFromHTML(String original, String url) {
	/*
	 * Hvis all tekst i HTMl sendes pÃ¥ Ã¨n gang, sÃ¥ blir det Java Heap Space-error
	 * Hvis det sendes linje for linje, sÃ¥ blir ikke javascript gjenkjent.
	 */
	original = original.toLowerCase();
	while (original.contains("  ")) {//fjerner space i starten av setninger, for aa unngaa daarlige treff som ikke er setninger
	    original = original.replaceAll("  ", " ");
	}
	while (original.substring(0, 1).contains(" ")) {
	    original = original.substring(1);
	}
	String extract = "";
	String[] datas = new String[5];
	datas[0] = original;
	datas[1] = url;
	datas[2] = "getExtractedTextFromHTML Asking for extraction of " + url;
	SaveParseToFile(datas);
	datas[2] = "Original contents=" + original;
	SaveParseToFile(datas);
	datas[2] = original;
	//	extract = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	extract = removeAllCSS(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " etter removeAllCSS=" + extract;
	SaveParseToFile(datas);
	extract = removeAllHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " etter removeAllHTML=" + extract;
	SaveParseToFile(datas);
	return extract;
    }

    static String removeAllCSS(String[] datas, String Source) {
	String html = datas[0];
	String returntext = html;
	if ((html.indexOf("{") > -1)) {
	    String lineStart = html.substring(html.indexOf("{"));
	    boolean check = true;
	    while ((lineStart.contains("}")) && check) {
		lineStart = lineStart.substring(0, lineStart.indexOf("}") + 1);
		check = false;
	    }
	    String startTag = lineStart;
	    String endTag = "}";
	    returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	} else if ((html.indexOf(".") > -1)) {
	    String lineStart = html.substring(html.indexOf("."));
	    boolean check = true;
	    while ((lineStart.contains(".")) && check) {
		lineStart = lineStart.substring(lineStart.indexOf("."));
		check = false;
	    }
	    if (lineStart.length() > 3) {
		String startTag = lineStart;
		String endTag = " ";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else {
		returntext = "";
	    }
	}
	return returntext;
    }

    private static String removeAllHTML(String[] datas, String Source) { //hent ut ALL interessant tekst uten HTML. 
	String html = datas[0];
	if (html.contains(">")) {
	    //SaveToFile("171 fÃ¸r removehtml=" + html);
	    String startTag;
	    String endTag;
	    String returntext;
	    datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " etter removehtml=" + html;
	    SaveParseToFile(datas);
	    while (html.contains(">")) {
		html = lookForHTMLInString(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
		datas[0] = html;
	    }
	    return html;
	} else {
	}
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " Returning " + html + " to " + Source;
	SaveParseToFile(datas);
	return html;
    }

    private static String lookForHTMLInString(String[] datas, String Source) { //denne startes sÃ¥lenge datas[0] inneholder >
	String returntext = datas[0];
	String html = datas[0];
	while (html.contains("  ")) {
	    html = html.replaceAll("  ", " ");
	}
	while (html.substring(0, 1).contains(" ")) {
	    html = html.substring(1);
	}
	if (html.contains("<")) {
	    String lineStart = html.substring(html.indexOf("<"));
	    //lineStart = lineStart.substring(0, Math.min(200, lineStart.length()));
	    boolean check = true;
	    while ((lineStart.contains(">")) && check) {
		lineStart = lineStart.substring(0, lineStart.indexOf(">") + 1);
		check = false;
	    }
	    datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " from " + Source + " datas[0]=" + datas[0];
	    SaveParseToFile(datas);
	    String pattern = "<\\S+>.+<\\/\\S+>";
	    String startTag = lineStart;
	    String endTag = lineStart.substring(1);
	    endTag = "</" + endTag;
	    if (lineStart.indexOf("<!doctype html>") > -1) {//#1 - doctype
		startTag = "<!doctype html";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<!doctype html ") > -1) {//#1 - doctype
		startTag = "<!doctype html";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<?xml ") > -1) {//#1 - doctype
		startTag = "<?xml ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<urlset ") > -1) {//#1 - doctype
		startTag = "<urlset ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<meta") > -1) {//#4&#6 - meta
		startTag = "<meta";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<link") > -1) {//#7 - <link
		startTag = "<link";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<script") > -1) && (html.indexOf("</script>") > -1)) {//paragrafer
		startTag = "<script";
		endTag = "</script>";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<a") > -1) && (html.indexOf(">") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "<a";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<!") > -1) {
		startTag = "<!";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<img") > -1) {
		startTag = "<img";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<td") > -1) {
		startTag = "<td";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<input") > -1) {
		startTag = "<input";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<fieldset") > -1) {
		startTag = "<fieldset";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<li") > -1) {
		startTag = "<li";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<span ") > -1) {
		startTag = "<span ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<ol ") > -1) {
		startTag = "<ol ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<nobr") > -1) {
		startTag = "<nobr";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("</nobr") > -1) {
		startTag = "</nobr";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<body ") > -1) {
		startTag = "<body ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<html>") > -1) && (html.indexOf("</html>") > -1)) {//#2 - <html
		startTag = "<html>";
		endTag = "</html>";
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if ((lineStart.indexOf("<html >") > -1) && (html.indexOf("</html>") > -1)) {//#2 - <html
		startTag = "<html >";
		endTag = "</html>";
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if (lineStart.indexOf("<div ") > -1) {
		startTag = lineStart;
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</div>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</div";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if (lineStart.indexOf("<a ") > -1) {
		startTag = lineStart;
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</a>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</a";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<html ") > -1)) {//#2 - <html
		startTag = "<html ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</html>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</html";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<br />") > -1)) {
		startTag = "<br /";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<li >") > -1)) {
		startTag = "<li ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</li>") > -1)) {
		startTag = "</li";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<span ") > -1)) {//#2 - <html
		startTag = "<span ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</span>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</span";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<form ") > -1)) {//#2 - <html
		startTag = "<form ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</form>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</form";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<ul ") > -1)) {//#2 - <html
		startTag = "<ul ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</ul>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</ul";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<table ") > -1)) {//#2 - <html
		startTag = "<table ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</table>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</table";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<tbody ") > -1)) {//#2 - <html
		startTag = "<tbody ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</tbody>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</tbody";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<tr ") > -1)) {//#2 - <html
		startTag = "<tr ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</tr>") > -1)) {//dekker </h2, </a, </li, </title
		startTag = "</tr";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<td ") > -1)) {//#2 - <html
		startTag = "<td ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</td>") > -1)) {
		startTag = "</td";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<abbr ") > -1)) {//#2 - <html
		startTag = "<abbr ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</abbr>") > -1)) {
		startTag = "</abbr";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<acronym ") > -1)) {
		startTag = "<acronym  ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</acronym>") > -1)) {
		startTag = "</acronym";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<address>") > -1)) {
		startTag = "<address";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</address>") > -1)) {
		startTag = "</address";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<applet ") > -1)) {
		startTag = "<applet ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</applet>") > -1)) {
		startTag = "</applet";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<area>") > -1)) {
		startTag = "<area";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<article>") > -1)) {
		startTag = "<article";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</article>") > -1)) {
		startTag = "</article";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<label>") > -1)) {
		startTag = "<label";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</label>") > -1)) {
		startTag = "</label";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<style ") > -1)) {
		startTag = "<style ";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("</style>") > -1)) {
		startTag = "</style";
		endTag = ">";
		returntext = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	    } else if ((lineStart.indexOf("<html >") > -1) && (html.indexOf("</html>") > -1)) {//#2 - <html
		startTag = "<html >";
		endTag = "</html>";
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if ((lineStart.indexOf("<head>") > -1)) {//#3 - head
		startTag = "<head";
		endTag = ">";
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if ((lineStart.indexOf("</head>") > -1)) {//#3 - head
		startTag = "</head";
		endTag = ">";
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if ((lineStart.indexOf("<label ") > -1)) {//#3 - head
		startTag = "<label ";
		endTag = ">";
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if ((lineStart.indexOf("</label>") > -1)) {//#3 - head
		startTag = "</label";
		endTag = ">";
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if ((html.indexOf(startTag) > -1) && (html.indexOf(endTag) > -1)) {//#2 - <html
		//System.out.println(Thread.currentThread().getStackTrace()[1].getLineNumber() + " trigget pÃ¥ html regex");
		datas[3] = startTag;
		datas[4] = endTag;
		returntext = cleanTag_KeepInbetween(datas);
	    } else if (lineStart.indexOf("</") > -1) {
		datas[2] =
			Thread.currentThread().getStackTrace()[1].getLineNumber() + " ======= Found endtag with no start! ======== -> "
				+ returntext + " <-" + html;
		SaveParseToFile(datas);
		startTag = "";
		endTag = "</";
		int fra1 = 0;
		int til1 = 0;
		int fra2 = html.indexOf(endTag) + endTag.length(); //dropp ut HTML-tag'ens slutt
		int til2 = html.length();
		if (html.indexOf(startTag) > -1) {
		    til1 = html.indexOf(startTag);//dropp ut HTML-tag'ens start
		    datas[0] = html.substring(fra1, til1);
		    String texta = datas[0];
		    //String texta = extractTextFromHTML(datas);
		    String capturedText = html.substring(html.indexOf(startTag) + startTag.length(), html.indexOf(endTag));
		    if ((capturedText.contains("<")) || (capturedText.contains(">"))) {
			//capturedText = extractTextFromHTML(datas);
		    }
		    String textc = html;
		    textc = textc.substring(fra2);
		    datas[0] = textc;
		    //textc = extractTextFromHTML(datas);
		    returntext = texta + " " + capturedText + " " + textc;
		}
	    } else {
		datas[2] =
			Thread.currentThread().getStackTrace()[1].getLineNumber() + " Did not recognize html. -> " + returntext + " <-"
				+ html;
		SaveParseToFile(datas);
		return returntext;
	    }
	} else {
	    returntext = html;
	    while (returntext.contains("html>body")) {
		returntext = returntext.replaceAll("html>body", "");
	    }
	    datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " found no more html=" + returntext;
	    SaveParseToFile(datas);
	    return returntext;
	}
	//SaveToFile("884 has found bracket. Interpreted to " + returntext);
	//SaveToFile("884 has found bracket. Interpreted from " + html);
	while (returntext.contains("  ")) {//fjerner space i starten av setninger, for aa unngaa daarlige treff som ikke er setninger
	    returntext = returntext.replaceAll("  ", " ");
	}
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " to " + Source + " returning after removing html="
			+ returntext;
	/*while (datas[2].contains("  ")) {//fjerner space i starten av setninger, for aa unngaa daarlige treff som ikke er setninger
	    datas[2] = datas[2].replaceAll("  ", " ");
	}*/
	//SaveParseToFile(datas);
	return returntext;
    }

    static String runDeletion(String[] datas, String startTag, String endTag, String Source) {
	datas[3] = startTag;
	datas[4] = endTag;
	return cleanHTML_DeleteBoth(datas, Source);
    }

    private static String cleanTag_KeepInbetween(String[] datas) {
	/*
	 * Delete tags, but keep in-between contents
	  	String startTag = "<!doctype html>";
		String endTag = "<!doctype html>";
	    datas[0]=message
	    datas[1]=url
	    datas[2]=html
	    datas[3]=startTag
	    datas[4]=endTag
	    */
	String startTag = datas[3];
	String endTag = datas[4];
	String html = datas[0];
	//datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Before removing tag=" + html;
	//SaveParseToFile(datas);
	int fra1 = 0;
	int til1 = html.indexOf(startTag);//dropp ut HTML-tag'ens start
	int fra2 = html.indexOf(endTag) + endTag.length(); //dropp ut HTML-tag'ens slutt
	int til2 = html.length();
	datas[0] = html.substring(fra1, til1);
	String texta = datas[0];
	//String texta = extractTextFromHTML(datas);
	String capturedText = html.substring(html.indexOf(startTag));
	capturedText = capturedText.substring(0, capturedText.indexOf(endTag) + endTag.length());
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted from " + capturedText;
	//SaveParseToFile(datas);
	capturedText = capturedText.substring(startTag.length());
	capturedText = capturedText.substring(0, capturedText.indexOf(endTag));
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted to " + capturedText;
	//SaveParseToFile(datas);
	String textc = html;
	textc = textc.substring(fra2);
	datas[0] = textc;
	//textc = extractTextFromHTML(datas);
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " returning after removing tag: texta=" + texta
			+ " capturedText=" + capturedText + " textc=" + textc;
	SaveParseToFile(datas);
	return texta + " " + capturedText + " " + textc;
    }

    private static String cleanHTML_DeleteBoth(String[] datas, String Source) {
	/*
	 * Delete tags AND in-between contents
	String startTag = "<meta";
	String endTag = ">";
	    datas[0]=message
	    datas[1]=url
	    datas[2]=html
	    datas[3]=startTag
	    datas[4]=endTag
	    */
	String startTag = datas[3];
	String endTag = datas[4];
	String html = datas[0];
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". html=" + html;
	SaveParseToFile(datas);
	int fra1 = 0;
	int til1 = html.indexOf(startTag);//dropp ut HTML-tag'ens start
	int fra2 = html.indexOf(endTag) + endTag.length(); //dropp ut HTML-tag'ens slutt
	int til2 = html.length();
	datas[0] = html.substring(fra1, til1);
	String texta = datas[0];
	//texta = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	String capturedText = html.substring(til1);
	capturedText = capturedText.substring(0, capturedText.indexOf(endTag) + endTag.length());
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted from " + capturedText;
	SaveParseToFile(datas);
	capturedText = "";
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted to " + capturedText
			+ " Returning to " + Source;
	SaveParseToFile(datas);
	String textc = html;
	textc = textc.substring(fra2);
	datas[0] = textc;
	//textc = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " returning to " + Source + " returning after removing "
			+ startTag + ". Pre-text=" + texta + " capturedText=" + capturedText + " textc=" + textc;
	SaveParseToFile(datas);
	return texta + " " + capturedText + " " + textc;
    }

    private static String cleanHTML_KeepInBetweenBewareStart(String[] datas) {
	/*
	 * Delete tags, keep in-between contents, beware of the length of start-tag
	String startTag = "<div ";
	String endTag = "</div>";
	    datas[0]=message
	    datas[1]=url
	    datas[2]=html
	    datas[3]=startTag
	    datas[4]=endTag
	    */
	String startTag = datas[3];
	String endTag = datas[4];
	String html = datas[0];
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". html=" + html;
	SaveParseToFile(datas);
	int fra1 = 0;
	int til1 = html.indexOf(startTag);//dropp ut HTML-tag'ens start
	int fra2 = html.indexOf(endTag) + endTag.length(); //dropp ut HTML-tag'ens slutt
	int til2 = html.length();
	String texta = html.substring(fra1, til1);
	datas[0] = texta;
	//texta = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	//texta = extractText(html.substring(fra1, til1), startTag + " a. " + html.substring(0, 20) + "<---- ");
	String capturedText = html;
	capturedText = capturedText.substring(capturedText.indexOf(startTag));
	capturedText = capturedText.substring(0, capturedText.indexOf(endTag) + endTag.length());
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted from " + capturedText;
	SaveParseToFile(datas);
	//begynner nÃ¥ Ã¥ fjerne tags
	capturedText = capturedText.substring(startTag.length());
	//skal finne bakerste aktuelle tag
	String temp = capturedText;
	while (temp.indexOf(endTag) > -1) {
	    temp = temp.substring(temp.indexOf(endTag) + endTag.length()); //finner reststrengen som er bak siste aktuelle endtag
	}
	capturedText = capturedText.substring(0, capturedText.indexOf(temp)); //alt minus reststrengen bak siste aktuelle endtag
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted to " + capturedText;
	SaveParseToFile(datas);
	if ((capturedText.contains("<")) || (capturedText.contains(">"))) {
	    datas[0] = capturedText;
	    //capturedText = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	}
	//textc = finnC(html, til1, startTag, fra2);
	String textc = html;
	textc = textc.substring(fra2);
	datas[0] = textc;
	//textc = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	return texta + " " + capturedText + " " + textc;
    }

    private static String cleanHTML_KeepInbetweenBewareEnd(String[] datas) {
	/*
	 * Delete tag, but beware of the length of end-tag
	String startTag = "<html";
	String endTag = "\">";
	    datas[0]=message
	    datas[1]=url
	    datas[2]=html
	    datas[3]=startTag
	    datas[4]=endTag
	    */
	String startTag = datas[3];
	String endTag = datas[4];
	String html = datas[0];
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". html=" + html;
	SaveParseToFile(datas);
	int fra1 = 0;
	int til1 = html.indexOf(startTag);//dropp ut HTML-tag'ens start
	int fra2 = (html.indexOf(endTag) + endTag.length()) - 1; //dropp ut HTML-tag'ens slutt
	int til2 = html.length();
	datas[0] = html.substring(fra1, til1);
	String texta = datas[0];
	//String texta = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	String capturedText = html.substring(html.indexOf(startTag) + startTag.length());
	capturedText = capturedText.substring(0, capturedText.indexOf(endTag));
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted from " + capturedText;
	SaveParseToFile(datas);
	capturedText = "";
	datas[2] =
		Thread.currentThread().getStackTrace()[1].getLineNumber() + " has found " + startTag + ". Interpreted to " + capturedText;
	SaveParseToFile(datas);
	String textc = html;
	textc = textc.substring(fra2);
	datas[0] = textc;
	//textc = extractTextFromHTML(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	return texta + " " + capturedText + " " + textc;
    }

    public static void SaveParseToFile(String[] datas) {
	if (true) {
	    //return;
	}
	if ((!datas[2].equals("") && (!datas[2].equals(" ")))) {
	} else {
	    return;
	}
	datas[1] = datas[1].replaceAll("http://", "");
	datas[1] = datas[1].replaceAll("/", ".");
	datas[1] = datas[1].replaceAll("\\?", ".");
	Date Nu = new Date();
	String Filnavn = "Parselog " + (Nu.getYear() + 1900) + "." + (Nu.getMonth() + 1) + "." + Nu.getDate() + " " + datas[1] + ".txt";
	System.out.println("Texthandler SaveParseToFile Filnavn=" + Filnavn);
	try {
	    File filen;
	    filen = new File(Filnavn);
	    if (!filen.exists()) {
		filen.createNewFile();
	    }
	    PrintStream utfil;
	    FileOutputStream appendFilen = new FileOutputStream(Filnavn, true);
	    utfil = new PrintStream(appendFilen);
	    //Class_Controller.PrintAction( "--> Looks interesting: " + LinkToCheck.Get_URL().toString() );
	    String text = datas[2];
	    text = text.substring(0, Math.min(text.length(), 10000));
	    utfil.println("Æ " + text + "");
	    System.out.println("Texthandler SaveParseToFile " + text);
	    utfil.close();
	} catch (IOException T) {
	    if (T.getMessage().equals("Access is denied")) {
		SaveParseToFile(datas);
	    } else {
		System.out.println(Filnavn);
		CastErrors(T);
	    }
	} catch (StackOverflowError T) {
	    datas[0] = Filnavn;
	    SaveParseToFile(datas);
	} catch (Exception T) {
	    CastErrors(T);
	}
    }

    public static void CastErrors(Exception T) {
	System.err.println("Throwable message: " + T.getMessage());
	System.err.println("Throwable cause: " + T.getCause());
	System.err.println("Throwable class: " + T.getClass());
	System.err.println("Origin stack " + 1 + ": ");
	System.err.println("Class: " + T.getStackTrace()[0].getClassName());
	System.err.println("Method: " + T.getStackTrace()[0].getMethodName());
	System.err.println("Line: " + T.getStackTrace()[0].getLineNumber());
	System.err.println("Origin stack " + 1 + ": ");
	System.err.println("Class: " + T.getStackTrace()[1].getClassName());
	System.err.println("Method: " + T.getStackTrace()[1].getMethodName());
	System.err.println("Line: " + T.getStackTrace()[1].getLineNumber());
	for (int y = 2; y < T.getStackTrace().length; y++) {
	    System.err.println(" ");
	    System.err.println("Origin stack " + y + ": ");
	    System.err.println("Class: " + T.getStackTrace()[y].getClassName());
	    System.err.println("Method: " + T.getStackTrace()[y].getMethodName());
	    System.err.println("Line: " + T.getStackTrace()[y].getLineNumber());
	}
    }

    public static String replaceFormatting(String uncleansedHTML, String URL) {
	String extract = uncleansedHTML;
	String[] datas = new String[5];
	datas[0] = uncleansedHTML;
	datas[1] = URL;
	datas[2] = "getExtractedTextFromHTML Asking for extraction of " + URL;
	SaveParseToFile(datas);
	datas[2] = "Original contents=" + uncleansedHTML;
	SaveParseToFile(datas);
	datas[2] = uncleansedHTML;
	extract = extract.replaceAll("<br />", "<br />. ");
	extract = extract.replaceAll("<b>", "<b> *");
	extract = extract.replaceAll("</b>", "</b> *");
	extract = extract.replaceAll("</span>", ".</span> ");
	extract = extract.replaceAll("</a>", "</a>. ");
	extract = extract.replaceAll("content=\"", "content=\". ");
	extract = extract.replaceAll("&quot;", "\"");
	extract = extract.replaceAll("&#39;", "'");
	//extract = extract.replaceAll("&#216;", "Ø");
	//extract = extract.replaceAll("&#229;", "å");
	//extract = extract.replaceAll("&#230;", "æ");
	//extract = extract.replaceAll("&#248;", "ø");
	extract = extract.replaceAll("&#9660;", "*");
	extract = extract.replaceAll("&middot;", " - ");
	extract = extract.replaceAll("htm", "htm. ");
	extract = extract.replaceAll("<p>", "<p>. ");
	extract = extract.replaceAll("\">", "\">. ");
	//extract = extract.replaceAll("/", "/. "); //fucker opp parsing
	extract = extract.replaceAll("html", "html. ");
	extract = extract.replaceAll("htm", "htm. ");
	extract = extract.replaceAll("&nbsp;", "htm. ");
	extract = extract.replaceAll("&hellip;", "...");
	datas[2] = "Contents after replace=" + extract;
	SaveParseToFile(datas);
	return extract;
    }

    public static String removeAllCSS(String uncleansedHTML, String URL) {
	String[] datas = new String[5];
	datas[0] = uncleansedHTML;
	datas[1] = URL;
	datas[2] = "getExtractedTextFromHTML Asking for extraction of " + URL;
	SaveParseToFile(datas);
	datas[2] = "Original contents=" + uncleansedHTML;
	SaveParseToFile(datas);
	datas[2] = uncleansedHTML;
	String extract = removeAllCSS(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	datas[2] = extract;
	SaveParseToFile(datas);
	return extract;
    }

    public static String removeAllScript(String uncleansedHTML, String URL) {
	String extract = uncleansedHTML;
	String[] datas = new String[5];
	datas[0] = uncleansedHTML;
	datas[1] = URL;
	datas[2] = "getExtractedTextFromHTML Asking for extraction of " + URL;
	SaveParseToFile(datas);
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " Original contents=" + extract;
	SaveParseToFile(datas);
	while (extract.contains("<noscript>")) {
	    String startTag = "<noscript>";
	    String endTag = "</noscript>";
	    extract = runDeletion(datas, startTag, endTag, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");
	}
	datas[2] = Thread.currentThread().getStackTrace()[1].getLineNumber() + " D=" + extract;
	SaveParseToFile(datas);
	return extract;
    }
}
