package agents;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.VCARD;

import play.Logger;


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

public class AgentJena extends AgentManager{

	public static Model model = ModelFactory.createDefaultModel();
	
	/**
	 * USER
	 *
	 */
	
	
	@Override
	public void spy(User user) {
		Resource user_rsc= model.createResource(user.getURI())
		    .addProperty(VCARD.FN,user.getFullName())
			.addProperty(VCARD.Given,user.getGivenName())
			.addProperty(VCARD.Family,user.getFamilyName())
			.addProperty(VCARD.NICKNAME, user.getNickName())
			.addProperty(VCARD.EMAIL, user.getEmail());
		
		printStatements();
		//Vita.model.write(System.out, "RDF/XML-ABBREV");
	}
	
	/**
	 * ACTIVITY
	 */

	@Override
	public void spy(HuntingActivity huntingActivity) {
	}

	@Override
	public void spy(PickingActivity pickingActivity) {
	}

	@Override
	public void spy(FishingActivity fishingActivity) {
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
	public void spy(Organism organism) {
	}

	@Override
	public void spy(Animal annimal) {
	}

	@Override
	public void spy(Plant plant) {
	}

	@Override
	public void spy(Amniote amniote) {
	}

	@Override
	public void spy(Bird bird) {
	}

	@Override
	public void spy(Fish fish) {
	}

	@Override
	public void spy(Mammal mammal) {
	}
	
   
	
	public static void printStatements(){
		
		Logger.debug("[[[[PRINT STATEMENTS]]]]");
		
		// list the statements in the Model
		StmtIterator iter = model.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) 
		{
			Statement stmt      = iter.nextStatement();  // get next statement
			Resource  subject   = stmt.getSubject();     // get the subject
			Property  predicate = stmt.getPredicate();   // get the predicate
			RDFNode   object    = stmt.getObject();      // get the object

			Logger.debug(subject.toString());
			Logger.debug(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				Logger.debug(object.toString());
			} else {
				// object is a literal
				Logger.debug(" \"" + object.toString() + "\"");
			}

			Logger.debug(" .");
		} 
	}


}
