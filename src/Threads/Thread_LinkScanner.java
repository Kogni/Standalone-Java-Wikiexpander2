
package Threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.util.regex.PatternSyntaxException;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import Control.Controller;
import Control.TextHandler;
import Objects.Object_Ord;
import Objects.Object_Webpage;
public class Thread_LinkScanner extends Thread_Searcher {

    private final Controller Class_Controller;
    private final Object_Webpage LinkToCheck;
    // --Commented out by Inspection (21.09.2014 15:21):long ThreadstartTime = System.currentTimeMillis();
    public int Status;
    // --Commented out by Inspection (21.09.2014 15:21):URL url;
    // --Commented out by Inspection (21.09.2014 15:21):String Filnavn = "URLs.txt";
    // --Commented out by Inspection (21.09.2014 15:21):String Title = "";
    // --Commented out by Inspection (21.09.2014 15:21):Thread_TimeKeeper Timer;
    private final StringBuffer DataImported = new StringBuffer();
    //int SaveValue = 0;
    private int RelatedWordsFound = 0;
    private final HtmlCleaner parser = new HtmlCleaner();

    public Thread_LinkScanner(Controller Class_Controller, Object_Webpage LinkToCheck) {
	this.Class_Controller = Class_Controller;
	this.LinkToCheck = LinkToCheck;
	Status = 0;
    }

    @Override
    public void run() {
	Scan();
    }

