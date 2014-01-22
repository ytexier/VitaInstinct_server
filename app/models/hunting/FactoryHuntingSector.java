package models.hunting;

import org.mongodb.morphia.Key;

import models.Location;
import models.Mammal;
import models.User;
import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryHuntingSector extends FactorySector{

	@Override
	public HuntingActivity createActivity(String organism, int amountOfOrganism, String date, Location location, Key<User> creator) {
		return (new HuntingActivity(new Mammal(organism), amountOfOrganism, date, location, creator));
	}

	@Override
	public HuntingEquipment createEquipment() {
		return (new HuntingEquipment());
	}

	@Override
	public HuntingAccessory createAccessory() {
		return (new HuntingAccessory());
	}

	@Override
	public AbstractEvent createEvent(String date, String comment,
			Location location) {
		// TODO Auto-generated method stub
		return new HuntingEvent(date, comment, location);
	}

}
