package models.fishing;

import org.mongodb.morphia.Key;

import models.Location;
import models.User;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryFishingSector extends FactorySector{

	@Override
	public FishingActivity createActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator, AbstractEvent event, AbstractEquipment equipment) {
		return new FishingActivity(organism, amountOfOrganism, date, location, creator, event, equipment);
	}

	@Override
	public AbstractEquipment createEquipment(String label, String comment,
			Key<User> creator) {
		// TODO Auto-generated method stub
		return new FishingEquipment(label, comment, creator);
	}

	@Override
	public FishingAccessory createAccessory(String label, String comment,
			Key<User> creator) {
		return new FishingAccessory(label, comment, creator);
	}

	@Override
	public AbstractEvent createEvent(String label, String date, String comment,
			Location location, Key<User> creator) {
		// TODO Auto-generated method stub
		return new FishingEvent(label, date, comment, location, creator);
	}


	

	

}