    private void Scan() {
	Status = 1;
	try {
	    //Class_Controller.PrintAction(this.getClass().toString() + " opening URL " + LinkToCheck.Get_URL().toString());
	    Status = 12;
	    URL url = LinkToCheck.Get_URL();
	    Thread_TimeKeeper Timer = new Thread_TimeKeeper(this);
	    Timer.start();
	    Status = 13;
	    URLConnection conn = url.openConnection();
	    conn.setRequestProperty("User-Agent",
		    "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");
	    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String uncleansedHTML;
	    Status = 14;
	    //Class_Controller.PrintAction( this.getClass().toString() + " getting contents on " + LinkToCheck.Get_URL().toString() );
	    String cleansedText;
	    while ((uncleansedHTML = in.readLine()) != null) {
		//TagNode cleansed = parser.clean(uncleansedHTML);
		//cleansedText = (String) cleansed.getText();
		/*
				cleansedText = uncleansedHTML;
				if (uncleansedHTML.contains("<title>")) {
				    finnTittel(cleansedText);
				}
				cleansedText = TextHandler.getExtractedTextFromHTML(uncleansedHTML, "Scan");
				Class_Controller.PrintAction(this.getClass().toString() + " cleansedText=" + cleansedText);
				System.out.println(this.getClass().toString() + " cleansedText=" + cleansedText);
				while (cleansedText.contains("  ")) {//fjerner space i starten av setninger, for aa unngaa daarlige treff som ikke er setninger
				    cleansedText = cleansedText.replaceAll("  ", " ");
				}
				if ((!cleansedText.equals("") && (!cleansedText.equals(" ")))) {
				    Class_Controller.PrintAction(this.getClass().toString() + " AfterCleanHTML: " + cleansedText);
				}
				DataImported.append(uncleansedHTML); //this CANNOT be cleansed for html!
				if (cleansedText.length() > 3) {
				    if (LinkToCheck.Get_URL().toString().contains(Class_Controller.startSite)) {
					FillWordListFromPage(uncleansedHTML); //maa kjoeres med UNCLEANSED HTML
				    }
				    FindSaveWordlist(cleansedText); //kjoeres med cleansed tekst. Finner ut antall relaterte ord funnet
				}
				*/
		//cleansedText = TextHandler.getExtractedTextFromHTML(uncleansedHTML, LinkToCheck.Get_URL().toString());
		//Class_Controller.PrintAction(this.getClass().toString() + " hentet en linje: " + uncleansedHTML.toString());
		DataImported.append(uncleansedHTML); //this CANNOT be cleansed for html!
	    }
	    Class_Controller.PrintAction(this.getClass().toString() + " Ferdig å hente HTML.");
	    cleansedText = DataImported.toString();
	    uncleansedHTML = DataImported.toString();
	    if (uncleansedHTML.contains("<title>")) {
		finnTittel(uncleansedHTML);
	    }
	    //Class_Controller.PrintAction(this.getClass().toString() + " done getting contents from " + url.toString());
	    //tekst mangler punktumer og inneholder scriptsprÃ¥k
	    //cleansedText = TextHandler.getExtractedTextFromHTML(uncleansedHTML, LinkToCheck.Get_URL().toString()); //denne kan ikke funke sammen replaceFormatting, siden begge to sletter deler av html-kode
	    /*
	    	    cleansedText = TextHandler.removeAllCSS(cleansedText, LinkToCheck.Get_URL().toString());
	    	    datas = new String[5];
	    	    datas[2] = this.getClass().toString() + " after removeAllCSS " + cleansedText;
	    	    datas[1] = LinkToCheck.Get_URL().toString();
	    	    TextHandler.SaveParseToFile(datas);

	    	    cleansedText = TextHandler.removeAllScript(cleansedText, LinkToCheck.Get_URL().toString());
	    	    //uten alt av scripter vil mye viktig tekst mangle
	    	    datas = new String[5];
	    	    datas[2] = this.getClass().toString() + " after removeAllScript " + cleansedText;
	    	    datas[1] = LinkToCheck.Get_URL().toString();
	    	    TextHandler.SaveParseToFile(datas);
	    */
	    cleansedText = TextHandler.replaceFormatting(cleansedText, LinkToCheck.Get_URL().toString());
	    String[] datas = new String[5];
	    datas[2] = this.getClass().toString() + " after replaceFormatting " + cleansedText;
	    datas[1] = LinkToCheck.Get_URL().toString();
	    TextHandler.SaveParseToFile(datas);
	    TagNode cleansed = parser.clean(cleansedText);
	    cleansedText = cleansed.getText().toString();
	    datas[2] = this.getClass().toString() + " after html-parse " + cleansedText;
	    TextHandler.SaveParseToFile(datas);
	    //cleansedText = TextHandler.getExtractedTextFromHTML(cleansedText, LinkToCheck.Get_URL().toString());
	    Class_Controller
		    .PrintAction(this.getClass().toString() + " done getting contents from " + url.toString() + ": " + cleansedText);
	    while (cleansedText.contains("  ")) {//fjerner space i starten av setninger, for aa unngaa daarlige treff som ikke er setninger
		cleansedText = cleansedText.replaceAll("  ", " ");
	    }
	    if ((!cleansedText.equals("") && (!cleansedText.equals(" ")))) {
		//Class_Controller.PrintAction(this.getClass().toString() + " AfterCleanHTML: " + cleansedText);
	    }
	    datas[2] = this.getClass().toString() + " Received " + cleansedText;
	    TextHandler.SaveParseToFile(datas);
	    //Class_Controller.PrintAction(this.getClass().toString() + " cleansedText.length()=" + cleansedText.length());
	    //Class_Controller.PrintAction(this.getClass().toString() + " Class_Controller.startSite=" + Class_Controller.startSite);
	    /*Class_Controller.PrintAction(this.getClass().toString() + " LinkToCheck.Get_URL().toString()="
	        + LinkToCheck.Get_URL().toString());*/
	    if (cleansedText.length() > 3) {
		//Class_Controller.PrintAction(this.getClass().toString() + " cleansedText=" + cleansedText);
		if (LinkToCheck.Get_URL().toString().contains(Class_Controller.startSite)) {
		    FillWordListFromPage(uncleansedHTML); //her skal alle fraser bli plukket opp for aa puttes i ordboka. maa kjoeres med UNCLEANSED HTML
		}
		FindSaveWordlist(cleansedText); //check if the line contains any saved phrases from ordbok. newLine is already CLEANSED for HTML
	    }
	    //Class_Controller.PrintAction( this.getClass().toString() + " done getting contents." );
	    Status = 15;
	    //Class_Controller.PrintAction( this.getClass().toString() + " scanning text on " + LinkToCheck.Get_URL().toString() );
	    ScanStrings(DataImported);//skal sjekke om url er tilstrekkelig relatert
	    Status = 2;
	    if (LinkToCheck.Get_URL().toString().contains(Class_Controller.startSite)) {
		this.Class_Controller.GoogleOrdliste(); //google ordlista etter at ordlista blir fylt i linja over
	    }
	} catch (FileNotFoundException T) {
	    /*Class_Controller.PrintAction(this.getClass().toString() + " getting contents failed with " + T.getClass() + " "
	        + T.getMessage());*/
	    if (T.getMessage().contains("http:")) {
		LinkToCheck.Set_SelfRelationValue(LinkToCheck.Get_SelfRelationValue() - 10, "FileNotFoundException");
	    } else {
		Class_Controller.CastErrors(T);
	    }
	} catch (UnknownHostException T) {
	    LinkToCheck.Set_SelfRelationValue(LinkToCheck.Get_SelfRelationValue() - 10, "UnknownHostException");
	    //Class_Controller.PrintAction(this.getClass().toString() + " getting contents failed with " + T.getClass());
	} catch (ConnectException T) {
	    //Class_Controller.PrintAction(this.getClass().toString() + " getting contents failed with " + T.getClass());
	    LinkToCheck.Set_SelfRelationValue(LinkToCheck.Get_SelfRelationValue() - 10, "ConnectException");
	} catch (IOException T) {
	    //Class_Controller.PrintAction(this.getClass().toString() + " getting contents failed with " + T.getClass());
	    LinkToCheck.Set_SelfRelationValue(LinkToCheck.Get_LinkedRelationValue() - 10, "IOException");
	    if (LinkToCheck.Get_LinkedRelationValue() < this.Class_Controller.InterestBorder) {
		LinkToCheck.Set_LinkedRelationValue(Class_Controller.InterestBorder);
	    }
	    //this.Class_Controller.CastErrors(T);
	} catch (PatternSyntaxException T) {
	    Class_Controller.CastErrors(T);
	} catch (IllegalArgumentException T) {
	    Class_Controller.PrintAction(this.getClass().toString() + " getting contents failed with " + T.getClass());
	    LinkToCheck.Set_SelfRelationValue(LinkToCheck.Get_SelfRelationValue() - 10, "IllegalArgumentException");
	} catch (Exception T) {
	    Class_Controller.PrintAction(this.getClass().toString() + " getting contents failed with " + T.getClass());
	    this.Class_Controller.CastErrors(T);
	    LinkToCheck.Set_SelfRelationValue(LinkToCheck.Get_SelfRelationValue() - 10, "Exception");
	}
	Status = 3;
	//Class_Controller.PrintAction("Done scanning " + LinkToCheck.Get_URL().toString());
    }

