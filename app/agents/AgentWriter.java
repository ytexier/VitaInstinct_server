package agents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model spy(PickingActivity activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model spy(FishingActivity activity) {
		// TODO Auto-generated method stub
		return null;
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
		String idEvent = event.getId().toString();
		Resource _event = jenaModel.createResource(event.getURI()+idEvent);
		this.writeRDF(jenaModel, db_hunting_ev);
		return jenaModel;
	}

	@Override
	public Model spy(PickingEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model spy(FishingEvent event) {
		// TODO Auto-generated method stub
		return null;
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
