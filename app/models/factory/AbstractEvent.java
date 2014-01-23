package models.factory;

import models.Location;
import models.User;
import models.Vita;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Id;
import com.hp.hpl.jena.rdf.model.Model;

import agents.AgentJena;
import agents.AgentWriter;


public abstract class AbstractEvent {
	
	@Id	private ObjectId id;
		private String sector;
		private String label;
		private String date;
		private String comment;
		private Location location;
		private Key<User> creator;
		private String uri;
		
	public abstract Model accept(AgentJena agent);
	public abstract Model accept(AgentWriter agent);
	
	public AbstractEvent() {}
	
	public AbstractEvent(String sector, String label, String date, String comment,
			Location location, Key<User> creator) {
		super();
		this.sector = sector;
		this.label = label;
		this.date = date;
		this.comment = comment;
		this.location = location;
		this.setURI(Vita.getURL() + "sector/" + sector + "/event/");
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
	
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

}
