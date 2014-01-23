package models.picking;

import org.mongodb.morphia.Key;

import models.Location;
import models.User;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryPickingSector extends FactorySector {

	@Override
	public PickingActivity createActivity(String organism, String thumbnail, String _abstract, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment) {
		return new PickingActivity(organism, thumbnail, _abstract,amountOfOrganism, date, location, creator, event, equipment);
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
	public AbstractEvent createEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		// TODO Auto-generated method stub
		return new PickingEvent(label, date, comment, location, creator);
	}


}
