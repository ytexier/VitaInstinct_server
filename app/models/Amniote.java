package models;

public abstract class Amniote extends Animal{
	public Amniote() {}
	public Amniote(String specie) {
		super(specie);
	}
	public Amniote(String specie, Sex sex) {
		super(specie, sex);
	}

}
