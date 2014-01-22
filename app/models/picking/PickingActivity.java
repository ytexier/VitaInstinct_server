package models.picking;

import models.ActivityEnding;
import models.Location;
import models.Plant;
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
public class PickingActivity extends AbstractActivity{

	private PickingEvent event;
	private PickingEquipment equipment;

	public PickingActivity(){

	}

	public PickingActivity(Plant organism, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment){
		super(organism, creator, amountOfOrganism, location, "hunting", date);
		this.equipment = (PickingEquipment) equipment;
		this.event = (PickingEvent) event;
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

	public PickingEquipment getEquipment() {
		return equipment;
	}

	public void setEquipment(PickingEquipment equipment) {
		this.equipment = equipment;
	}
}
