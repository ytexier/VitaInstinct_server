package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Fish extends Animal {
	public Fish(){}
	public Fish(String _specie){
		super(_specie);
	}
	public Fish(String _specie, Sex _sex) {
		super(_specie, _sex);
	}

}
