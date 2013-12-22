package models;

public abstract class Animal extends Organism {
	public Animal(){}
	public Animal(String _specie) {
		super(_specie);
	}
	public Animal(String _specie, Sex _sex) {
		super(_specie, _sex);
	}
}
