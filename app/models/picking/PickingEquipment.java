package models.picking;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;

import com.hp.hpl.jena.rdf.model.Model;

import controllers.MorphiaObject;
import agents.AgentJena;
import agents.AgentWriter;
import models.User;
import models.factory.AbstractEquipment;

@Entity
public class PickingEquipment extends AbstractEquipment {
	
	@Override
	public Model accept(AgentJena agent) {
		return agent.spy(this);
	}
	@Override
	public Model accept(AgentWriter agent) {
		return agent.spy(this);
	}
	
	public PickingEquipment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PickingEquipment(String label, String comment,
			Key<User> creator) {
		super("picking", label, comment, creator);
		// TODO Auto-generated constructor stub
	}

    public static PickingEquipment findById(String id){
    	PickingEquipment equipment = MorphiaObject.datastore.find(PickingEquipment.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return equipment;
    }
    
	public static List<PickingEquipment> all() throws Exception{
        if (MorphiaObject.datastore != null)
                return MorphiaObject.datastore
                		.find(PickingEquipment.class).asList();
        else
        	return new ArrayList<PickingEquipment>();
    }
    

}
