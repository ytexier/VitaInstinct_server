package models;

public enum Sex{
	male, female;
	public static boolean contains(String s)
	{
		for(Sex Sex:values())
			if (Sex.name().equals(s)) 
				return true;
		return false;
	} 	
}
