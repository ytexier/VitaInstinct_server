package models.hunting;

import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryHuntingSector extends FactorySector{

	@Override
	public HuntingActivity createActivity() {
		return (new HuntingActivity());
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
	public AbstractEvent createEvent() {
		return (new HuntingEvent());
	}
}
