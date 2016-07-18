
package Objects;

import java.net.URL;
public class Object_Webpage {

    private final URL Adresse;

    private int LinkedRelationValue;

    private int SelfRelationValue;

    private boolean Searched = false;

    boolean Saved = false;

    public String SearchPhrase;

    public Object_Webpage(URL Adresse, int LinkedRelationValue, int SelfRelationValue) {
	this.Adresse = Adresse;
	this.LinkedRelationValue = LinkedRelationValue;
	this.SelfRelationValue = SelfRelationValue;
    }

    public URL Get_URL() {
	return Adresse;
    }

    public int Get_LinkedRelationValue() {
	return LinkedRelationValue;
    }

    boolean Get_Searched() {
	return Searched;
    }

    public int Get_SelfRelationValue() {
	return SelfRelationValue;
    }

    public void Set_LinkedRelationValue(int value) {
	LinkedRelationValue = value;
    }

    public void Set_Searched() {
	Searched = true;
    }

    public void Set_SearchFailed() {
	Searched = false;
    }

    public void Set_SelfRelationValue(int value, String source) {
	SelfRelationValue = value;
    }

    public String Get_Extract() {
        String extract = "";
        return extract;
    }

    public void SetSaved() {
	Saved = true;
    }

    public boolean GetSaved() {
	return Saved;
    }
}
