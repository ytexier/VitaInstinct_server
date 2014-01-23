package models.picking;

import java.util.ArrayList;
import java.util.List;

import models.ActivityEnding;
import models.Location;
import models.Plant;
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
public class PickingActivity extends AbstractActivity{
	
	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	@Override
	public Model accept(AgentWriter agent) {
		return agent.spy(this);
	}
	
	public PickingActivity(){

	}

	public PickingActivity(String organism, String thumbnail, String _abstract, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment){
		super(new Plant(organism, thumbnail, _abstract), creator, amountOfOrganism, location, "piciking", date, (PickingEvent) event, (PickingEquipment) equipment);
	}
    
    public static PickingActivity findById(String id){
    	PickingActivity activity = MorphiaObject.datastore.find(PickingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
    }
	
    public PickingEvent getEvent(){
    	return (PickingEvent) super.getEvent();
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

	public static List<PickingActivity> all() throws Exception{
        if (MorphiaObject.datastore != null)
                return MorphiaObject.datastore
                		.find(PickingActivity.class).asList();
        else
        	return new ArrayList<PickingActivity>();
    }
	
}
