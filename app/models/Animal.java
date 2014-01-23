package models;

public abstract class Animal extends Organism {
	public Animal(){}
	public Animal(String specie) {
		super(specie);
	}
	public Animal(String specie, Sex sex) {
		super(specie, sex);
	}
}
