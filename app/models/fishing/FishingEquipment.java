package models.fishing;

import agents.AgentManager;
import models.factory.AbstractEquipment;

public class FishingEquipment extends AbstractEquipment {
	public void accept(AgentManager v){
		v.spy(this);
	}
}
