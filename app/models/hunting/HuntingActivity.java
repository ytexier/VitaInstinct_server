package models.hunting;

import java.util.Date;

import models.ActivityEnding;
import models.Amniote;
import models.Location;
import models.User;
import models.factory.AbstractActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

import controllers.MorphiaObject;
import agents.AgentManager;

@Entity
public class HuntingActivity extends AbstractActivity{

	public void accept(AgentManager v){
		v.spy(this);
	}
	

    public static HuntingActivity findById(String id){
    	HuntingActivity activity = MorphiaObject.datastore.find(HuntingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
    }
    
	
	public void setOrganism(Amniote _amniote){
		super.setOrganism(_amniote);
	}

	public void setDate(String _date) {
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
