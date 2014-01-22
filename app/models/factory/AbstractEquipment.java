package models.factory;

import models.User;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Id;

import agents.AgentJena;


public abstract class AbstractEquipment {
	@Id 
	private ObjectId id;
	private String sector;
	private String label;
	private String comment;

	private Key<User> creator;
	
	public abstract void accept(AgentJena agentJena);
	
	public AbstractEquipment() {}

	public AbstractEquipment(String sector, String label, String comment,
			Key<User> creator) {
		super();
		this.sector = sector;
		this.label = label;
		this.comment = comment;
		this.creator = creator;
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
}
