package models.factory;

public abstract class FactorySector {
	public abstract AbstractActivity createActivity();
	public abstract AbstractEquipment createEquipment();
	public abstract AbstractEquipment createAccessory();
	public abstract AbstractEvent createEvent();
	//TODO
}
