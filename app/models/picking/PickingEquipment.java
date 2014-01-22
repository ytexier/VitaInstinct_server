package models.picking;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentManager;
import models.User;
import models.factory.AbstractEquipment;
import models.hunting.HuntingEquipment;

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

    public static PickingEquipment findById(String id){
    	PickingEquipment equipment = MorphiaObject.datastore.find(PickingEquipment.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return equipment;
    }
	
}
