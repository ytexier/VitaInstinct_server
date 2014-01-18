package models.fishing;

import java.util.Date;

import models.ActivityEnding;
import models.Fish;
import models.Location;
import models.factory.AbstractActivity;
import models.hunting.HuntingActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

import controllers.MorphiaObject;
import agents.AgentManager;

@Entity
public class FishingActivity extends AbstractActivity{
	public void accept(AgentManager v){
		v.spy(this);
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
}
