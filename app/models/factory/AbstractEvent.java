package models.factory;

import java.util.ArrayList;
import java.util.List;

import models.Location;
import models.User;

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
	
	private Key<User> creator;
	
	private ArrayList<? extends AbstractActivity> activities;
	private ArrayList<User> users;
	
	public AbstractEvent() {}
	
	
	
	public AbstractEvent(String sector, String date, String comment,
			Location location, Key<User> creator) {
		super();
		this.sector = sector;
		this.date = date;
		this.comment = comment;
		this.location = location;
		this.creator = creator;
		this.users = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		this.users.add(user);
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


	public List<User> getUsers() {
		return users;
	}



	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}



	public ArrayList<? extends AbstractActivity> getActivities() {
		return activities;
	}



	public void setActivities(ArrayList<? extends AbstractActivity> activities) {
		this.activities = activities;
	}



	
}
