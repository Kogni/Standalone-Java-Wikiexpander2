package Objects;

import java.net.URL;

public class Object_Website extends Object_Webpage{

	private final Object_Webpage[]	SiteMap;

	public Object_Website(URL Adresse, int RelatedWordsFound) {

		super( Adresse, RelatedWordsFound, 0);
		SiteMap = new Object_Webpage[999];

	}

	public boolean InsertPage( Object_Webpage newpage ) {

		for ( int x = 0; x < SiteMap.length; x++ ) {
			if ( SiteMap[x] == null ) {
				SiteMap[x] = newpage;

				return true;
			}
			else if ( SiteMap[x].Get_URL().toString().equals( newpage.Get_URL().toString() ) ) {
				return false;
			}
		}
		return false;
	}

	public Object_Webpage Get_NextPageSearch() {

		int LinkedRelation = 0;
		Object_Webpage Highest = null;
		if (!this.Get_Searched()) {
			if ( this.Get_LinkedRelationValue() > LinkedRelation ) {
				Highest = this;
				LinkedRelation = this.Get_LinkedRelationValue();
			}
		}

		for ( int x = 0; x < SiteMap.length; x++ ) {
			if ( SiteMap[x] != null ) {
				if (!SiteMap[x].Get_Searched()) {
					if ( SiteMap[x].Get_LinkedRelationValue() > LinkedRelation ) {
						LinkedRelation = SiteMap[x].Get_LinkedRelationValue();
						Highest = SiteMap[x];

					}
				}
			}
		}
		return Highest;
	}

	public int Get_SiteRelationValue() {

		int SiteRelationValue = 0;
		for ( int x = 0; x < SiteMap.length; x++ ) {
			if ( SiteMap[x] != null ) {

				SiteRelationValue = SiteRelationValue + SiteMap[x].Get_SelfRelationValue();

			}
		}

		return SiteRelationValue;
	}

	public Object_Webpage[] Get_SiteMap() {
		return SiteMap;
	}

	public boolean isURLAdded( URL page ) {

		for ( int x = 0; x < SiteMap.length; x++ ) {
			if ( SiteMap[x] != null ) {
				if ( SiteMap[x].Get_URL().toString().equals( page.toString() ) ) {
					return true;
				}
			}
			else {
				return false;
			}
		}
		return false;
	}

	public boolean GetSaved( URL page ) {

		for ( int x = 0; x < SiteMap.length; x++ ) {
			if ( SiteMap[x] != null ) {
				if ( SiteMap[x].Get_URL().toString().equals( page.toString() ) ) {
					return SiteMap[x].Saved;
				}
			}
			else {
				return false;
			}
		}
		return false;
	}

}
