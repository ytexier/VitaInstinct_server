package models;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Mammal extends Amniote{
	private String url;
	public Mammal(){}
	public Mammal(String specie){
		super(specie);
		url = Vita.getURI() + "organism/mammal/" + specie;
		super.setURI(url);
	}
	public Mammal(String specie, Sex sex) {
		super(specie, sex);
	}

}
