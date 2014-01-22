package models.fishing;

import models.User;
import models.factory.AbstractEquipment;
import models.picking.PickingEquipment;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
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
	
    public static FishingEquipment findById(String id){
    	FishingEquipment equipment = MorphiaObject.datastore.find(FishingEquipment.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return equipment;
    }
}
