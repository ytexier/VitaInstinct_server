package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Fish extends Animal {
	public Fish(){}
	public Fish(String specie){
		super(specie, "fish");
	}
	public Fish(String specie, Sex sex) {
		super(specie, sex);
	}

}
