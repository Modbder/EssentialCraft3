package ec3.api;

public enum EnumMatrixType {
	
	EMPTY,
	CHAOS,
	FROZEN,
	MAGIC,
	SHADE;
	
	EnumMatrixType()
	{
		id = this.ordinal();
	}
	
	EnumMatrixType(int i)
	{
		id = i;
	}
	
	int id;
	
	public int retrieveID()
	{
		return id;
	}

}
