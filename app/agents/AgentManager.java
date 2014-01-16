package agents;

import models.User;
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
	
	public abstract String visitUser(User user);
	
	public abstract String visitActivity(HuntingActivity activity);
	public abstract String visitActivity(FishingActivity activity);
	public abstract String visitActivity(PickingActivity activity);
	
	public abstract String visitEquipment(HuntingEquipment equipment);
	public abstract String visitEquipment(FishingEquipment equipment);
	public abstract String visitEquipment(PickingEquipment equipment);
	
	public abstract String visitAccessory(HuntingAccessory accessory);
	public abstract String visitAccessory(FishingAccessory accessory);
	public abstract String visitAccessory(PickingAccessory accessory);
	
	public abstract String visitEvent(HuntingEvent event);
	public abstract String visitEvent(FishingEvent event);
	public abstract String visitEvent(PickingEvent event);

}
