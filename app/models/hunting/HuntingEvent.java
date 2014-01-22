package models.hunting;

import java.util.ArrayList;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import agents.AgentJena;
import models.Location;
import models.User;
import models.factory.AbstractEvent;
import models.fishing.FishingActivity;

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


	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	
	public ArrayList<HuntingActivity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<HuntingActivity> activities) {
		this.activities = activities;
	}
}
