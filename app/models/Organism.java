package models;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import agents.AgentManager;

import com.hp.hpl.jena.rdf.model.Model;


public abstract class Organism {
	@Id
	@Property("id")
	private ObjectId id;
	private String specie;
	@Embedded
	private Sex sex;
	
	private String URI;
    
	public Model accept(AgentManager v){
		return v.spy(this);
	}
	
	
	public Organism(){}
	public Organism(String _specie){
		specie = _specie;
	}
	
	public Organism(String _specie, String url){
		specie = _specie;
		setURI(url + specie);
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
		return URI;
	}


	public void setURI(String uRI) {
		URI = uRI;
	}
}
