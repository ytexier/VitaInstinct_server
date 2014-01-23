package models;

public abstract class Amniote extends Animal{
	public Amniote() {}
	public Amniote(String specie, String thumbnail, String _abstract) {
		super(specie, thumbnail, _abstract);
	}
	public Amniote(String specie, String thumbnail, String _abstract, Sex sex) {
		super(specie, thumbnail, _abstract, sex);
	}

}
