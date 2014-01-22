package models.hunting;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import agents.AgentJena;
import agents.AgentManager;
import models.User;
import models.factory.AbstractEquipment;

@Entity
public class HuntingEquipment extends AbstractEquipment {
	
	public HuntingEquipment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HuntingEquipment(String label, String comment,
			Key<User> creator) {
		super("hunting", label, comment, creator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AgentJena agentJena) {
		// TODO Auto-generated method stub
		
	}
}
