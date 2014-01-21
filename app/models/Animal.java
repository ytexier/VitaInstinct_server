package models;

public abstract class Animal extends Organism {
	public Animal(){}
	public Animal(String specie, String url) {
		super(specie, "animal/" + url);
	}
	public Animal(String specie, Sex sex) {
		super(specie, sex);
	}
}
