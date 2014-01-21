package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Plant extends Organism{

	public Plant(){}
	public Plant(String specie) {
		super(specie, "plant");
	}
	
	public Plant(String specie, Sex sex) {
		super(specie, sex);
	}

}
