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
	
	public void setFish(Fish _fish){
		super.setOrganism(_fish);
	}
	public void setDate(Date _date) {
		super.setDate(_date);		
	}
	
	public void setLocation(Location _location) {
		super.setLocation(_location);		
	}
	
	
	public void setActivityEnding(ActivityEnding _ActivityEnding) {
		super.setActivityEnding(_ActivityEnding);		
	}
	
	
	public void setAmountOfOrganism(Integer _amountOfOrganism){
		super.setAmountOfOrganism(_amountOfOrganism);
	}
}
