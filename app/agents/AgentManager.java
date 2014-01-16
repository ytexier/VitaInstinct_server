package agents;

import models.fishing.FishingAccessory;
import models.fishing.FishingActivity;
import models.fishing.FishingEquipment;
import models.fishing.FishingEvent;
import models.hunting.HuntingAccessory;
import models.hunting.HuntingActivity;
import models.hunting.HuntingEquipment;
import models.hunting.HuntingEvent;
import models.picking.PickingAccessory;
import models.picking.PickingActivity;
import models.picking.PickingEquipment;
import models.picking.PickingEvent;

public abstract class AgentManager {
	public abstract void visitActivity(HuntingActivity activity);
	public abstract void visitActivity(FishingActivity activity);
	public abstract void visitActivity(PickingActivity activity);
	
	public abstract void visitEquipment(HuntingEquipment equipment);
	public abstract void visitEquipment(FishingEquipment equipment);
	public abstract void visitEquipment(PickingEquipment equipment);
	
	public abstract void visitAccessory(HuntingAccessory accessory);
	public abstract void visitAccessory(FishingAccessory accessory);
	public abstract void visitAccessory(PickingAccessory accessory);
	
	public abstract void visitEvent(HuntingEvent event);
	public abstract void visitEvent(FishingEvent event);
	public abstract void visitEvent(PickingEvent event);

}
