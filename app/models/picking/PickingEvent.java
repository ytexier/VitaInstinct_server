package models.picking;

import agents.AgentManager;
import models.factory.AbstractEvent;



public class PickingEvent extends AbstractEvent {
	public void accept(AgentManager v){
		v.visitEvent(this);
	}	
}
