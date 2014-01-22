package models.fishing;

import models.User;
import models.factory.AbstractEquipment;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import agents.AgentJena;

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
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
}
