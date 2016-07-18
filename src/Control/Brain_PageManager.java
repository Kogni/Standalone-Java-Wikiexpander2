package Control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import Objects.Object_Database;
import Objects.Object_Ordbok_Old;
import Objects.Object_Webpage;
import Objects.Object_Website;

public class Brain_PageManager {

    private final Controller Class_Controller;
    private final Object_Website[] Websites;
    private Object_Webpage[] WebPages;
    private int WebsiteCounter = 0;

    boolean Soeker = false;
    private boolean Lagrer = false;

    public Brain_PageManager(Controller Class_Controller) {
	//System.out.println( this.getClass().toString() + "  started" );
	this.Class_Controller = Class_Controller;
	Websites = new Object_Website[9999999];

    }

    public boolean isAdded(Object_Webpage newpage) {

	if (!isUrlValid(newpage.Get_URL().toString())) {
	    return false;
	}
	Object_Webpage Rootsite = FigureRootsite(newpage, "InsertPage");
	CheckRootSite(Rootsite.Get_URL());
	for (int x = 0; x < Websites.length; x++) {
	    if (Websites[x] != null) {
		if (Websites[x].Get_URL().toString().equals(Rootsite.Get_URL().toString())) {
		    return Websites[x].isURLAdded(newpage.Get_URL());
		}
	    }
	}
	return false;
    }

    void InsertPage(Object_Webpage Newpage) {
	//System.out.println( this.getClass().toString() + " InsertPage started" );
	if (isAdded(Newpage)) {
	    return;
	}
	Object_Webpage Rootsite = FigureRootsite(Newpage, "InsertPage");
	CheckRootSite(Rootsite.Get_URL());
	for (int x = 0; x < Websites.length; x++) {
	    if (Websites[x] != null) {
		if (Websites[x].Get_URL().toString().equals(Rootsite.Get_URL().toString())) {
		    boolean Saved = Websites[x].InsertPage(Newpage);
		    if (Saved) {
			//Class_Controller.PrintAction( this.getClass().toString() + " Adding and saving new URL " + Newpage.Get_URL().toString() );
			SaveURL(Newpage);
		    }
		    return;
		}
	    }
	}
    }

    void InsertSite(Object_Website Newpage) {
	//System.out.println( this.getClass().toString() + " InsertSite started" );
	for (int x = 0; x < Websites.length; x++) {
	    if (Websites[x] == null) {
		Websites[x] = Newpage;
		WebsiteCounter++;

		return;
	    } else if (Websites[x].Get_URL().toString().equals(Newpage.Get_URL().toString())) {
		return;
	    }
	}
	System.err.println(System.currentTimeMillis() + " Kan ikke lagre website fordi array er full!");
    }

    public Object_Website FigureRootsite(Object_Webpage Webpage, String Kilde) {
	//System.out.println( this.getClass().toString() + " FigureRootsite started" );

	if (!isUrlValid(Webpage.Get_URL().toString())) {
	    return null;
	}
	try {
	    String Left = "http://";

	    URL Rootsite = Webpage.Get_URL();
	    if (Left.equals(Webpage.Get_URL().toString())) {
		return null;
	    }
	    String Right = Rootsite.toString().substring(Left.length(), Rootsite.toString().length());
	    int indx = 0;
	    indx = Math.max(Right.indexOf("/"), 0);
	    while (indx > 0) {

		Rootsite = new URL(Rootsite.toString().substring(0, (Left.length() + indx)));
		Right = Rootsite.toString().substring(Left.length(), Rootsite.toString().length());
		indx = Math.max(Right.indexOf("/"), 0);

	    }
	    indx = 0;
	    Rootsite = CheckRootSite(Rootsite);
	    return new Object_Website(Rootsite, Webpage.Get_LinkedRelationValue());
	} catch (Exception T) {
	    System.err.println("" + Webpage.Get_URL().toString() + " fra " + Kilde);
	    this.Class_Controller.CastErrors(T);
	}

	return null;
    }

