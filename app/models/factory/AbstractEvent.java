package models.factory;

import models.Location;
import models.User;
import models.Vita;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Id;

import agents.AgentJena;

public abstract class AbstractEvent {
	@Id 
	private ObjectId id;
	private String sector;
	private String date;
	private String comment;
	private Location location;
	private String uri;
	
	private Key<User> creator;
	
	
	
	public AbstractEvent() {}
	
	
	
	public AbstractEvent(String sector, String date, String comment,
			Location location, Key<User> creator) {
		super();
		this.sector = sector;
		this.date = date;
		this.comment = comment;
		this.location = location;
		this.setCreator(creator);
		this.uri = Vita.getURL() + "sector/" + sector + "/event/" + id;
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
