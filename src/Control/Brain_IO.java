
package Control;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;
import Objects.Object_Webpage;
class Brain_IO {

    private final Controller Class_Controller;
    private String Filnavn = "URLs.txt";
    private File filen;
    String Content;
    FileInputStream fstream;
    DataInputStream in;
    String filenameFrom;
    String filenameTo;
    int loadedURLs = 0;

    public Brain_IO(Controller Class_Controller) {
	this.Class_Controller = Class_Controller;
    }

    public void SavePhrase(String Phrase, String Sentence, int value) {
	//Class_Controller.PrintAction("-> " + this.getClass().toString() + " SavePhrase " + Sentence + " " + value);
	//value = (int) (Math.round((double) (value / 1000)));
	Filnavn = "Results\\" + value + ".txt";
	File ProductInfo = new File(Filnavn);
	while (Sentence.substring(0, 1).contains(" ")) {
	    Sentence = Sentence.substring(1);
	}
	try {
	    filen = new File(Filnavn);
	    if (!ProductInfo.getParentFile().exists())
		ProductInfo.getParentFile().mkdirs();
	    if (!ProductInfo.exists()) {
		ProductInfo.createNewFile();
	    }
	    if (!filen.exists()) {
		filen.createNewFile();
	    } else if (SentenceIsSaved(filen, Sentence)) {
		Class_Controller.PrintAction("-> " + this.getClass().toString() + " sentence was already saved");
		return;
	    }
	    PrintStream utfil;
	    FileOutputStream appendFilen = new FileOutputStream(Filnavn, true);
	    utfil = new PrintStream(appendFilen);
	    //Class_Controller.PrintAction("-> " + this.getClass().toString() + " lagrer til fil: " + Sentence + " for " + Phrase);
	    utfil.println("Å " + Sentence);
	    utfil.close();
	} catch (IOException T) {
	    Class_Controller.CastErrors(T);
	} catch (Exception T) {
	    Class_Controller.CastErrors(T);
	}
    }

    private boolean SentenceIsSaved(File filen, String Sentence) throws FileNotFoundException {
	final Scanner scanner = new Scanner(filen);
	while (scanner.hasNextLine()) {
	    final String lineFromFile = scanner.nextLine();
	    if (lineFromFile.contains(Sentence)) {
		//System.out.println("I found " +name+ " in file " +file.getName());
		scanner.close();
		return true;
	    }
	}
	scanner.close();
	return false;
    }

    public void Load_URLs() {
	try {
	    Class_Controller.PrintAction(System.currentTimeMillis() + " " + this.getClass().toString() + " Loader URLr fra fil " + Filnavn);
	    filen = new File(Filnavn);
	    if (!filen.exists()) {
		filen.createNewFile();
	    }
	    FileInputStream fstream2 = new FileInputStream(filen);
	    DataInputStream in2 = new DataInputStream(fstream2);
	    int hundreds = 0;
	    int thousands = 0;
	    while (in2.available() > 0) {
		Class_Controller.SaveURL(in2.readLine(), "Loader", 100);
		loadedURLs++;
		hundreds++;
		if (hundreds >= 1000) {
		    hundreds = 0;
		    thousands++;
		    Class_Controller.PrintAction(System.currentTimeMillis() + " " + this.getClass().toString() + " " + thousands
			    + "'000 URLs loaded so far");
		}
	    }
	    if (loadedURLs > 0) {
		Class_Controller.GoogledPhrases = true;
	    }
	    Class_Controller.PrintAction(System.currentTimeMillis() + " " + this.getClass().toString() + " Done loading URLs. TOtal URLs="
		    + loadedURLs);
	    in2.close();
	    Backup();
	    boolean doneLoading = true;
	    Class_Controller.TimeTick("IO");
	} catch (Exception T) {
	    Class_Controller.CastErrors(T);
	}
    }

    private void Backup() {
	try {
	    filen = new File(Filnavn);
	    if (!filen.exists()) {
		return;
	    }
	    FileOutputStream appendFilen = new FileOutputStream(Filnavn + ".Backup.txt", true);
	    PrintStream utfil = new PrintStream(appendFilen);
	    FileInputStream fstream2 = new FileInputStream(filen);
	    DataInputStream in2 = new DataInputStream(fstream2);
	    while (in2.available() > 0) {
		utfil.println(in2.readLine());
	    }
	    utfil.close();
	    in2.close();
	} catch (Exception T) {
	    Class_Controller.CastErrors(T);
	}
    }

