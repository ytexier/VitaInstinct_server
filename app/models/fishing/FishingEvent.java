package models.fishing;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentManager;
import models.Location;
import models.User;
import models.factory.AbstractEvent;
import models.picking.PickingEvent;

@Entity
public class FishingEvent extends AbstractEvent {
	
	private ArrayList<FishingActivity> activities;
	
	public FishingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FishingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("fishing", label, date, comment, location, creator);
		this.activities = new ArrayList<FishingActivity>();
		// TODO Auto-generated constructor stub
	}
	
	public void addActivity(FishingActivity activity) {
		this.activities.add(activity);
	}

	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}

	public ArrayList<FishingActivity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<FishingActivity> activities) {
		this.activities = activities;
	}
	
    public static FishingEvent findById(String id){
    	FishingEvent event = MorphiaObject.datastore.find(FishingEvent.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return event;
    }
}
