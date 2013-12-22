package models.picking;

import models.factory.AbstractSector;

public class PickingSector extends AbstractSector {

	@Override
	public PickingActivity createActivity() {
		return (new PickingActivity());
	}

	@Override
	public PickingEquipment createEquipment() {
		return (new PickingEquipment());
	}

	@Override
	public PickingAccessory createAccessory() {
		return (new PickingAccessory());
	}

}
