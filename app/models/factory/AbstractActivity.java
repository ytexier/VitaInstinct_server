package models.factory;

import java.util.ArrayList;
import java.util.Date;

import models.ActivityEnding;
import models.Location;
import models.Organism;
import models.User;
import models.hunting.HuntingActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

import agents.AgentJena;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;

public abstract class AbstractActivity {
	
	@Id 
	private ObjectId id;
	private String date;
	private String sectorName;
	
	@Embedded 
	private Location location;
	private Integer amountOfOrganism;
 	@Embedded
	private Organism organism;
	private ActivityEnding activityEnding;
	@Embedded
	private Key<User> creator;
	private String creatorName;
	


	
	public AbstractActivity() {
	}
	
	public abstract void accept(AgentJena agent);

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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Organism getOrganism() {
		return organism;
	}
	public void setOrganism(Organism organism){
		organism = organism;
	}
	public Key<User> getCreator() {
		return creator;
	}
	public void setCreator(Key<User> creator) {
		this.creator = creator;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
    
	
}
