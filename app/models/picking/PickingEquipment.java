package models.picking;

import agents.AgentManager;
import models.factory.AbstractEquipment;

public class PickingEquipment extends AbstractEquipment {
	public void accept(AgentManager v){
		v.visitEquipment(this);
	}	
}
