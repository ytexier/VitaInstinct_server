package models;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import agents.AgentManager;


public abstract class Organism {
	@Id
	@Property("id")
	private ObjectId id;
	private String specie;
	@Embedded
	private Sex sex;
	
	private String uri;
    
	public void accept(AgentManager v){
		v.spy(this);
	}
	
	
	public Organism(){}
	
	public Organism(String specie){
		this.specie = specie;
		this.uri = Vita.getURL() + "organism/";
	}
	
	public Organism(String _specie, Sex _sex){
		specie = _specie;
		sex = _sex;
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}


	public String getURI() {
		return uri;
	}


	public void setURI(String url) {
		uri = url;
	}
}
