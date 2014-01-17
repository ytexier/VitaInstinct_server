package models.hunting;

import agents.AgentManager;
import models.factory.AbstractAccessory;

public class HuntingAccessory extends AbstractAccessory{
	public void accept(AgentManager v){
		v.spy(this);
	}
}
