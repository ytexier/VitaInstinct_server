package models.factory;

import javax.validation.constraints.NotNull;

import org.apache.xerces.util.URI;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public abstract class AbstractEquipment {
	@Id
	protected ObjectId id;
	@NotNull
	protected String name;
	protected URI picture_uri;
	protected String features;
}
