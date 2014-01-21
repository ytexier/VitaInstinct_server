package models.picking;

import org.mongodb.morphia.Key;

import models.Location;
import models.Plant;
import models.User;
import models.factory.FactorySector;

public class FactoryPickingSector extends FactorySector {

	@Override
	public PickingActivity createActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator) {
		return (new PickingActivity(new Plant(organism), amountOfOrganism, date, location, creator));
	}

	@Override
	public PickingEquipment createEquipment() {
		return (new PickingEquipment());
	}

	@Override
	public PickingAccessory createAccessory() {
		return (new PickingAccessory());
	}

	@Override
	public PickingEvent createEvent() {
		return (new PickingEvent());
	}

}
