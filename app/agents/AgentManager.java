package agents;


import com.hp.hpl.jena.rdf.model.Model;

import models.Organism;
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
	
	public abstract Model spy(User user);
	
	public abstract Model spy(HuntingActivity activity);
	public abstract Model spy(PickingActivity activity);
	public abstract Model spy(FishingActivity activity);
	
	public abstract Model spy(HuntingEquipment equipment);
	public abstract Model spy(PickingEquipment equipment);
	public abstract Model spy(FishingEquipment equipment);
	
	public abstract Model spy(HuntingEvent event);
	public abstract Model spy(PickingEvent event);
	public abstract Model spy(FishingEvent event);
	
	//next time
	//public abstract void spy(HuntingAccessory huntingAccessory);
	//public abstract void spy(PickingAccessory pickingAccessory);
	//public abstract void spy(FishingAccessory fishingAccessory);
	
	public abstract Model spy(Organism organism);

	
}
