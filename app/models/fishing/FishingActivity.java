package models.fishing;

import java.util.ArrayList;

import models.ActivityEnding;
import models.Fish;
import models.Location;
import models.User;
import models.factory.AbstractActivity;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;

@Entity
public class FishingActivity extends AbstractActivity{
	
	@Embedded	private FishingEvent event;
	@Reference	private FishingEquipment equipment;

	public FishingActivity(){

	}
	
	public FishingActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment){
		super(new Fish(organism), creator, amountOfOrganism, location, "fishing", date);
		this.equipment = (FishingEquipment) equipment;
		this.event = (FishingEvent) event;
	}
	
	
	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	
    public static FishingActivity findById(String id){
    	FishingActivity activity = MorphiaObject.datastore.find(FishingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
    }
	
	public void setDate(String date) {
		super.setDate(date);		
	}
	
	public void setLocation(Location location) {
		super.setLocation(location);		
	}
	
	
	public void setActivityEnding(ActivityEnding activityEnding) {
		super.setActivityEnding(activityEnding);		
	}
	
	
	public void setAmountOfOrganism(Integer _amountOfOrganism){
		super.setAmountOfOrganism(_amountOfOrganism);
	}

	public FishingEvent getEvent() {
		return event;
	}

	public void setEvent(FishingEvent event) {
		this.event = event;
	}
	
	public FishingEquipment getEquipment() {
		return equipment;
	}

	public void setEquipment(FishingEquipment equipment) {
		this.equipment = equipment;
	}

	

}
