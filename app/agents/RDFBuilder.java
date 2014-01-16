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
		
		rdf += getHeader();
		
        rdf += 	"<sioc:UserAccount rdf:about=\""
        			+url+"\""+user.getUsername()
        			+"?id="+user.getId().toString()
        			+"\">"
        			
	        		+	"<sioc:email>"+user.getEmail()+"</sioc:email>"
	        		+ 	"<rdfs:label>"+user.getUsername()+"</rdfs:label>"
	        		+	"<dcterms:created>"+user.getRegistration().toString()+"</dcterms:created>"
        		+ "</sioc:UserAccount>";	
        
        rdf += "</rdf:RDF>";
	
		return rdf;
	}
	
	
	@Override
	public String visitActivity(HuntingActivity activity) {
		
		rdf += getHeader();
		
		
		
		
		return rdf;
	}

	@Override
	public String visitActivity(FishingActivity activity) {
		
		rdf += getHeader();
		return rdf;
	}

	@Override
	public String visitActivity(PickingActivity activity) {
		
		rdf += getHeader();
		return rdf;
	}
	
	
	
	

	@Override
	public String visitEquipment(HuntingEquipment equipment) {
		
		rdf += getHeader();
		return rdf;
	}

	@Override
	public String visitEquipment(FishingEquipment equipment) {
		
		rdf += getHeader();
		return rdf;		
	}

	@Override
	public String visitEquipment(PickingEquipment equipment) {
		
		rdf += getHeader();
		return rdf;		
	}

	@Override
	public String visitAccessory(HuntingAccessory accessory) {
		
		rdf += getHeader();
		return rdf;		
	}

	@Override
	public String visitAccessory(FishingAccessory accessory) {
		
		rdf += getHeader();
		return rdf;		
	}

	@Override
	public String visitAccessory(PickingAccessory accessory) {
		
		rdf += getHeader();
		return rdf;		
	}

	@Override
	public String visitEvent(HuntingEvent event) {
		
		rdf += getHeader();
		return rdf;		
	}

	@Override
	public String visitEvent(FishingEvent event) {
		
		rdf += getHeader();
		return rdf;		
	}

	@Override
	public String visitEvent(PickingEvent event) {
		
		rdf += getHeader();
		return rdf;		
	}

	
	public String getHeader(){
		String rdf_header="<rdf:RDF "
				+ "xmlns:foaf=\"http://xmlns.com/foaf/0.1/\""
				+ "xmlns:dcterms=\"http://purl.org/dc/terms/\""
				+ "xmlns:sioc=\"http://rdfs.org/sioc/ns#\""
				+ "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
				+ "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\""
				+ ">";
		return rdf_header;
	}


}
