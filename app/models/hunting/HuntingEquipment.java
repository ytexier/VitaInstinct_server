package models.hunting;

import agents.AgentManager;
import models.factory.AbstractEquipment;

public class HuntingEquipment extends AbstractEquipment {
	public void accept(AgentManager v){
		v.spy(this);
	}
}
