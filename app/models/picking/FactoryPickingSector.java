package models.picking;

import models.factory.FactorySector;

public class FactoryPickingSector extends FactorySector {

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

	@Override
	public PickingEvent createEvent() {
		return (new PickingEvent());
	}

}
