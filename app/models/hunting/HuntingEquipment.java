package models.hunting;

import org.mongodb.morphia.annotations.Entity;

import agents.AgentManager;
import models.factory.AbstractEquipment;

@Entity
public class HuntingEquipment extends AbstractEquipment {
	public void accept(AgentManager v){
		v.spy(this);
	}
}
