package models.picking;

import java.util.ArrayList;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import agents.AgentJena;
import agents.AgentManager;
import models.Location;
import models.User;
import models.factory.AbstractEvent;
import models.fishing.FishingActivity;


@Entity
public class PickingEvent extends AbstractEvent {
	
	
	public PickingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PickingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("picking", label, date, comment, location, creator);
		super.setActivities(new ArrayList<PickingActivity>());
		// TODO Auto-generated constructor stub
	}
	
	public void addActivity(PickingActivity activity) {
		((ArrayList<PickingActivity>)super.getActivities()).add(activity);
	}

	public void accept(AgentManager v){
		v.spy(this);
	}

	@Override
	public void accept(AgentJena agentJena) {
		// TODO Auto-generated method stub
		
	}	
}
