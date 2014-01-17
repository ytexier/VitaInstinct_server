package models.factory;

import java.util.Date;

import models.ActivityEnding;
import models.Location;
import models.Organism;
import models.User;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

public abstract class AbstractActivity {
	
	@Id 
	private ObjectId id;
	private Date date;
	@Embedded 
	private Location location;
	private Integer amountOfOrganism;
 	@Embedded
	private Organism organism;
	private ActivityEnding activityEnding;
	private Key<User> creator;
	
	public AbstractActivity() {
	}
	
    public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public ActivityEnding getActivityEnding() {
		return activityEnding;
	}
	public void setActivityEnding(ActivityEnding activityEnding) {
		this.activityEnding = activityEnding;
	}
	public Integer getAmountOfOrganism() {
		return amountOfOrganism;
	}
	public void setAmountOfOrganism(Integer amountOfOrganism) {
		this.amountOfOrganism = amountOfOrganism;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Organism getOrganism() {
		return organism;
	}
	public void setOrganism(Organism _organism){
		organism = _organism;
	}
	public Key<User> getCreator() {
		return creator;
	}
	public void setCreator(Key<User> creator) {
		this.creator = creator;
	}
	
}
