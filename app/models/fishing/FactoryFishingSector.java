package models.fishing;

import org.mongodb.morphia.Key;

import models.Fish;
import models.Location;
import models.User;
import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryFishingSector extends FactorySector{

	@Override
	public FishingActivity createActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator) {
		return (new FishingActivity(new Fish(organism), amountOfOrganism, date, location, creator));
	}

	@Override
	public FishingEquipment createEquipment() {
		return (new FishingEquipment());
	}

	@Override
	public FishingAccessory createAccessory() {
		return (new FishingAccessory());
	}

	@Override
	public AbstractEvent createEvent() {
		return (new FishingEvent());
	}

	

}