    private String finnTittel(String line) {
	String tittel = null;
	try {
	    tittel = line.substring(line.indexOf("<title>") + "<title>".length(), line.indexOf("</title>"));
	} catch (Exception T) {
	    Class_Controller.PrintAction(this.getClass().toString() + " error in finnTittel: " + T.getClass());
	    //this.Class_Controller.CastErrors(T);
	}
	return tittel;
    }

    private void ScanStrings(StringBuffer buffer) {//skal sjekke om url er tilstrekkelig relatert
	//Class_Controller.PrintAction( this.getClass().toString() + " Scanning text on " + LinkToCheck.Get_URL().toString() + " " + LinkToCheck.Get_URL().toString().contains( "hjernebark.atlassian" ) );
	String Buffer = buffer.toString().toLowerCase();
	Status = 151;
	Status = 152;
	try {
	    //Class_Controller.PrintAction( this.getClass().toString() + " Tries to estimate relation value for " + LinkToCheck.Get_URL().toString() );
	    Status = 1521;
	    //LinkToCheck.Set_SelfRelationValue(RelatedWordsFound * 100, "ScanStrings");
	    Status = 1522;
	    Thread thread = new Thread_URLFinder(Class_Controller, Buffer, this, LinkToCheck.Get_URL().toString());
	    thread.start();
	    Status = 1523;
	    if ((RelatedWordsFound * 100) > Class_Controller.InterestBorder) {
		//Class_Controller.PrintAction( this.getClass().toString() + " Link is relevant: " + LinkToCheck.Get_URL().toString() + " " + RelatedWordsFound );
		Status = 1524;
		Status = 1525;
		if ((LinkToCheck.Get_SelfRelationValue() > 0)) {
		    SaveToFile();
		    //Class_Controller.PrintAction(this.getClass().toString() + " Related sentences found: " + RelatedWordsFound);
		} else {
		    //Class_Controller.PrintAction( this.getClass().toString() + " Nothing interesting found there." );
		}
		Status = 1526;
	    } else {
		//Class_Controller.PrintAction( this.getClass().toString() + " No related words found here" );
	    }
	    Status = 153;
	} catch (Exception T) {
	    Class_Controller.PrintAction(this.getClass().toString() + " Failed to estimate relation value on "
		    + LinkToCheck.Get_URL().toString());
	    this.Class_Controller.CastErrors(T);
	}
	Status = 154;
    }

