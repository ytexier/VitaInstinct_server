package models.factory;

import java.util.List;

import models.ActivityEnding;
import models.Location;
import models.Organism;
import models.User;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

import com.hp.hpl.jena.rdf.model.Model;

import agents.AgentJena;

public abstract class AbstractActivity {
	
	@Id 
	private ObjectId id;
	private String date;
	private String sector;
	
	@Embedded 
	private Location location;
	private Integer amountOfOrganism;
 	@Embedded
	private Organism organism;
	private ActivityEnding activityEnding;
	@Embedded
	private Key<User> creator;
	
	@Embedded
	private AbstractEvent event;
	@Embedded
	private List<AbstractEquipment> equipment;
	
	public AbstractActivity(){
		
	}
	
	public AbstractActivity(Organism organism, Key<User> creator, int amountOfOrganism, Location location, String sector, String date) {
		this.organism = organism;
		this.creator = creator;
		this.amountOfOrganism = amountOfOrganism;
		this.location = location;
		this.sector = sector;
		this.date = date;
	}
	
	public abstract Model accept(AgentJena agent);

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
		this.organism = organism;
	}
	public Key<User> getCreator() {
		return creator;
	}
	public void setCreator(Key<User> creator) {
		this.creator = creator;
	}

	public String getSector(){
		return sector;
	};

	public void setSector(String sector){
		this.sector = sector;
	}
	
	public String getLabel(){
		return sector+"|"+date+"|"+organism.getSpecie();
	}
 
	
}
