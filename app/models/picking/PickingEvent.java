package models.picking;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentManager;
import models.Location;
import models.User;
import models.factory.AbstractEvent;
import models.fishing.FishingActivity;
import models.hunting.HuntingActivity;
import models.hunting.HuntingEvent;


@Entity
public class PickingEvent extends AbstractEvent {
	

	private ArrayList<PickingActivity> activities;
	
	public PickingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PickingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("picking", label, date, comment, location, creator);
		this.activities = new ArrayList<PickingActivity>();
		// TODO Auto-generated constructor stub
	}
	
	public void addActivity(PickingActivity activity) {
		this.activities.add(activity);
	}

	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	
	public ArrayList<PickingActivity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<PickingActivity> activities) {
		this.activities = activities;
	}
	
    public static PickingEvent findById(String id){
    	PickingEvent event = MorphiaObject.datastore.find(PickingEvent.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return event;
    }
}
