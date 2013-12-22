package models.fishing;

import models.factory.AbstractSector;

public class FishingSector extends AbstractSector{

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

	

}
