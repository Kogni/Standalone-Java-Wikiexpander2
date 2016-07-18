package Objects;

public class Object_Ord{

	public String	Ordet;
	public int		RelationValue	= 1;
	public int		SaveValue		= 1;
	private boolean	Searched		= false;

	public Object_Ord( String Ordet, int Value, int SaveValue ) {
		this.Ordet = Ordet;
		this.RelationValue = Value;
		this.SaveValue = SaveValue;
	}

	public void Set_Searched() {
		Searched = true;
	}
}
