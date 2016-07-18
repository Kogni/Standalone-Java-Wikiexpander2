package Objects;

public class Object_Ordbok_Old {

    public final Object_Ord[] Ord;

    Object_Ordbok_Old() {
	Ord = new Object_Ord[999999];
	for (int X = 0; X < Ord.length; X++) {
	    Ord[X] = new Object_Ord();
	}
    }

}
