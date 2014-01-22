package models.picking;


import models.User;

import org.mongodb.morphia.Key;

import agents.AgentManager;

public class PickingAccessory extends PickingEquipment{
	
	public PickingAccessory(String label, String comment, Key<User> creator) {
		super(label, comment, creator);
		// TODO Auto-generated constructor stub
	}

}
