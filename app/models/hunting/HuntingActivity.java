package models.hunting;

import java.util.ArrayList;

import models.ActivityEnding;


import models.Location;
import models.Mammal;
import models.Organism;
import models.User;
import models.Vita;
import models.factory.AbstractActivity;






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
	
	@Embedded
	private HuntingEvent event;

	
	public HuntingActivity(){

	}
	
	public HuntingActivity(Mammal organism, int amountOfOrganism, String date, Location location, Key<User> creator){
		super(organism, creator, amountOfOrganism, location, "hunting", date);	
		super.setEquipment(new ArrayList<HuntingEquipment>());
		event = new HuntingEvent();
	}
	
	public String getURL(){
		return Vita.getURL()+"sector/hunting/activity/";
	}
	
	public Organism getOrganism(){
		return super.getOrganism();
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



	

}
