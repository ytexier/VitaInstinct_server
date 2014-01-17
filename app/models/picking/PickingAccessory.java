package models.picking;


import agents.AgentManager;
import models.factory.AbstractAccessory;

public class PickingAccessory extends AbstractAccessory {

	public void accept(AgentManager v){
		v.spy(this);
	}
	
	
}
