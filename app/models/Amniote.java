package models;

public abstract class Amniote extends Animal{
	public Amniote() {}
	public Amniote(String specie, String url) {
		super(specie, "amniote/" + url);
	}
	public Amniote(String specie, Sex sex) {
		super(specie, sex);
	}

}