    private void FindSaveWordlist(String newLine) { //check if the line contains any saved phrases from ordbok. newLine is already CLEANSED for HTML
	Object_Ord[] TempOrdbok = this.Class_Controller.GetOrdliste();
	Status = 15231;
	for (int X = 0; X < TempOrdbok.length; X++) {
	    Status = 15232;
	    if ((TempOrdbok[X] != null) && (!TempOrdbok[X].Ordet.equals(""))) {
		Status = 15233;
		/*Class_Controller.PrintAction(this.getClass().toString() + " FindSaveWordlist -" + TempOrdbok[X].Ordet + "- "

		                     + TempOrdbok[X].RelationValue);*/
		if ((TempOrdbok[X].RelationValue != 0) && (newLine.contains(TempOrdbok[X].Ordet))) {
		    Status = 15234;
		    Status = 15235;
		    CutTextToSentences(newLine, TempOrdbok[X].Ordet);
		    Status = 15236;
		}
	    } else {
		X = TempOrdbok.length;
	    }
	}
	Status = 15237;
    }

    private void CutTextToSentences(String newLine, String Ordet) {
	//Class_Controller.PrintAction(this.getClass().toString() + " FindSaveSentence " + newLine);
	String[] datas = new String[5];
	datas[1] = LinkToCheck.Get_URL().toString();
	datas[2] = this.getClass().toString() + " CutTextToSentences før replace " + newLine;
	TextHandler.SaveParseToFile(datas);
	Status = 152351;
	/*newLine = newLine.replaceAll("%", " ");
	newLine = newLine.replaceAll("\n", " ");
	newLine = newLine.replaceAll("//", ". ");
	//newLine = newLine.replaceAll( "|", ". " );
	newLine = newLine.replaceAll(":http", ". ");
	newLine = replaceAll(newLine, "Ã¥", "ï¿½");
	newLine = replaceAll(newLine, "Ã¸", "ï¿½");
	newLine = replaceAll(newLine, "Ã¦", "ï¿½");
	newLine = replaceAll(newLine, "&amp;", "&");*/
	newLine = newLine.replaceAll("&quot;", "\"");
	newLine = newLine.replaceAll("&#39;", "'");
	//newLine = newLine.replaceAll("&#197;", "Å");
	//newLine = newLine.replaceAll("&#198;", "Æ");
	//newLine = newLine.replaceAll("&#216;", "Ø");
	//newLine = newLine.replaceAll("&#229;", "å");
	//newLine = newLine.replaceAll("&#230;", "æ");
	//newLine = newLine.replaceAll("&#248;", "ø");
	newLine = newLine.replaceAll("&#8217;;", "'");
	//newLine = newLine.replaceAll("Ã¦", "æ");
	newLine = newLine.replaceAll("&middot;", " - ");
	newLine = newLine.replaceAll("htm", "htm. ");
	newLine = newLine.replaceAll("&hellip;", "...");
	newLine = newLine.replaceAll("&nbsp;", " ");
	datas[2] = this.getClass().toString() + " CutTextToSentences etter replace " + newLine;
	TextHandler.SaveParseToFile(datas);
	while (TextHandler.InneholderSeparertOrd(Ordet, newLine) > -1) {
	    Status = 152352;
	    int punktum_start = newLine.indexOf(".");
	    if (punktum_start < 0) {//finner ikke flere punktumer
		CheckSentenceForWord(newLine, Ordet);
		return;
	    }
	    String sentence = newLine.substring(0, punktum_start + 1);
	    if ((sentence.length() > 2) && (sentence.substring(0, 2).equals("no"))) { //sjekker om setningen stopper midt i en ULR
		sentence = newLine.substring(0, punktum_start + 3); //forsÃ¸ker Ã¥ redde URL
	    }
	    if ((sentence.length() > 5)) {
		Status = 152353;
		CheckSentenceForWord(sentence, Ordet);
		Status = 152354;
	    }
	    newLine = newLine.substring(sentence.length());
	    Status = 152355;
	}
	Status = 152356;
    }

