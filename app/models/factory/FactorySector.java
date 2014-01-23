package models.factory;

import org.mongodb.morphia.Key;

import models.Location;
import models.User;

public abstract class FactorySector {
	public abstract AbstractActivity createActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment);
	public abstract AbstractEquipment createEquipment(String label, String comment, Key<User> creator);
	public abstract AbstractEquipment createAccessory(String label, String comment,	Key<User> creator);
	public abstract AbstractEvent createEvent(String label, String date, String comment, Location location, Key<User> creator);
}
