package models.hunting;

import java.util.ArrayList;
import java.util.Date;

import models.ActivityEnding;
import models.Amniote;
import models.Fish;
import models.Location;
import models.Mammal;
import models.Organism;
import models.User;
import models.factory.AbstractActivity;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;
import models.fishing.FishingEquipment;
import models.fishing.FishingEvent;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentManager;

@Entity
public class HuntingActivity extends AbstractActivity{
	
	@Embedded
	private Mammal organism;
	@Embedded
	private HuntingEvent event;
	@Embedded
	private ArrayList<HuntingEquipment> equipments;
	
	public HuntingActivity(Mammal mammal, int amountOfOrganism, String date, Location location, Key<User> creator){
		
		organism = mammal;
		equipments = new ArrayList<HuntingEquipment>();
		event = new HuntingEvent();
		super.setCreator(creator);
		super.setAmountOfOrganism(amountOfOrganism);
		super.setLocation(location);
		super.setSector("hunting");
		super.setDate(date);
	}
	


	@Override
	public void accept(AgentJena agent) {
		agent.spy(this);
	}

	
    public static HuntingActivity findById(String id){
    	HuntingActivity activity = MorphiaObject.datastore.find(HuntingActivity.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return activity;
    }
    
    public Mammal getOrganism(){
    	return organism;
    }
    
	public void setOrganism(Mammal mammal){
		super.setOrganism(mammal);
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


	public ArrayList<HuntingEquipment> getEquipments() {
		return equipments;
	}


	public void setEquipments(ArrayList<HuntingEquipment> equipments) {
		this.equipments = equipments;
	}



	

}
