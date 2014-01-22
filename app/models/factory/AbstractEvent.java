package models.factory;

import models.Location;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import agents.AgentJena;

public abstract class AbstractEvent {
	@Id 
	private ObjectId id;
	private String sector;
	private String date;
	private String comment;
	private Location location;
	
	
	
	public AbstractEvent() {}
	
	
	
	public AbstractEvent(String sector, String date, String comment,
			Location location) {
		super();
		this.sector = sector;
		this.date = date;
		this.comment = comment;
		this.location = location;
	}
	
	
	public abstract void accept(AgentJena agentJena);

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}



	
}
