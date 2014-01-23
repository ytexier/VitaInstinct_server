package models.hunting;

import models.ActivityEnding;
import models.Location;
import models.Mammal;
import models.User;
import models.factory.AbstractActivity;


import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentWriter;


@Entity
public class HuntingActivity extends AbstractActivity{
	

	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	
	@Override
	public Model accept(AgentWriter agent) {
		return agent.spy(this);
	}

	
	public HuntingActivity(){

	}
	
	public HuntingActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment){
		super(new Mammal(organism), creator, amountOfOrganism, location, "fishing", date, (HuntingEvent) event, (HuntingEquipment) equipment);
	}
	

	
    public static HuntingActivity findById(String id){
    	HuntingActivity activity = MorphiaObject.datastore.find(HuntingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
    }
    
    public HuntingEvent getEvent(){
    	return (HuntingEvent) super.getEvent();
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
