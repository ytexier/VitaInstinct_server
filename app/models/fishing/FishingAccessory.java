package models.fishing;

import agents.AgentManager;
import models.factory.AbstractAccessory;

public class FishingAccessory extends AbstractAccessory{
	public void accept(AgentManager v){
		v.spy(this);
	}
}
