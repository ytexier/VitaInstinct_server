package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Bird extends Amniote{

	public Bird(){}
	public Bird(String _specie){
		super(_specie);
	}
	public Bird(String _specie, Sex _sex) {
		super(_specie, _sex);
	}

}
