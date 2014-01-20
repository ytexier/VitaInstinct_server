package agents;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
	
	private String db_activities = "db/vita_activities.rdf";
	
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
	public void spy(HuntingActivity huntingActivity) {
	
		Individual dataset = jenaModel
				.createIndividual(Vita.getURI()+"dbActivities", jenaModel.getOntClass(Vita.VitaClass.Dataset.getNS()));
		
		Vita.VitaClass.Activity.createOntClass(jenaModel);
		Vita.VitaClass.Sector.createOntClass(jenaModel);
		Vita.VitaClass.User.createOntClass(jenaModel);
		Vita.VitaClass.Organism.createOntClass(jenaModel);
		Vita.VitaClass.Equipment.createOntClass(jenaModel);
		Vita.VitaClass.Event.createOntClass(jenaModel);
		
		Individual activity = jenaModel.createIndividual(Vita.VitaClass.Activity.getOntClass(jenaModel));
		
		dataset.addProperty(Vita.activity, activity);
		
		Individual sector = jenaModel.createIndividual(Vita.getURI()+"sector",Vita.VitaClass.Sector.getOntClass(jenaModel));
		sector.addLiteral(Vita.value,jenaModel.createTypedLiteral("hunting",XSDDatatype.XSDstring));
		
		activity.addProperty(Vita.sector, sector);
		
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