    private void CheckSentenceForWord(String sentence, String Ordet) { //finner ut om en setning inneholder et nï¿½kkelord, og ber isaafall om lagring av setningen
	//Class_Controller.PrintAction(this.getClass().toString() + " FindSaveWord " + sentence);
	//æøå funker ikke riktig
	String[] datas = new String[5];
	datas[1] = LinkToCheck.Get_URL().toString();
	datas[2] = this.getClass().toString() + " CheckSentenceForWord " + sentence;
	//TextHandler.SaveParseToFile(datas);
	Status = 1523531;
	if (!(sentence.length() > 5) || (!sentence.contains(Ordet)) || (sentence.toLowerCase().contains("google"))
		|| (!(sentence.length() > (Ordet.length() + 2)))) {
	    return;
	}
	if (TextHandler.InneholderSeparertOrd(Ordet, sentence) > -1) {
	    Status = 1523532;
	    /*while (sentence.substring(0, 1).equals(" ")) {//fjerner space i starten av setninger, for aa unngaa daarlige treff som ikke er setninger

	    sentence = sentence.substring(1);

	    }*/
	    int sentenceValue = FindSaveValue(sentence);
	    //Class_Controller.PrintAction(this.getClass().toString() + " FindSaveWord sentenceValue=" + sentenceValue + " ");
	    if ((sentence.length() > (Ordet.length() + 2))//filtrerer bort rester av kode og ting som ikke er setninger
		    && (TextHandler.InneholderSeparertOrd(Ordet, sentence) > -1) && (sentence.contains(" "))
		    && (sentenceValue > 0)
		    && (!sentence.contains(";var ")) && (!sentence.contains("|")) && (!sentence.contains("{")) && (!sentence.contains("}"))) {
		RelatedWordsFound++;
		sentenceValue = (int) (Math.round((double) (sentenceValue / 200)));
		//Class_Controller.PrintAction(this.getClass().toString() + " FindSaveWord sentenceValue=" + sentenceValue + " ");
		if (sentenceValue > 0) {
		    sentenceValue = (int) (Math.round((double) (sentenceValue / 5)));
		    RelationValue = RelationValue + sentenceValue;
		    LinkToCheck.Set_SelfRelationValue(LinkToCheck.Get_SelfRelationValue() + sentenceValue, "FindSaveWord");
		    /*Class_Controller
		        .PrintAction(this.getClass().toString() + " FindSaveValue sentenceValue=" + sentenceValue + " setning="
		    	    + sentence + " URL=" + LinkToCheck.Get_URL().toString() + " total value="
		    	    + LinkToCheck.Get_SelfRelationValue());*/
		    datas = new String[5];
		    datas[1] = LinkToCheck.Get_URL().toString();
		    datas[2] = this.getClass().toString() + " Før saving " + sentence;
		    //TextHandler.SaveParseToFile(datas);
		    this.Class_Controller.SaveSentence(sentence, Ordet, sentenceValue);
		}
		//Class_Controller.PrintAction( "Found relevance: " + Ordet );
	    }
	}
	Status = 1523533;
    }

    private void FillWordListFromPage(String newLine) { //her skal alle fraser bli plukket opp for aa puttes i ordboka
	FindWordsFromLabelsList(newLine);
	FindWordsFromPopularLabelst(newLine);
    }