    private URL CheckRootSite(URL RootsiteA) {
	//System.out.println( this.getClass().toString() + " CheckRootSite started" );
	try {
	    URL RootsiteB = RootsiteA;
	    String Left = "http://";
	    String Right = RootsiteB.toString().substring(Left.length(), RootsiteB.toString().length());
	    int indx = 0;
	    indx = Math.max(Right.indexOf("/"), 0);
	    while (indx > 0) {

		RootsiteB = new URL(RootsiteB.toString().substring(0, (Left.length() + indx)));
		Right = RootsiteB.toString().substring(Left.length(), RootsiteB.toString().length());
		indx = Math.max(Right.indexOf("/"), 0);

	    }
	    if (!(RootsiteA.toString().equals(RootsiteB.toString()))) {
		System.out.println("" + RootsiteA + " != " + RootsiteB);
	    }

	    return RootsiteB;
	} catch (Exception T) {

	    this.Class_Controller.CastErrors(T);
	}
	return RootsiteA;
    }

    public Object_Webpage[] GetLinksToSave() {
	//System.out.println( this.getClass().toString() + " GetLinksToSave started" );
	if (Lagrer) {
	    return null;
	}
	Lagrer = true;

	Object_Database SortedDatabase = new Object_Database();

	for (int X = (Websites.length - 1); X >= 0; X--) {
	    if (Websites[X] != null) {

		Object_Webpage[] Sider = Websites[X].Get_SiteMap();
		for (int Y = 0; Y < Sider.length; Y++) {
		    if (Sider[Y] != null) {
			if (Sider[Y].Get_SelfRelationValue() > Class_Controller.InterestBorder) {
			    SortedDatabase.InsertLink(Sider[Y].Get_URL().toString(), Sider[Y].Get_LinkedRelationValue(),
				    Sider[Y].Get_SelfRelationValue());

			}
		    }
		}

	    }
	}

	Lagrer = false;
	return SortedDatabase.GetDatabase();
    }

    public void InsertLink(String line, int RelatedWordsFound) {
	//System.out.println( this.getClass().toString() + " InsertLink started" );
	RelatedWordsFound = RelatedWordsFound + Adressvalue(line);
	if (!isUrlValid(line)) {
	    return;
	}
	try {
	    URL Adresse = new URL(line);
	    Object_Webpage New = new Object_Webpage(Adresse, RelatedWordsFound, 0);
	    Object_Website Rootsite = FigureRootsite(New, "controller.SaveURL");
	    if (Rootsite == null) {
		return;
	    }
	    InsertSite(Rootsite);

	    InsertPage(New);

	} catch (MalformedURLException T) {
	} catch (Exception T) {
	    this.Class_Controller.CastErrors(T);
	}
	//System.out.println( this.getClass().toString() + " InsertLink finished" );
    }

    private int Adressvalue(String line) {
	//System.out.println( this.getClass().toString() + " Adressvalue started" );
	int Value = 0;
	Object_Ordbok_Old Ordbok = this.Class_Controller.OrdBok;
	for (int X = 0; X < Ordbok.Ord.length; X++) {
	    if (Ordbok.Ord[X] != null) {
		if (Ordbok.Ord[X].RelationValue != 0) {
		    int Bonus = 0;
		    int Occurence1 = countOccurrences(line, Ordbok.Ord[X].Ordet.toLowerCase());
		    int Occurence2 = countOccurrences(line, " " + Ordbok.Ord[X].Ordet.toLowerCase() + " ");
		    int Occurence3 = countOccurrences(line, Ordbok.Ord[X].Ordet.toLowerCase().replaceAll(" ", ""));
		    int Occurence4 = countOccurrences(line, Ordbok.Ord[X].Ordet.toLowerCase().replaceAll(" ", "-"));

		    Bonus = Bonus + (Math.min(Occurence1, 3) * Ordbok.Ord[X].RelationValue);
		    Bonus = Bonus + (Math.min(Occurence2, 3) * Ordbok.Ord[X].RelationValue * 2);
		    Bonus = Bonus + (Math.min(Occurence3, 3) * Ordbok.Ord[X].RelationValue);
		    Bonus = Bonus + (Math.min(Occurence4, 3) * Ordbok.Ord[X].RelationValue);
		    Value = Value + Bonus;
		}
	    } else {
		X = Ordbok.Ord.length;
	    }
	}

	return Value;
    }

