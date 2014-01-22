package models.hunting;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import agents.AgentJena;
import agents.AgentManager;
import models.Location;
import models.User;
import models.factory.AbstractEvent;

@Entity
public class HuntingEvent extends AbstractEvent {
	
	
	
	public HuntingEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HuntingEvent(String date, String comment,
			Location location, Key<User> creator) {
		super("hunting", date, comment, location, creator);
		// TODO Auto-generated constructor stub
	}

	public void accept(AgentManager v){
		v.spy(this);
	}

	@Override
	public void accept(AgentJena agentJena) {
		// TODO Auto-generated method stub
		
	}
}
