package models.picking;

import java.util.ArrayList;
import java.util.Date;

import models.ActivityEnding;
import models.Fish;
import models.Location;
import models.Mammal;
import models.Plant;
import models.User;
import models.factory.AbstractActivity;
import models.fishing.FishingEquipment;
import models.fishing.FishingEvent;
import models.hunting.HuntingActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentManager;

@Entity
public class PickingActivity extends AbstractActivity{
	
	@Embedded
	private PickingEvent event;
	
	public PickingActivity(){

	}
	
	public PickingActivity(Plant organism, int amountOfOrganism, String date, Location location, Key<User> creator){
		super(organism, creator, amountOfOrganism, location, "hunting", date);
		super.setEquipments(new ArrayList<PickingEquipment>());
		event = new PickingEvent();
	}
    
	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	
	
    public static PickingActivity findById(String id){
    	PickingActivity activity = MorphiaObject.datastore.find(PickingActivity.class)
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


	public PickingEvent getEvent() {
		return event;
	}


	public void setEvent(PickingEvent event) {
		this.event = event;
	}

	
}
