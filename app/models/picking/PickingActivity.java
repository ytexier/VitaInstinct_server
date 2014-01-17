package models.picking;

import java.util.Date;

import models.ActivityEnding;
import models.Location;
import models.Plant;
import models.factory.AbstractActivity;
import models.hunting.HuntingActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

import controllers.MorphiaObject;
import agents.AgentManager;

@Entity
public class PickingActivity extends AbstractActivity{
	
	public void accept(AgentManager v){
		v.spy(this);
	}	
	
    public static PickingActivity findById(String id){
    	PickingActivity activity = MorphiaObject.datastore.find(PickingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
    }
	
	
	
	public void setPlant(Plant _plant){
		super.setOrganism(_plant);
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
