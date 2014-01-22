package models.hunting;

import java.util.ArrayList;

import models.ActivityEnding;


import models.Location;
import models.Mammal;
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
public class HuntingActivity extends AbstractActivity{
	
	@Embedded	private HuntingEvent event;
	@Embedded	private HuntingEquipment equipment;

	
	public HuntingActivity(){

	}
	
	public HuntingActivity(Mammal organism, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment){
		super(organism, creator, amountOfOrganism, location, "hunting", date);	
		this.equipment = (HuntingEquipment) equipment;
		this.event = (HuntingEvent) event;
	}
	
	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	
    public static HuntingActivity findById(String id){
    	HuntingActivity activity = MorphiaObject.datastore.find(HuntingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
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


	public HuntingEvent getEvent() {
		return event;
	}


	public void setEvent(HuntingEvent event) {
		this.event = event;
	}

	public HuntingEquipment getEquipment() {
		return equipment;
	}

	public void setEquipment(HuntingEquipment equipment) {
		this.equipment = equipment;
	}

	

}
