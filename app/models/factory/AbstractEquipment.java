package models.factory;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;


public abstract class AbstractEquipment {
	@Id 
	private ObjectId id;
}
