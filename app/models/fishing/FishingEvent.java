package models.fishing;

import org.mongodb.morphia.annotations.Entity;

import agents.AgentManager;
import models.factory.AbstractEvent;

@Entity
public class FishingEvent extends AbstractEvent {
	public void accept(AgentManager v){
		v.spy(this);
	}
}