    public void SaveAll(Object_Webpage[] array) {
	if (array == null) {
	    return;
	}
	//System.out.println( "SaveAll "+array );
	int Count = 0;
	Object_Webpage[] LinkArray = array;
	try {
	    filen = new File(Filnavn);
	    filen.delete();
	    if (!filen.exists()) {
		filen.createNewFile();
	    }
	    PrintStream utfil;
	    FileOutputStream appendFilen = new FileOutputStream(Filnavn, true);
	    utfil = new PrintStream(appendFilen);
	    Count = 0;
	    for (int X = 0; X < LinkArray.length; X++) {
		if (LinkArray[X] != null) {
		    if (LinkArray[X].Get_SelfRelationValue() > Class_Controller.InterestBorder) {
			System.out.println("-> Lagrer til fil: "
				+ Class_Controller.Class_Brain_PageManager.FigureRootsite(LinkArray[X], "BrainIO"));
			utfil.println("\n" + LinkArray[X].Get_Extract() + " - " + LinkArray[X].Get_URL().toString() + "\n");
			Count++;
		    }
		}
	    }
	    utfil.close();
	    System.out.println(System.currentTimeMillis() + " Database saved: " + Count + " links");
	} catch (IOException T) {
	    Class_Controller.CastErrors(T);
	} catch (Exception T) {
	    Class_Controller.CastErrors(T);
	}
    }

    public void PrintActionLog(String Message) {
	try {
	    Date Idag = new Date();
	    String Logname = "Action log.txt";
	    File ProductsFile = new File(Logname);
	    if (!ProductsFile.exists()) {
		ProductsFile.createNewFile();
	    }
	    PrintStream utfil;
	    FileOutputStream appendFilen = new FileOutputStream(ProductsFile, true);
	    utfil = new PrintStream(appendFilen);
	    utfil.println((1900 + Idag.getYear()) + "/" + (1 + Idag.getMonth()) + "/" + Idag.getDate() + " " + Idag.getHours() + "."
		    + Idag.getMinutes() + ":" + Idag.getSeconds() + " " + Message);
	    //System.out.println( Offer.Get_Name()+"~"+Offer.Get_Description()+"~"+Offer.Get_MetaDescription()+"~"+Offer.Get_MetaKeywords()+"~"+Offer.Get_Model()+"~"+Offer.Get_SKU()+"~"+Offer.Get_UPC()+"~"+Offer.Get_Location()+"~"+Offer.Get_Price()+"~"+Offer.Get_Quantity()+"~"+Offer.Get_MinQuantity()+"~"+Offer.Get_SubstractStock()+"~"+Offer.Get_RequiresShipping()+"~"+Offer.Get_SEOKeywords()+"~"+Offer.Get_Image()+"~"+Offer.Get_Length()+"~"+Offer.Get_Height()+"~"+Offer.Get_Width()+"~"+Offer.Get_Weight()+"~"+Offer.Get_ProductStatus()+"~"+Offer.Get_SortOrder()+"~"+Offer.Get_Manufacturer()+"~"+Offer.Get_Category()+"~"+Offer.Get_SubCategory()+"~"+Offer.Get_Download()+"~"+Offer.Get_Attribute()+"~"+Offer.Get_Options()+"~"+Offer.Get_DiscountPrice()+"~"+Offer.Get_SpecialPrice()+"~"+Offer.Get_AdditionalImage()+"~"+Offer.Get_Points()+"~"+Offer.Get_RewardPoints() );
	    utfil.close();
	    utfil = null;
	} catch (IOException T) {
	    Class_Controller.PrintAction("Could not save action A: " + T.getMessage() + " " + T.getCause() + " " + T.getClass());
	    //Class_Controller.ReportError( T, this.getClass().toString() + " PrintActionLog" );
	    if (T.getMessage().equals("Access is denied")) {
		PrintActionLog(Message);
	    } else {
		//Class_Controller.ReportError( T, this.getClass().toString() + " PrintActionLog" );
	    }
	} catch (Exception T) {
	    Class_Controller.PrintAction("Could not save action B: " + T.getMessage() + " " + T.getCause() + " " + T.getClass());
	    //Class_Controller.ReportError( T, this.getClass().toString() + " PrintActionLog" );
	}
    }
}
