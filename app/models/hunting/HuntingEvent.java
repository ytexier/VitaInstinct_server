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
	
	private ArrayList<HuntingActivity> activities;
	
	public HuntingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HuntingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("hunting", label, date, comment, location, creator);
		this.activities = new ArrayList<HuntingActivity>();
		// TODO Auto-generated constructor stub
	}
	
	public void addActivity(HuntingActivity activity) {
		this.activities.add(activity);
	}

	public void accept(AgentManager v){
		v.spy(this);
	}

	@Override
	public void accept(AgentJena agentJena) {
		// TODO Auto-generated method stub
		
	}
}
