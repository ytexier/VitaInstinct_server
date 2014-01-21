package models.factory;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class AbstractEvent {
	@Id 
	private ObjectId id;
}
