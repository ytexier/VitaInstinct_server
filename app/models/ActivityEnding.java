package models;

public enum ActivityEnding{	
	kill, sight;
	public static boolean contains(String s)
	{
		for(ActivityEnding ActivityEnding:values())
			if (ActivityEnding.name().equals(s)) 
				return true;
		return false;
	} 	
}
