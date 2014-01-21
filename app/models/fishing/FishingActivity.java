package models.fishing;

import java.util.ArrayList;
import java.util.Date;

import models.ActivityEnding;
import models.Fish;
import models.Location;
import models.factory.AbstractActivity;
import models.hunting.HuntingActivity;
import models.hunting.HuntingEquipment;
import models.hunting.HuntingEvent;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentManager;

@Entity
public class FishingActivity extends AbstractActivity{
	
	@Embedded
	private FishingEvent event;
	@Embedded
	private ArrayList<FishingEquipment> equipments;
	
	
	@Override
	public void accept(AgentJena agent) {
		agent.spy(this);
	}
	
    public static FishingActivity findById(String id){
    	FishingActivity activity = MorphiaObject.datastore.find(FishingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
    }
	
	public void setFish(Fish fish){
		super.setOrganism(fish);
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

	public ArrayList<FishingEquipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(ArrayList<FishingEquipment> equipments) {
		this.equipments = equipments;
	}
	
	

}
