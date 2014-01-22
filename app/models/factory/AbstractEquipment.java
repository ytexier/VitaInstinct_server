package models.factory;

import models.User;
import models.Vita;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Id;

import com.hp.hpl.jena.rdf.model.Model;

import agents.AgentJena;


public abstract class AbstractEquipment {
	@Id 
	private ObjectId id;
	private String sector;
	private String label;
	private String comment;
	private String uri;

	private Key<User> creator;
	
	public abstract Model accept(AgentJena agent);
	
	public AbstractEquipment() {}

	public AbstractEquipment(String sector, String label, String comment,
			Key<User> creator) {
		super();
		this.sector = sector;
		this.label = label;
		this.comment = comment;
		this.creator = creator;
		this.uri = Vita.getURL() + "sector/" + sector + "/equipment/" + id;
	}
	

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Key<User> getCreator() {
		return creator;
	}

	public void setCreator(Key<User> creator) {
		this.creator = creator;
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}
}
