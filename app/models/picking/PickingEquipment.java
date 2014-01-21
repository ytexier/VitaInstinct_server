package models.picking;

import org.mongodb.morphia.annotations.Entity;

import agents.AgentManager;
import models.factory.AbstractEquipment;

@Entity
public class PickingEquipment extends AbstractEquipment {
	public void accept(AgentManager v){
		v.spy(this);
	}	
}
