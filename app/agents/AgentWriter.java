package agents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import models.Amniote;
import models.Location;
import models.Organism;
import models.User;
import models.Vita;
import models.fishing.FishingActivity;
import models.fishing.FishingEquipment;
import models.fishing.FishingEvent;
import models.hunting.HuntingActivity;
import models.hunting.HuntingEquipment;
import models.hunting.HuntingEvent;
import models.picking.PickingActivity;
import models.picking.PickingEquipment;
import models.picking.PickingEvent;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

public class AgentWriter extends AgentManager{
	
	public static String db_users 		= "db/user/db_users.rdf";
	public static String db_hunting_eq 	= "db/hunting/db_hunting_eq.rdf";
	public static String db_hunting_ac 	= "db/hunting/db_hunting_ac.rdf";
	public static String db_hunting_ev 	= "db/hunting/db_hunting_ev.rdf";
	public static String db_fishing_eq 	= "db/fishing/db_fishing_eq.rdf";
	public static String db_fishing_ac 	= "db/fishing/db_fishing_ac";
	public static String db_fishing_ev 	= "db/fishing/db_fishing_ev.rdf";
	public static String db_picking_eq 	= "db/fishing/db_picking_eq.rdf";
	public static String db_picking_ac 	= "db/fishing/db_picking_ac.rdf";
	public static String db_picking_ev 	= "db/fishing/db_picking_ev.rdf";
	
	OntModel jenaModel;
	
