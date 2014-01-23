package models;

public abstract class Animal extends Organism {
	public Animal(){}
	public Animal(String specie, String thumbnail, String _abstract) {
		super(specie, thumbnail, _abstract);
	}
	public Animal(String specie, String thumbnail, String _abstract, Sex sex) {
		super(specie, thumbnail, _abstract, sex);
	}
}
