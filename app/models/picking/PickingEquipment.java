package models.picking;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import agents.AgentJena;
import agents.AgentManager;
import models.User;
import models.factory.AbstractEquipment;

@Entity
public class PickingEquipment extends AbstractEquipment {

	public PickingEquipment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PickingEquipment(String label, String comment,
			Key<User> creator) {
		super("picking", label, comment, creator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}

	
}
