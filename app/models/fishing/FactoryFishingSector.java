package models.fishing;

import models.factory.AbstractEvent;
import models.factory.FactorySector;

public class FactoryFishingSector extends FactorySector{

	@Override
	public FishingActivity createActivity() {
		return (new FishingActivity());
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
