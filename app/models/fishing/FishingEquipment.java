package models.fishing;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import agents.AgentJena;
import agents.AgentManager;
import models.User;
import models.factory.AbstractEquipment;

@Entity
public class FishingEquipment extends AbstractEquipment {
	
	public FishingEquipment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FishingEquipment(String label, String comment,
			Key<User> creator) {
		super("fishing", label, comment, creator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AgentJena agentJena) {
		// TODO Auto-generated method stub
		
	}
}
