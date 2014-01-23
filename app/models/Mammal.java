package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Mammal extends Amniote{
	public Mammal(){}
	public Mammal(String specie, String thumbnail, String _abstract) {
		super(specie, thumbnail, _abstract);
	}
	public Mammal(String specie, String thumbnail, String _abstract, Sex sex) {
		super(specie, thumbnail, _abstract, sex);
	}

}
