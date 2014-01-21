package agents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;




import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;











import models.Mammal;
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
	
	OntModel jenaModel;
	
	private String db_activities = "db/dbActivities.rdf";
	
	public AgentJena(){
		try {
			jenaModel = createModel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public OntModel createModel() throws Exception {
		OntModel jenaModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM,null);
		jenaModel.setNsPrefix( "vita", Vita.NS );
		jenaModel.setNsPrefix( "owl", OWL.NS );
		jenaModel.setNsPrefix( "dc", DC.NS );
		return jenaModel;
	}
	
	/**
	 * USER
	 */
	
	@Override
	public void spy(User user) {

		/*
		Vita.rscUser = model.createResource(user.getURI())
		    .addProperty(VCARD.FN,user.getFullName())
			.addProperty(VCARD.Given,user.getGivenName())
			.addProperty(VCARD.Family,user.getFamilyName())
			.addProperty(VCARD.NICKNAME, user.getNickName())
			.addProperty(VCARD.EMAIL, user.getEmail());
		*/

	}
	
	/**
	 * ACTIVITY
	 */

	@Override
	public void spy(HuntingActivity activity) {
	
		User user = User.findById(activity.getCreator().getId().toString());
		Organism organism = activity.getOrganism();
		HuntingEvent event = activity.getEvent();
		ArrayList<HuntingEquipment> equipements = activity.getEquipments();
		
		Individual dataset = jenaModel
				.createIndividual(Vita.getURI()+"dbActivities", jenaModel.getOntClass(Vita.VitaClass.Dataset.getNS()));
		
		Vita.VitaClass.Activity.createOntClass(jenaModel);
		Vita.VitaClass.Sector.createOntClass(jenaModel);
		Vita.VitaClass.User.createOntClass(jenaModel);
		Vita.VitaClass.Organism.createOntClass(jenaModel);
		Vita.VitaClass.Equipment.createOntClass(jenaModel);
		Vita.VitaClass.Event.createOntClass(jenaModel);
		
		Individual _activity = jenaModel.createIndividual(Vita.VitaClass.Activity.getOntClass(jenaModel));
		
		dataset.addProperty(Vita.activity, _activity);
		
		//sector
		Individual _sector = jenaModel.createIndividual(Vita.getURI()+"sector", Vita.VitaClass.Sector.getOntClass(jenaModel));
		_sector.addLiteral(Vita.value,jenaModel.createTypedLiteral("hunting", XSDDatatype.XSDstring));
		
		//creator
		Individual _creator = jenaModel.createIndividual(Vita.getURI()+"user",Vita.VitaClass.User.getOntClass(jenaModel));		
		_creator.addProperty(VCARD.FN, user.getFullName())
			.addProperty(VCARD.Given, user.getGivenName())
			.addProperty(VCARD.Family, user.getFamilyName())
			.addProperty(VCARD.NICKNAME, user.getNickName())
			.addProperty(VCARD.EMAIL, user.getEmail());
		
		//organism
		Individual _organism = jenaModel.createIndividual(Vita.getURI()+"organism", Vita.VitaClass.Organism.getOntClass(jenaModel));		
		_organism.addLiteral(Vita.value,jenaModel.createTypedLiteral("osef", XSDDatatype.XSDstring));
		
		//organism
		Individual _location = jenaModel.createIndividual(Vita.getURI()+"location", Vita.VitaClass.Location.getOntClass(jenaModel));		
		_location.addLiteral(Vita.latitude,jenaModel.createTypedLiteral(activity.getLocation().getLatPos(),XSDDatatype.XSDstring));
		_location.addLiteral(Vita.longitude,jenaModel.createTypedLiteral(activity.getLocation().getLongPos(),XSDDatatype.XSDstring));
		
		//equipments
		Individual _equipment = jenaModel.createIndividual(Vita.getURI()+"equipment", Vita.VitaClass.Equipment.getOntClass(jenaModel));		
		_equipment.addLiteral(Vita.value,jenaModel.createTypedLiteral("osef", XSDDatatype.XSDstring));		
		
		
		
		_activity.addProperty(Vita.sector, _sector);
		_activity.addProperty(Vita.creator, _creator);
		_activity.addProperty(Vita.targetOrganism, _organism);
		_activity.addProperty(Vita.location, _location);
		_activity.addProperty(Vita.equipments, _equipment);
		
		
		this.writeRDF(jenaModel, db_activities);
		
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
		/*User user = huntingEvent.getCreator();
		Resource creator = jenaModel.createResource()
				.addProperty(DC.creator, user.getFullName());
		Individual comment = jenaModel.createIndividual()
				.addProperty(RDFS.label, jenaModel.createLiteral(huntingEvent.getLabel(), "en"));
		*/
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
	/*
	@Override
	public void spy(HuntingAccessory huntingAccessory) {
	}

	@Override
	public void spy(PickingAccessory pickingAccessory) {
	}

	@Override
	public void spy(FishingAccessory fishingAccessory) {
	}
	*/
	/**
	 * ORGANISM
	 */
	
	@Override
	public void spy(Organism organism) {

	}


	
	/**
	 * 
	 * @param model
	 */
	private void writeOutput(Model model, String db){
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
	private void writeRDF(Model model, String db){
		Model model_loaded = FileManager.get().loadModel(db);
		model = ModelFactory.createUnion(model_loaded, model);
		writeOutput(model, db);
	}
	
	//TODO
	//TODO
	/*
	import org.restlet.data.MediaType;
    
	public void write(OutputStream output) {
	        if (mediaType.equals(MediaType.APPLICATION_RDF_XML))
	            //jenaModel.write(output,"RDF/XML");  //this is faster
	            jenaModel.write(output,"RDF/XML-ABBREV");   //this is more readable 
	        else if (mediaType.equals(MediaType.APPLICATION_RDF_TURTLE))
	            jenaModel.write(output,"TURTLE");
	        else if (mediaType.equals(MediaType.TEXT_RDF_N3))
	            jenaModel.write(output,"N3");
	        else if (mediaType.equals(MediaType.TEXT_RDF_NTRIPLES))
	            jenaModel.write(output,"N-TRIPLE");    
	        else
	            jenaModel.write(output,"RDF/XML-ABBREV");    
	};
	*/
}
