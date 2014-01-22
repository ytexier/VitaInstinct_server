package agents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.mongodb.morphia.Key;

import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import models.Location;
import models.Organism;
import models.User;
import models.Vita;
import models.factory.AbstractEquipment;
import models.fishing.FishingActivity;
import models.fishing.FishingEquipment;
import models.fishing.FishingEvent;
import models.hunting.HuntingActivity;
import models.hunting.HuntingEquipment;
import models.hunting.HuntingEvent;
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
		model = ModelFactory.createDefaultModel();
		
		Vita.rscUser = model.createResource(user.getURI())
		    .addProperty(VCARD.FN,user.getFullName())
			.addProperty(VCARD.Given,user.getGivenName())
			.addProperty(VCARD.Family,user.getFamilyName())
			.addProperty(VCARD.NICKNAME, user.getNickName())
			.addProperty(VCARD.EMAIL, user.getEmail());
		
		return model;
		*/
	}

	
	
	/**
	 * ACTIVITY
	 */

	@Override
	public Model spy(HuntingActivity activity) {
	
		String 			idActivity = activity.getId().toString();	
		User 			creator = User.findById(activity.getCreator().getId().toString());
		Organism 		organism = activity.getOrganism();
		String 			label = activity.getLabel();
		HuntingEvent	event = activity.getEvent();

		//Resource Activity
		Resource _activity = jenaModel.createResource(activity.getURI());
	
		//Organism : targetOrganism
		Vita.VitaClass.Organism.createOntClass(jenaModel);
		Individual _organism = jenaModel.createIndividual(organism.getURI(), Vita.VitaClass.Organism.getOntClass(jenaModel));
		
		//Location : location
		Vita.VitaClass.Location.createOntClass(jenaModel);
		Individual _location = jenaModel.createIndividual(Vita.getURL()+"location/"+idActivity, Vita.VitaClass.Location.getOntClass(jenaModel));		
		_location.addLiteral(Vita.latitude,jenaModel.createTypedLiteral(activity.getLocation().getLatPos(),XSDDatatype.XSDstring));
		_location.addLiteral(Vita.longitude,jenaModel.createTypedLiteral(activity.getLocation().getLongPos(),XSDDatatype.XSDstring));
	
		//List Equipment : equipments
		Vita.VitaClass.Equipment.createOntClass(jenaModel);
		ArrayList<Individual> _equipments = new ArrayList<Individual>();
		//for(AbstractEquipment equipment : activity.getEquipments())
			//_equipments.add(jenaModel.createIndividual(equipment.getURI(), Vita.VitaClass.Equipment.getOntClass(jenaModel)));		
		
		//Event : isRelatedTo
		Vita.VitaClass.Event.createOntClass(jenaModel);
		Individual _event = jenaModel.createIndividual(event.getURI(), Vita.VitaClass.Organism.getOntClass(jenaModel));	
		
		//Add Properties
		_activity.addProperty(RDFS.label, label);
		_activity.addProperty(DC.creator, creator.getFullName());
		_activity.addProperty(Vita.targetOrganism, _organism);
		_activity.addProperty(Vita.location, _location);
		for(Individual e : _equipments)
			_activity.addProperty(Vita.equipments, e);
		_activity.addProperty(Vita.isRelatedTo, _event);
		
		this.writeRDF(jenaModel, db_activities);
	
		return jenaModel;
	
	}



	@Override
	public Model spy(PickingActivity activity) {
		return jenaModel;
	}

	@Override
	public Model spy(FishingActivity activity) {
		return jenaModel;
	}
	
	/**
	 * EQUIPMENT
	 */

	@Override
	public Model spy(HuntingEquipment equipment) {
		
		String 			idEquipment = equipment.getId().toString();	
		User 			creator = User.findById(equipment.getCreator().getId().toString());
		String 			label = equipment.getLabel();
		String 			comment = equipment.getComment();
		
		//Resource Equipment
		Resource _equipment = jenaModel.createResource(equipment.getURI());
		
		//Add Properties
		_equipment.addProperty(RDFS.label, label);
		_equipment.addProperty(DC.creator, creator.getFullName());
		_equipment.addProperty(RDFS.comment, comment);	
	
		return jenaModel;
	}

	@Override
	public Model spy(PickingEquipment equipment) {
		return jenaModel;
	}

	@Override
	public Model spy(FishingEquipment equipment) {
		return jenaModel;
	}
	
	/**
	 * EVENT
	 */

	@Override
	public Model spy(HuntingEvent event) {
	
		String		idEvent = event.getId().toString();
		User 		creator = User.findById(event.getCreator().getId().toString());
		String 		label = event.getLabel();
		String 		comment = event.getComment();
		String 		date = event.getDate();
		Location	location = event.getLocation();
		
		ArrayList<HuntingActivity> 	activities = event.getActivities();
		ArrayList<Key<User>> 			registers = event.getRegisters();	
		
		//Resource Event
		Resource _event = jenaModel.createResource(event.getURI());
		
		//Location
		Vita.VitaClass.Location.createOntClass(jenaModel);
		Individual _location = jenaModel.createIndividual(event.getURI()+"/location", Vita.VitaClass.Location.getOntClass(jenaModel));		
		_location.addLiteral(Vita.latitude,jenaModel.createTypedLiteral(event.getLocation().getLatPos(),XSDDatatype.XSDstring));
		_location.addLiteral(Vita.longitude,jenaModel.createTypedLiteral(event.getLocation().getLongPos(),XSDDatatype.XSDstring));
	
		//Activities
		ArrayList<Individual> _activities = new ArrayList<Individual>();
		for(HuntingActivity activity : activities)
			_activities.add(jenaModel.createIndividual(Vita.getURL()+"activity/"+activity.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Registers
		ArrayList<Individual> _registers = new ArrayList<Individual>();
		for(Key<User> register : registers)
			_registers.add(jenaModel.createIndividual(Vita.getURL()+"user/"+register.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Add properties
		_event.addProperty(RDFS.label,label);
		_event.addProperty(DC.creator, creator.getFullName());
		_event.addProperty(RDFS.comment, comment);	
		for(Individual e : _activities)
			_event.addProperty(Vita.activities, e);
		for(Individual r : _registers)
			_event.addProperty(Vita.registers, r);
		
		this.writeRDF(jenaModel, db_activities);
		
		return jenaModel;
	}

	@Override
	public Model spy(PickingEvent event) {
		return jenaModel;
	}

	@Override
	public Model spy(FishingEvent event) {
		return jenaModel;
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
	public Model spy(Organism organism) {
		return jenaModel;
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


	

    /*
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
