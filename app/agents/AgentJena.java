package agents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;





import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;


import models.Organism;
import models.User;
import models.Vita;
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

public class AgentJena extends AgentManager{
	
	private String db = "db/vita.rdf";
	private Model model;

	/**
	 * USER
	 *
	 */
	
	@Override
	public Model spy(User user) {
		model = ModelFactory.createDefaultModel();
		
		Vita.rscUser = model.createResource(user.getURI())
		    .addProperty(VCARD.FN,user.getFullName())
			.addProperty(VCARD.Given,user.getGivenName())
			.addProperty(VCARD.Family,user.getFamilyName())
			.addProperty(VCARD.NICKNAME, user.getNickName())
			.addProperty(VCARD.EMAIL, user.getEmail());
		
		return model;
	}
	
	/**
	 * ACTIVITY
	 */

	@Override
	public void spy(HuntingActivity huntingActivity) {

		//TODO
		///TESTTTT
		Model model_activity = ModelFactory.createDefaultModel();
		Model model_creator = User.findById(huntingActivity.getCreator().getId().toString())
				.accept(new AgentJena());
		Model model_organism = huntingActivity.getOrganism()
				.accept(new AgentJena());
 		model = ModelFactory.createUnion(model_creator, model_organism);
		this.writeRDF(model);
		///TESTTTT
		
	}

	@Override
	public void spy(PickingActivity pickingActivity) {
		
		model = ModelFactory.createDefaultModel();
		
		///TODO
		
		this.writeRDF(model);
		
	}

	@Override
	public void spy(FishingActivity fishingActivity) {
		
		model = ModelFactory.createDefaultModel();
		
		///TODO
		
		this.writeRDF(model);
		
	}
	
	/**
	 * EQUIPMENT
	 */

	@Override
	public void spy(HuntingEquipment huntingEquipment) {
	}

	@Override
	public void spy(PickingEquipment pickingEquipment) {
	}

	@Override
	public void spy(FishingEquipment fishingEquipment) {
	}
	
	/**
	 * EVENT
	 */

	@Override
	public void spy(HuntingEvent huntingEvent) {
	}

	@Override
	public void spy(PickingEvent pickingEvent) {
	}

	@Override
	public void spy(FishingEvent fishingEvent) {
	}

	/**
	 * ACCESSORY
	 */
	
	@Override
	public void spy(HuntingAccessory huntingAccessory) {
	}

	@Override
	public void spy(PickingAccessory pickingAccessory) {
	}

	@Override
	public void spy(FishingAccessory fishingAccessory) {
	}

	/**
	 * ORGANISM
	 */
	
	@Override
	public Model spy(Organism organism) {
		model = ModelFactory.createDefaultModel();
		
		Vita.rscUser = model.createResource(organism.getURI())
				.addProperty(VCARD.NICKNAME, organism.getSpecie());
		
		
		return model;
	}


	
	/**
	 * 
	 * @param model
	 */
	private void writeOutput(Model model){
		OutputStream os = null;
        try {
			os = new FileOutputStream(db);
			RDFDataMgr.write(os, model, RDFFormat.RDFXML_ABBREV) ;
			os.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param model 
	 */
	private void writeRDF(Model model){
		Model model_loaded = FileManager.get().loadModel(db);
		model = ModelFactory.createUnion(model_loaded, model);
		writeOutput(model);
	}

}
