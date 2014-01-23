package models.hunting;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentWriter;
import models.Location;
import models.User;
import models.factory.AbstractEvent;

@Entity
public class HuntingEvent extends AbstractEvent {
	
	private ArrayList<HuntingActivity> activities;
	
	public HuntingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("hunting", label, date, comment, location, creator);
		this.activities = new ArrayList<HuntingActivity>();
		// TODO Auto-generated constructor stub
	}
	
	public HuntingEvent() {
		// TODO Auto-generated constructor stub
	}

	public void addActivity(HuntingActivity activity) {
		this.activities.add(activity);
	}

	public ArrayList<HuntingActivity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<HuntingActivity> activities) {
		this.activities = activities;
	}
	
    public static HuntingEvent findById(String id){
    	HuntingEvent event = MorphiaObject.datastore.find(HuntingEvent.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return event;
    }
    
	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	@Override
	public Model accept(AgentWriter agent) {
		return agent.spy(this);
	}
	
	
	public static List<HuntingEvent> all() throws Exception{
        if (MorphiaObject.datastore != null)
                return MorphiaObject.datastore
                		.find(HuntingEvent.class).asList();
        else
        	return new ArrayList<HuntingEvent>();
    }
}
