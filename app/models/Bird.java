package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Bird extends Amniote{
	public Bird(){}
	public Bird(String specie, String thumbnail, String _abstract){
		super(specie, thumbnail, _abstract);
	}
	public Bird(String specie, String thumbnail, String _abstract, Sex sex) {
		super(specie, thumbnail, _abstract, sex);
	}

}
