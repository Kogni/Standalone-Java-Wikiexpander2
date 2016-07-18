package Objects;

import java.net.URL;

class Object_Link{

	private final URL			url;
	private boolean		SearchedForLinks	= false;

    public Object_Link( URL url, int relationValue ) {
		this.url = url;
        int relationValue1 = relationValue;
	}

	public URL GetURL() {
		return url;
	}

	public boolean GetSearched() {
		return SearchedForLinks;
	}

	public void SetSearched( boolean b ) {

		SearchedForLinks = true;
	}

}
