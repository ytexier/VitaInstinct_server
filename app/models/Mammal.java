package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Mammal extends Amniote{
	public Mammal(){}
	public Mammal(String specie){
		super(specie, "mammal/");
	}
	public Mammal(String specie, Sex sex) {
		super(specie, sex);
	}

}
