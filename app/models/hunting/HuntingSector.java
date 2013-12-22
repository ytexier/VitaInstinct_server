package models.hunting;

import models.factory.AbstractSector;

public class HuntingSector extends AbstractSector{

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
}
