package Objects;

public class Object_Ordbok_Old{

	public Object_Ord	Ord[];

	public Object_Ordbok_Old() {
		Ord = new Object_Ord[9999999];
		for ( int X = 0; X < Ord.length; X++ ) {
			Ord[X] = new Object_Ord( "", 0, 0 );
		}
	}

}
