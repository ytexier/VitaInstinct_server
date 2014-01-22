package models.factory;

import java.util.ArrayList;
import java.util.List;

import models.Location;
import models.User;
import models.Vita;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import agents.AgentJena;

public abstract class AbstractEvent {
	@Id 
	private ObjectId id;
	private String sector;
	private String label;
	private String date;
	private String comment;
	@Embedded
	private Location location;
<<<<<<< HEAD
	private String uri;
	
	private Key<User> creator;
	
	private ArrayList<? extends AbstractActivity> activities;
	private ArrayList<User> registers;
=======
	@Embedded
	private Key<User> creator;
	
	@Reference private ArrayList<? extends AbstractActivity> activities;
	@Reference private ArrayList<User> register;
>>>>>>> dimql
	
	public AbstractEvent() {}
	
	public AbstractEvent(String sector, String label, String date, String comment,
			Location location, Key<User> creator) {
		super();
		this.sector = sector;
		this.label = label;
		this.date = date;
		this.comment = comment;
		this.location = location;
		this.uri = Vita.getURL() + "sector/" + sector + "/event/" + id;
		this.creator = creator;
<<<<<<< HEAD
		this.registers = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		this.registers.add(user);
=======
		this.register = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		this.register.add(user);
>>>>>>> dimql
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

<<<<<<< HEAD
	public List<User> getRegisters() {
		return registers;
=======


	public void setCreator(Key<User> creator) {
		this.creator = creator;
	}


	public List<User> getRegisters() {
		return register;
>>>>>>> dimql
	}



<<<<<<< HEAD
	public void setRegisters(ArrayList<User> registers) {
		this.registers = registers;
=======
	public void setRegisters(ArrayList<User> users) {
		this.register = users;
>>>>>>> dimql
	}



	public ArrayList<? extends AbstractActivity> getActivities() {
		return activities;
	}



	public void setActivities(ArrayList<? extends AbstractActivity> activities) {
		this.activities = activities;
	}



	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
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