    private void FindWordsFromLabelsList(String newLine) {
	//Class_Controller.PrintAction(this.getClass().toString() + " FindWordsFromLabelsList -" + newLine + "-");
	String contentsleft = newLine;
	while (contentsleft.contains("<li><a class=\"label\" rel=\"nofollow\" href=\"")) {
	    String pre = "<li><a class=\"label\" rel=\"nofollow\" href=\"";
	    String post = "</a></li>";
	    int preStart = contentsleft.indexOf(pre);
	    contentsleft = contentsleft.substring(preStart + pre.length());
	    int phraseStart = contentsleft.indexOf("\">" + 2);//der frasen egentlig begynner
	    if (phraseStart == -1) {
		return;
	    }
	    int phraseEnd = contentsleft.indexOf(post);
	    if (phraseEnd == -1) {
		return;
	    }
	    contentsleft = contentsleft.substring(phraseEnd);
	    String phrase = contentsleft.substring(phraseStart, phraseEnd);
	    //Class_Controller.PrintAction( "Plukket ut phrase: " + phrase );
	    if (phrase.length() > 20) { //this is not a word
	    } else if (phrase.length() < 2) {
	    } else {
		this.Class_Controller.SaveWord(phrase);
	    }
	}
    }

    private void FindWordsFromPopularLabelst(String newLine) {
	/*Class_Controller.PrintAction(this.getClass().toString() + " FindWordsFromPopularLabelst -" + newLine + "- "
		+ (newLine.contains("<li class=\"popularlabel\"><a class=\"label\" rel=\"nofollow\" href=\"/wiki/label/")));*/
	String contentsleft = newLine;
	while (contentsleft.contains("<li class=\"popularlabel\"><a class=\"label\" rel=\"nofollow\" href=\"/wiki/label/")) {
	    String pre = "<li class=\"popularlabel\"><a class=\"label\" rel=\"nofollow\" href=\"/wiki/label/";
	    String post = "</a></li>";
	    int preStart = contentsleft.indexOf(pre);
	    contentsleft = contentsleft.substring(preStart + pre.length());
	    int phraseStart = contentsleft.indexOf("\">");//der frasen egentlig begynner
	    //Class_Controller.PrintAction(this.getClass().toString() + " FindWordsFromPopularLabelst - phraseStart=" + phraseStart);
	    if (phraseStart == -1) {
		return;
	    }
	    phraseStart = phraseStart + 2;
	    /*Class_Controller.PrintAction(this.getClass().toString() + " FindWordsFromPopularLabelst - phraseStart="
	        + contentsleft.substring(phraseStart, (phraseStart + 20)));*/
	    int phraseEnd = contentsleft.indexOf(post);
	    //Class_Controller.PrintAction(this.getClass().toString() + " FindWordsFromPopularLabelst - phraseEnd=" + phraseEnd);
	    if (phraseEnd == -1) {
		return;
	    }
	    String phrase = contentsleft.substring(phraseStart, phraseEnd);
	    contentsleft = contentsleft.substring(phraseEnd);
	    //Class_Controller.PrintAction("Plukket ut phrase: " + phrase);
	    if (phrase.length() > 20) { //this is not a word
	    } else if (phrase.length() < 2) {
	    } else {
		this.Class_Controller.SaveWord(phrase);
	    }
	}
    }

