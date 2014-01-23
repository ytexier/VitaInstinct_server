package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Plant extends Organism{

	public Plant(){}
	public Plant(String specie, String thumbnail, String _abstract) {
		super(specie, thumbnail, _abstract);
	}
	
	public Plant(String specie, String thumbnail, String _abstract, Sex sex) {
		super(specie, thumbnail, _abstract, sex);
	}

}