    int countOccurrences(String haystack, String needle) {
	//System.out.println( this.getClass().toString() + " countOccurrences started" );
	//System.out.println( "countOccurrences "+needle );
	int count = 0;
	int lastIndex = haystack.indexOf(needle, 0);
	//System.out.println( needle +" "+haystack.indexOf( needle, lastIndex ) );

	while (lastIndex != -1) {
	    //System.out.println( "lastIndex "+lastIndex );
	    haystack = haystack.substring((lastIndex + needle.length()), haystack.length());
	    lastIndex = haystack.indexOf(needle, 0);

	    if (lastIndex != -1) {
		count++;
	    }
	}
	return count;
    }

    public void OrderNewSearch() {

	if (Soeker) {

	    return;
	}
	if (!this.Class_Controller.Doneloading) {
	    return;
	}

	Soeker = true;
	for (int x = 0; x < Websites.length; x++) {
	    if (Websites[x] != null) {

		Object_Webpage Temp = Websites[x].Get_NextPageSearch();
		if (Temp != null) {
		    //Class_Controller.PrintAction( this.getClass().toString() + " OrderNewSearch checks page " + Temp.Get_URL().toString() );
		    //Class_Controller.PrintAction( this.getClass().toString() + " OrderNewSearch Get_LinkedRelationValue=" + Temp.Get_LinkedRelationValue() );
		    if (Temp.Get_LinkedRelationValue() > 0) {

			this.Class_Controller.SearchURL(Temp);
			Soeker = false;

			return;
		    }
		}

	    }
	}

	Soeker = false;
	Class_Controller.PrintAction(this.getClass().toString() + " OrderNewSearch found no work.");
	System.exit(0);
    }

    public boolean GetSaved(URL rootsite) {

	for (int x = 0; x < WebPages.length; x++) {
	    if (WebPages[x] != null) {
		if (WebPages[x].Get_URL().toString().equals(rootsite.toString())) {
		    return WebPages[x].GetSaved();
		}
	    } else {
		return false;
	    }
	}
	return false;
    }

    public void SetSaved(URL rootsite) {

	for (int x = 0; x < WebPages.length; x++) {
	    if (WebPages[x] != null) {
		if (WebPages[x].Get_URL().toString().equals(rootsite.toString())) {
		    WebPages[x].SetSaved();
		    return;
		}
	    } else {
		return;
	    }
	}
    }

    void SaveURL(Object_Webpage newpage) {
	//Class_Controller.PrintAction( this.getClass().toString() + " SavePhrase " + phrase );

	if (!Class_Controller.Doneloading) {
	    return;
	}

	try {
	    File filen = new File("URLs.txt");
	    if (!filen.exists()) {
		filen.createNewFile();
	    }

	    PrintStream utfil;
	    FileOutputStream appendFilen = new FileOutputStream("URLs.txt", true);
	    utfil = new PrintStream(appendFilen);

	    utfil.println(newpage.Get_URL().toString());
	    utfil.close();
	    //Class_Controller.PrintAction( this.getClass().toString() + " Saved URL " + newpage.Get_URL().toString() );
	} catch (IOException T) {
	    Class_Controller.PrintAction(this.getClass().toString() + " failed to save phrase " + newpage + " " + T.getClass());
	    Class_Controller.CastErrors(T);
	} catch (Exception T) {
	    Class_Controller.PrintAction(this.getClass().toString() + " failed to save phrase " + newpage + " " + T.getClass());
	    Class_Controller.CastErrors(T);
	}
    }

    public boolean isUrlValid(String temp) {
	if (temp == null) {
	    return false;
	}
	if (temp.equals("")) {
	    return false;
	}
	if (temp.equals("http:/")) {
	    return false;
	}
	if (temp.equals("http:")) {
	    return false;
	}

	if (temp.contains("http://webcache.googleusercontent.com/")) {
	    return false;
	}
	if (temp.contains("http://www.youtube.com/")) {
	    return false;
	}
	if (temp.contains("http://maps.google")) {
	    return false;
	}
	if (temp.contains("http://maps.google")) {
	    return false;
	}
	return true;
    }

}
