package models.fishing;

import java.util.ArrayList;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import agents.AgentJena;
import agents.AgentManager;
import models.Location;
import models.User;
import models.factory.AbstractEvent;

@Entity
public class FishingEvent extends AbstractEvent {
	
	private Key<User> creator;
	
	public FishingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FishingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("fishing", label, date, comment, location, creator);
		super.setActivities(new ArrayList<FishingActivity>());
		// TODO Auto-generated constructor stub
	}
	
	public void addActivity(FishingActivity activity) {
		((ArrayList<FishingActivity>)super.getActivities()).add(activity);
	}

	public void accept(AgentManager v){
		v.spy(this);
	}

	@Override
	public void accept(AgentJena agentJena) {
		// TODO Auto-generated method stub
		
	}
}
