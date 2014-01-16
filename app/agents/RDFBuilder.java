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

public class RDFBuilder extends AgentManager {

    public String rdf = "";
    public String url = "";
    
	@Override
	public String visitUser(User user){
		
        rdf += 	"<sioc:UserAccount rdf:about=\""
        			+url+"\""+user.getPseudo()
        			+"?id="+user.getId().toString()
        			+"\">"
        			
	        		+	"<sioc:email>"+user.getEmail()+"</sioc:email>"
	        		+ 	"<rdfs:label>"+user.getPseudo()+"</rdfs:label>"
	        		+	"<dcterms:created>"+user.getRegistration().toString()+"</dcterms:created>"
        		+ "</sioc:UserAccount>";	
	
		return rdf;
	}
	
	
	@Override
	public String visitActivity(HuntingActivity activity) {
		
		return rdf;
	}

	@Override
	public String visitActivity(FishingActivity activity) {
		return rdf;
	}

	@Override
	public String visitActivity(PickingActivity activity) {
		return rdf;
	}
	
	
	
	

	@Override
	public String visitEquipment(HuntingEquipment equipment) {
		return rdf;
	}

	@Override
	public String visitEquipment(FishingEquipment equipment) {
		return rdf;		
	}

	@Override
	public String visitEquipment(PickingEquipment equipment) {
		return rdf;		
	}

	@Override
	public String visitAccessory(HuntingAccessory accessory) {
		return rdf;		
	}

	@Override
	public String visitAccessory(FishingAccessory accessory) {
		return rdf;		
	}

	@Override
	public String visitAccessory(PickingAccessory accessory) {
		return rdf;		
	}

	@Override
	public String visitEvent(HuntingEvent event) {
		return rdf;		
	}

	@Override
	public String visitEvent(FishingEvent event) {
		return rdf;		
	}

	@Override
	public String visitEvent(PickingEvent event) {
		return rdf;		
	}



}
