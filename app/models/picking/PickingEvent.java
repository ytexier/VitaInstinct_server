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
import models.hunting.HuntingActivity;


@Entity
public class PickingEvent extends AbstractEvent {
	
<<<<<<< HEAD
=======
	private ArrayList<PickingActivity> activities;
>>>>>>> dimql
	
	public PickingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PickingEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		super("picking", label, date, comment, location, creator);
		this.activities = new ArrayList<PickingActivity>();
		// TODO Auto-generated constructor stub
	}
	
	public void addActivity(PickingActivity activity) {
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
