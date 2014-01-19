package agents;

import models.Amniote;
import models.Animal;
import models.Bird;
import models.Fish;
import models.Mammal;
import models.Organism;
import models.Plant;
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
	
	
	public abstract void spy(HuntingActivity huntingActivity);
	public abstract void spy(PickingActivity pickingActivity);
	public abstract void spy(FishingActivity fishingActivity);
	
	public abstract void spy(HuntingEquipment huntingEquipment);
	public abstract void spy(PickingEquipment pickingEquipment);
	public abstract void spy(FishingEquipment fishingEquipment);
	
	public abstract void spy(HuntingEvent huntingEvent);
	public abstract void spy(PickingEvent pickingEvent);
	public abstract void spy(FishingEvent fishingEvent);
	
	public abstract void spy(HuntingAccessory huntingAccessory);
	public abstract void spy(PickingAccessory pickingAccessory);
	public abstract void spy(FishingAccessory fishingAccessory);
	
	public abstract void spy(Organism organism);
	public abstract void spy(Animal annimal);
	public abstract void spy(Plant plant);
	public abstract void spy(Amniote amniote);
	public abstract void spy(Bird bird);
	public abstract void spy(Fish fish);
	public abstract void spy(Mammal mammal);
	
}
