package models.factory;

public abstract class FactorySector {
	public abstract AbstractActivity createActivity();
	public abstract AbstractEquipment createEquipment();
	public abstract AbstractAccessory createAccessory();
	public abstract AbstractEvent createEvent();
	//TODO
}
