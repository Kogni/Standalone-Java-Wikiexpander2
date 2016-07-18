package Control;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import Objects.Object_Ord;

class Brain_Ordbok {

    private final Controller Class_Controller;
    private Object_Ord[] Ordliste;

    public Brain_Ordbok(Controller controller) {
	this.Class_Controller = controller;

	Ordliste = new Object_Ord[9999999];

    }

    public Object_Ord[] Get_Ordliste() {
	return Ordliste;
    }

    public void SaveWordList(Object_Ord[] ord) {
	this.Ordliste = ord;
    }

    public void GoogleOrdliste() {
	if (!Class_Controller.DoneLoadingPhrases)
	    return;
	if (Class_Controller.GoogledPhrases) {
	    return;
	}
	Class_Controller.GoogledPhrases = true;
	Class_Controller.PrintAction(this.getClass().toString() + " googler ordliste");
	Ordliste = Class_Controller.OrdBok.Ord;
	for (int x = 0; x < Ordliste.length; x++) {
	    if (Ordliste[x] != null) {
		if (!Ordliste[x].Ordet.equals("")) {
		    if (Ordliste[x].RelationValue > 0) {
			Class_Controller.SearchPhrase(Ordliste[x]);

			URL New = null;
			String tempurl = null;
			try {
			    tempurl = "http://www.google.no/search?q=" + Ordliste[x].Ordet;
			    tempurl = tempurl.replaceAll(" ", "+");
			    New = new URL(tempurl);
			    URLConnection myURLConnection = New.openConnection();
			    myURLConnection.connect();
			} catch (MalformedURLException e) { // new URL() failed
			} catch (Exception T) {
			    Class_Controller.CastErrors(T);
			}
			if (New != null) {
			    Class_Controller.SaveURL(New.toString(), "Ordbok", 100);
			} else {
			    System.err.println(this.getClass().getName());
			}

			New = null;
			try {
			    New = new URL(tempurl);
			    URLConnection myURLConnection = New.openConnection();
			    myURLConnection.connect();
			} catch (MalformedURLException e) { // new URL() failed
			} catch (Exception T) {
			    Class_Controller.CastErrors(T);
			}
			if (New != null) {
			    Class_Controller.PrintAction(this.getClass().toString() + " lagrer Google results for " + Ordliste[x].Ordet
				    + ": " + New.toString());
			    Class_Controller.SaveURL(New.toString(), "Ordbok", 999);
			} else {
			    System.err.println(this.getClass().getName());
			}

			New = null;
			try {
			    New = new URL("http://www.google.no/search?q=" + Ordliste[x].Ordet.replaceAll(" ", "-"));
			    URLConnection myURLConnection = New.openConnection();
			    myURLConnection.connect();
			} catch (MalformedURLException e) { // new URL() failed
			} catch (Exception T) {
			    Class_Controller.CastErrors(T);
			}
			if (New != null) {
			    Class_Controller.SaveURL(New.toString(), "Ordbok", 999);
			} else {
			    System.err.println(this.getClass().getName());
			}
		    }
		}
	    }
	}
	Class_Controller.PrintAction(this.getClass().toString() + " ferdig å google ordliste");
    }

}
