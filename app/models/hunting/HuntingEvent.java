package models.hunting;

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
public class HuntingEvent extends AbstractEvent {
	
	
	
	public HuntingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("hunting", label, date, comment, location, creator);
		super.setActivities(new ArrayList<HuntingActivity>());
		// TODO Auto-generated constructor stub
	}
	
	public HuntingEvent() {
		// TODO Auto-generated constructor stub
	}

	public void addActivity(HuntingActivity activity) {
		((ArrayList<HuntingActivity>)super.getActivities()).add(activity);
	}

	@Override
	public void accept(AgentJena agentJena) {
		// TODO Auto-generated method stub
		
	}
}
