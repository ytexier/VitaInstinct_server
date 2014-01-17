package models.fishing;

import agents.AgentManager;
import models.factory.AbstractEvent;

public class FishingEvent extends AbstractEvent {
	public void accept(AgentManager v){
		v.spy(this);
	}
}
