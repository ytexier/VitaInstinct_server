package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Plant extends Organism{

	public Plant(){}
	public Plant(String _specie) {
		super(_specie);
	}
	
	public Plant(String _specie, Sex _sex) {
		super(_specie, _sex);
	}

}
