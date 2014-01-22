package models.picking;

import org.mongodb.morphia.Key;

import models.Location;
import models.Plant;
import models.User;
import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryPickingSector extends FactorySector {

	@Override
	public PickingActivity createActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator) {
		return (new PickingActivity(new Plant(organism), amountOfOrganism, date, location, creator));
	}

	@Override
	public PickingEquipment createEquipment(String label, String comment, Key<User> creator) {
		return (new PickingEquipment(label, comment, creator));
	}

	@Override
	public PickingAccessory createAccessory(String label, String comment, Key<User> creator) {
		return (new PickingAccessory(label, comment, creator));
	}

	@Override
	public AbstractEvent createEvent(String date, String comment,
			Location location, Key<User> creator) {
		// TODO Auto-generated method stub
		return new PickingEvent(date, comment, location, creator);
	}


}