    // --Commented out by Inspection START (21.09.2014 15:21):
    //    public int countOccurrences(String haystack, String needle) {
    //	int count = 0;
    //	int lastIndex = haystack.indexOf(needle, 0);
    //	while (lastIndex != -1) {
    //	    haystack = haystack.substring((lastIndex + needle.length()), haystack.length());
    //	    lastIndex = haystack.indexOf(needle, 0);
    //	    if (lastIndex != -1) {
    //		count++;
    //	    }
    //	}
    //	return count;
    //    }
    // --Commented out by Inspection STOP (21.09.2014 15:21)
    // --Commented out by Inspection START (21.09.2014 15:21):
    //    private String CleanHTML(String source) {
    //	if (source.contains("no\",\"jam\":0,\"jsonp\":true,\"msgs\"")) {
    //	    return "";
    //	}
    //	source = HTML.html2text(source);
    //	if (source.length() < 6) {
    //	    source = "";
    //	}
    //	return source;
    //    }
    // --Commented out by Inspection STOP (21.09.2014 15:21)
    private int FindSaveValue(String setning) {//mï¿½ler verdien per setning
	setning = setning.toLowerCase();
	//Class_Controller.PrintAction(this.getClass().toString() + " FindSaveValue " + Buffer);
	//System.out.println( this.getClass().toString() + " FindSaveValue started" );
	int SaveValue = 0;
	try {
	    Object_Ord[] TempOrdbok = this.Class_Controller.GetOrdliste();
	    for (int X = 0; X < TempOrdbok.length; X++) {
		if ((TempOrdbok[X] != null) && (!TempOrdbok[X].Ordet.equals(""))) {
		    /*Class_Controller.PrintAction(this.getClass().toString() + " FindSaveValue TempOrdbok" + X + "="

		                + TempOrdbok[X].Ordet.toString() + " SaveValue=" + TempOrdbok[X].SaveValue);*/
		    if ((TempOrdbok[X].SaveValue != 0) && (setning.contains(TempOrdbok[X].Ordet))) {
			int Bonus = 0;
			int Occurence1 = (setning.contains(TempOrdbok[X].Ordet.toLowerCase())) ? 1 : 0;
			int Occurence2 = (setning.contains(TempOrdbok[X].Ordet.toLowerCase() + " ")) ? 1 : 0;
			int Occurence3 = (setning.contains(TempOrdbok[X].Ordet.toLowerCase().replaceAll(" ", ""))) ? 1 : 0;
			int Occurence4 = (setning.contains(TempOrdbok[X].Ordet.toLowerCase().replaceAll(" ", "-"))) ? 1 : 0;
			/*Class_Controller.PrintAction(this.getClass().toString() + " FindSaveValue occurrences="

			             + (Occurence1 + Occurence2 + Occurence3 + Occurence4));*/
			Bonus = Bonus + (Math.min(Occurence1, 5) * TempOrdbok[X].SaveValue);
			Bonus = Bonus + (Math.min(Occurence2, 5) * TempOrdbok[X].SaveValue * 2);
			Bonus = Bonus + (Math.min(Occurence3, 5) * TempOrdbok[X].SaveValue);
			Bonus = Bonus + (Math.min(Occurence4, 5) * TempOrdbok[X].SaveValue);
			SaveValue = SaveValue + Bonus;
			/*Class_Controller.PrintAction(this.getClass().toString() + " FindSaveValue " + TempOrdbok[X].Ordet + " SaveValue="
				+ SaveValue + " ");*/
		    }
		} else {
		    X = TempOrdbok.length;
		}
	    }
	} catch (Exception T) {
	    this.Class_Controller.CastErrors(T);
	}
	return SaveValue;
    }

    private String replaceAll(String source, String toReplace, String replacement) {
	int idx = source.lastIndexOf(toReplace);
	if (idx != -1) {
	    StringBuffer ret = new StringBuffer(source);
	    ret.replace(idx, idx + toReplace.length(), replacement);
	    while ((idx = source.lastIndexOf(toReplace, idx - 1)) != -1) {
		ret.replace(idx, idx + toReplace.length(), replacement);
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
	    filen = new File(Filnavn);
	    if (!filen.exists()) {
		filen.createNewFile();
	    }
	    PrintStream utfil;
	    FileOutputStream appendFilen = new FileOutputStream(Filnavn, true);
	    utfil = new PrintStream(appendFilen);
	    //Class_Controller.PrintAction( "--> Looks interesting: " + LinkToCheck.Get_URL().toString() );
	    /*Class_Controller.PrintAction(this.getClass().toString() + " ---> Seems related: " + LinkToCheck.Get_URL().toString()
	        + " Score: " + LinkToCheck.Get_SelfRelationValue());*/
	    utfil.println("" + LinkToCheck.Get_URL().toString() + "");
	    utfil.close();
	} catch (IOException T) {
	    if (T.getMessage().equals("Access is denied")) {
		SaveToFile();
	    } else {
		this.Class_Controller.CastErrors(T);
	    }
	} catch (Exception T) {
	    this.Class_Controller.CastErrors(T);
	}
    }
}
