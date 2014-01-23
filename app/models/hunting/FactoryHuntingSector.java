package models.hunting;

import org.mongodb.morphia.Key;

import models.Location;
import models.User;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryHuntingSector extends FactorySector{

	@Override
	public HuntingActivity createActivity(String organism, String thumbnail, String _abstract,
			 int amountOfOrganism, String date, Location location, Key<User> creator, 
			AbstractEvent event, AbstractEquipment equipment) {
		return new HuntingActivity(organism, thumbnail, _abstract, amountOfOrganism, date, location, creator, event, equipment);
	}

	@Override
	public HuntingEquipment createEquipment(String label, String comment, Key<User> creator) {
		return new HuntingEquipment(label, comment, creator);
	}

	@Override
	public HuntingAccessory createAccessory(String label, String comment, Key<User> creator) {
		return (new HuntingAccessory(label, comment, creator));
	}

	@Override
	public AbstractEvent createEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		// TODO Auto-generated method stub
		return new HuntingEvent(label, date, comment, location, creator);
	}

}
