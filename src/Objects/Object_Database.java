
package Objects;

import java.net.URL;
public class Object_Database {

    private int URLS = 0;

    private final Object_Webpage[] LinkArray;

    public Object_Database() {
	LinkArray = new Object_Webpage[9999];
    }

    public void InsertLink(String Adresse, int RelatedWordsFound, int Selfvalue) {
	if (Adresse.length() == 0) {
	    return;
	}
	for (int X = 0; X < LinkArray.length; X++) {
	    if (LinkArray[X] == null) {
		try {
		    LinkArray[X] = new Object_Webpage(new URL(Adresse), RelatedWordsFound, Selfvalue);
		    URLS++;
		    SortURLsAlpha();
		    return;
		} catch (Exception T) {
		    System.err.println("Object_Database Kunne ikke lagre url");
		    System.err.println("InsertLink " + Adresse);
		    System.err.println("Throwable message: " + T.getMessage());
		    System.err.println("Throwable cause: " + T.getCause());
		    System.err.println("Throwable class: " + T.getClass());
		    if (T.getStackTrace() != null) {
			System.err.println("Exception origin: ");
			System.err.println("Class: " + T.getStackTrace()[0].getClassName());
			System.err.println("Method: " + T.getStackTrace()[0].getMethodName());
			System.err.println("Line: " + T.getStackTrace()[0].getLineNumber());
		    }
		    if (T.getStackTrace() != null) {
			System.err.println("Exception origin: ");
			System.err.println("Class: " + T.getStackTrace()[1].getClassName());
			System.err.println("Method: " + T.getStackTrace()[1].getMethodName());
			System.err.println("Line: " + T.getStackTrace()[1].getLineNumber());
		    }
		}
	    } else {
		if (LinkArray[X].Get_URL().toString().equals(Adresse)) {
		    LinkArray[X].Set_SelfRelationValue(LinkArray[X].Get_SelfRelationValue() + RelatedWordsFound, "database");
		    return;
		} else if (LinkArray[X].Get_URL().toString().compareToIgnoreCase(Adresse) == 0) {
		    LinkArray[X].Set_SelfRelationValue(LinkArray[X].Get_SelfRelationValue() + RelatedWordsFound, "database");
		    return;
		}
	    }
	}
    }

    private void SortURLsAlpha() {
	for (int X = 0; X < LinkArray.length; X++) {
	    if (LinkArray[X] != null) {
		for (int Y = X; Y < LinkArray.length; Y++) {
		    if (LinkArray[Y] != null) {
			if (!LinkArray[X].Get_URL().equals(LinkArray[Y].Get_URL())) {
			    if (LinkArray[X].Get_URL().toString().compareToIgnoreCase(LinkArray[Y].Get_URL().toString()) > 0) {
				Object_Webpage A = LinkArray[X];
				Object_Webpage B = LinkArray[Y];
				LinkArray[X] = B;
				LinkArray[Y] = A;
			    }
			}
		    }
		}
	    }
	}
    }

    public Object_Webpage[] GetDatabase() {
	return LinkArray;
    }
}
