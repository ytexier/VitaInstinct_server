package models.factory;

public abstract class AbstractSector {
	public abstract AbstractActivity createActivity();
	public abstract AbstractEquipment createEquipment();
	public abstract AbstractAccessory createAccessory();
}