	public AgentWriter(){
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

	@Override
	public Model spy(User user) {
		String idUser = user.getId().toString();
		Resource rsc = jenaModel.createResource(user.getURI()+idUser)
		    .addProperty(VCARD.FN,user.getFullName())
			.addProperty(VCARD.Given,user.getGivenName())
			.addProperty(VCARD.Family,user.getFamilyName())
			.addProperty(VCARD.NICKNAME, user.getNickName())
			.addProperty(VCARD.EMAIL, user.getEmail());
		this.writeRDF(jenaModel, db_users);	
		return jenaModel;
		
	}

	@Override
	public Model spy(HuntingActivity activity) {

		String 			idActivity = activity.getId().toString();	
		User 			creator = User.findById(activity.getCreator().getId().toString());
		Amniote 		organism = (Amniote) activity.getOrganism();
		String 			label = "TODO";
		HuntingEvent	event = activity.getEvent();
		String 			idEvent = event.getId().toString();

		//Resource Activity
		Resource _activity = jenaModel.createResource(activity.getURI()+idActivity);
	
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
		//for(Equipment equipment : activity.getEquipments())
		//	_equipments.add(jenaModel.createIndividual(equipment.getURI()+equipment.getId(), Vita.VitaClass.Equipment.getOntClass(jenaModel)));		
		
		//Event : isRelatedTo
		Vita.VitaClass.Event.createOntClass(jenaModel);
		Individual _event = jenaModel.createIndividual(event.getURI()+idEvent, Vita.VitaClass.Event.getOntClass(jenaModel));	
		
		//Add Properties
		_activity.addProperty(RDFS.label, label);
		_activity.addProperty(DC.creator, creator.getFullName());
		_activity.addProperty(Vita.targetOrganism, _organism);
		_activity.addProperty(Vita.location, _location);
		for(Individual e : _equipments)
			_activity.addProperty(Vita.equipments, e);
		_activity.addProperty(Vita.isRelatedTo, _event);
		
		this.writeRDF(jenaModel, db_hunting_ac);	
		
		return jenaModel;
	}

	@Override
	public Model spy(PickingActivity activity) {
		String 			idActivity = activity.getId().toString();	
		User 			creator = User.findById(activity.getCreator().getId().toString());
		Amniote 		organism = (Amniote) activity.getOrganism();
		String 			label = "TODO";
		PickingEvent	event = activity.getEvent();

		//Resource Activity
		Resource _activity = jenaModel.createResource(activity.getURI()+idActivity);
	
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
		//for(Equipment equipment : activity.getEquipments())
		//	_equipments.add(jenaModel.createIndividual(equipment.getURI()+equipment.getId(), Vita.VitaClass.Equipment.getOntClass(jenaModel)));		
		
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
		
		this.writeRDF(jenaModel, db_picking_ac);	
		return jenaModel;
	}

	@Override
	public Model spy(FishingActivity activity) {
		String 			idActivity = activity.getId().toString();	
		User 			creator = User.findById(activity.getCreator().getId().toString());
		Amniote 		organism = (Amniote) activity.getOrganism();
		String 			label = "TODO";
		FishingEvent	event = activity.getEvent();

		//Resource Activity
		Resource _activity = jenaModel.createResource(activity.getURI()+idActivity);
	
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
		//for(Equipment equipment : activity.getEquipments())
		//	_equipments.add(jenaModel.createIndividual(equipment.getURI()+equipment.getId(), Vita.VitaClass.Equipment.getOntClass(jenaModel)));		
		
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
		
		this.writeRDF(jenaModel, db_fishing_ac);	
		return jenaModel;
	}

	@Override
	public Model spy(HuntingEquipment equipment) {
		String 			idEquipment = equipment.getId().toString();
		String			idUser = equipment.getCreator().getId().toString();
		User 			creator = User.findById(equipment.getCreator().getId().toString());
		String 			label = equipment.getLabel();
		String 			comment = equipment.getComment();
		String 			sector = equipment.getSector();
		
		Individual _equipment = jenaModel.createIndividual(equipment.getURI()+idEquipment, Vita.VitaClass.Equipment.getOntClass(jenaModel));		
		//Add Properties
		_equipment.addLiteral(Vita.sector, jenaModel.createTypedLiteral(sector,XSDDatatype.XSDstring));	
		_equipment.addProperty(RDFS.label, label);
		_equipment.addProperty(DC.creator, creator.getFullName());
		_equipment.addProperty(RDFS.comment, comment);
		_equipment.addProperty(Vita.owner, creator.getURI()+idUser);
		
		this.writeRDF(jenaModel, db_hunting_eq);
		return jenaModel;
	}
	
	

	@Override
	public Model spy(PickingEquipment equipment) {
		String 			idEquipment = equipment.getId().toString();
		String			idUser = equipment.getCreator().getId().toString();
		User 			creator = User.findById(equipment.getCreator().getId().toString());
		String 			label = equipment.getLabel();
		String 			comment = equipment.getComment();
		String 			sector = equipment.getSector();
		
		Individual _equipment = jenaModel.createIndividual(equipment.getURI()+idEquipment, Vita.VitaClass.Equipment.getOntClass(jenaModel));		
		//Add Properties
		_equipment.addLiteral(Vita.sector, jenaModel.createTypedLiteral(sector,XSDDatatype.XSDstring));	
		_equipment.addProperty(RDFS.label, label);
		_equipment.addProperty(DC.creator, creator.getFullName());
		_equipment.addProperty(RDFS.comment, comment);
		_equipment.addProperty(Vita.owner, creator.getURI()+idUser);
		
		this.writeRDF(jenaModel, db_hunting_eq);
		return jenaModel;
	}

	@Override
	public Model spy(FishingEquipment equipment) {
		String 			idEquipment = equipment.getId().toString();
		String			idUser = equipment.getCreator().getId().toString();
		User 			creator = User.findById(equipment.getCreator().getId().toString());
		String 			label = equipment.getLabel();
		String 			comment = equipment.getComment();
		String 			sector = equipment.getSector();
		
		Individual _equipment = jenaModel.createIndividual(equipment.getURI()+idEquipment, Vita.VitaClass.Equipment.getOntClass(jenaModel));		
		//Add Properties
		_equipment.addLiteral(Vita.sector, jenaModel.createTypedLiteral(sector,XSDDatatype.XSDstring));	
		_equipment.addProperty(RDFS.label, label);
		_equipment.addProperty(DC.creator, creator.getFullName());
		_equipment.addProperty(RDFS.comment, comment);
		_equipment.addProperty(Vita.owner, creator.getURI()+idUser);
		
		this.writeRDF(jenaModel, db_hunting_eq);
		return jenaModel;
	}

	@Override
	public Model spy(HuntingEvent event) {
		
		String		idEvent = event.getId().toString();
		User 		creator = User.findById(event.getCreator().getId().toString());
		String 		label = event.getLabel();
		String 		comment = event.getComment();
		String 		date = event.getDate();
		Location	location = event.getLocation();
		
		//ArrayList<HuntingActivity> 	activities = event.getActivities();
		//ArrayList<Key<User>> 		registers = event.getRegisters();	
		
		//Resource Event
		Resource _event = jenaModel.createResource(event.getURI()+idEvent);
		
		//Location
		Vita.VitaClass.Location.createOntClass(jenaModel);
		Individual _location = jenaModel.createIndividual(event.getURI()+"location/", Vita.VitaClass.Location.getOntClass(jenaModel));		
		_location.addLiteral(Vita.latitude,jenaModel.createTypedLiteral(event.getLocation().getLatPos(),XSDDatatype.XSDstring));
		_location.addLiteral(Vita.longitude,jenaModel.createTypedLiteral(event.getLocation().getLongPos(),XSDDatatype.XSDstring));
	
		//Activities
		//ArrayList<Individual> _activities = new ArrayList<Individual>();
		//for(HuntingActivity activity : activities)
		//	_activities.add(jenaModel.createIndividual(Vita.getURL()+"activity/"+activity.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Registers
		//ArrayList<Individual> _registers = new ArrayList<Individual>();
		//for(Key<User> register : registers)
		//	_registers.add(jenaModel.createIndividual(Vita.getURL()+"user/"+register.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Add properties
		_event.addProperty(Vita.location, _location);
		_event.addProperty(RDFS.label, label);
		_event.addProperty(DC.creator, creator.getFullName());
		_event.addProperty(RDFS.comment, comment);	
		//for(Individual e : _activities)
		//	_event.addProperty(Vita.activities, e);
		//for(Individual r : _registers)
		//	_event.addProperty(Vita.registers, r);
		this.writeRDF(jenaModel, db_hunting_ev);
		return jenaModel;
	}

	@Override
	public Model spy(PickingEvent event) {
		String		idEvent = event.getId().toString();
		User 		creator = User.findById(event.getCreator().getId().toString());
		String 		label = event.getLabel();
		String 		comment = event.getComment();
		String 		date = event.getDate();
		Location	location = event.getLocation();
		
		//ArrayList<HuntingActivity> 	activities = event.getActivities();
		//ArrayList<Key<User>> 		registers = event.getRegisters();	
		
		//Resource Event
		Resource _event = jenaModel.createResource(event.getURI()+idEvent);
		
		//Location
		Vita.VitaClass.Location.createOntClass(jenaModel);
		Individual _location = jenaModel.createIndividual(event.getURI()+"location/", Vita.VitaClass.Location.getOntClass(jenaModel));		
		_location.addLiteral(Vita.latitude,jenaModel.createTypedLiteral(event.getLocation().getLatPos(),XSDDatatype.XSDstring));
		_location.addLiteral(Vita.longitude,jenaModel.createTypedLiteral(event.getLocation().getLongPos(),XSDDatatype.XSDstring));
	
		//Activities
		//ArrayList<Individual> _activities = new ArrayList<Individual>();
		//for(HuntingActivity activity : activities)
		//	_activities.add(jenaModel.createIndividual(Vita.getURL()+"activity/"+activity.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Registers
		//ArrayList<Individual> _registers = new ArrayList<Individual>();
		//for(Key<User> register : registers)
		//	_registers.add(jenaModel.createIndividual(Vita.getURL()+"user/"+register.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Add properties
		_event.addProperty(Vita.location, _location);
		_event.addProperty(RDFS.label, label);
		_event.addProperty(DC.creator, creator.getFullName());
		_event.addProperty(RDFS.comment, comment);	
		//for(Individual e : _activities)
		//	_event.addProperty(Vita.activities, e);
		//for(Individual r : _registers)
		//	_event.addProperty(Vita.registers, r);
		this.writeRDF(jenaModel, db_picking_ev);
		return jenaModel;
	}

	@Override
	public Model spy(FishingEvent event) {
		String		idEvent = event.getId().toString();
		User 		creator = User.findById(event.getCreator().getId().toString());
		String 		label = event.getLabel();
		String 		comment = event.getComment();
		String 		date = event.getDate();
		Location	location = event.getLocation();
		
		//ArrayList<FishingActivity> 	activities = event.getActivities();
		//ArrayList<Key<User>> 		registers = event.getRegisters();	
		
		//Resource Event
		Resource _event = jenaModel.createResource(event.getURI()+idEvent);
		
		//Location
		Vita.VitaClass.Location.createOntClass(jenaModel);
		Individual _location = jenaModel.createIndividual(event.getURI()+"location/", Vita.VitaClass.Location.getOntClass(jenaModel));		
		_location.addLiteral(Vita.latitude,jenaModel.createTypedLiteral(event.getLocation().getLatPos(),XSDDatatype.XSDstring));
		_location.addLiteral(Vita.longitude,jenaModel.createTypedLiteral(event.getLocation().getLongPos(),XSDDatatype.XSDstring));
	
		//Activities
		//ArrayList<Individual> _activities = new ArrayList<Individual>();
		//for(HuntingActivity activity : activities)
		//	_activities.add(jenaModel.createIndividual(Vita.getURL()+"activity/"+activity.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Registers
		//ArrayList<Individual> _registers = new ArrayList<Individual>();
		//for(Key<User> register : registers)
		//	_registers.add(jenaModel.createIndividual(Vita.getURL()+"user/"+register.getId().toString(), Vita.VitaClass.Location.getOntClass(jenaModel)));		
		
		//Add properties
		_event.addProperty(Vita.location, _location);
		_event.addProperty(RDFS.label, label);
		_event.addProperty(DC.creator, creator.getFullName());
		_event.addProperty(RDFS.comment, comment);	
		//for(Individual e : _activities)
		//	_event.addProperty(Vita.activities, e);
		//for(Individual r : _registers)
		//	_event.addProperty(Vita.registers, r);
		this.writeRDF(jenaModel, db_fishing_ev);
		return jenaModel;
	}

	@Override
	public Model spy(Organism organism) {
		// TODO Auto-generated method stub
		return null;
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

}
