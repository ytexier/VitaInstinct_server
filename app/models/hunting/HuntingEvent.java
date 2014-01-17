package models.hunting;

import agents.AgentManager;
import models.factory.AbstractEvent;

public class HuntingEvent extends AbstractEvent {
	public void accept(AgentManager v){
		v.spy(this);
	}
}
