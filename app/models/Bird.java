package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Bird extends Amniote{
	public Bird(){}
	public Bird(String specie){
		super(specie, "bird/");
	}
	public Bird(String _specie, Sex _sex) {
		super(_specie, _sex);
	}

}
