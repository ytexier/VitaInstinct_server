package models.fishing;

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
import models.hunting.HuntingEvent;

@Entity
public class FishingEvent extends AbstractEvent {
	
	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	@Override
	public Model accept(AgentWriter agent) {
		return agent.spy(this);
	}
	
	public FishingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FishingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("fishing", label, date, comment, location, creator);
	}
	
    public static FishingEvent findById(String id){
    	FishingEvent event = MorphiaObject.datastore.find(FishingEvent.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return event;
    }
    
	
	public static List<FishingEvent> all() throws Exception{
        if (MorphiaObject.datastore != null)
                return MorphiaObject.datastore
                		.find(FishingEvent.class).asList();
        else
        	return new ArrayList<FishingEvent>();
    }
    
}
