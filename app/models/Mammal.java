package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Mammal extends Amniote{

	public Mammal(){}
	public Mammal(String _specie){
		super(_specie);
	}
	public Mammal(String _specie, Sex _sex) {
		super(_specie, _sex);
	}

}
