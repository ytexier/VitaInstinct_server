package models;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDFS;

public class VitaOWL {
	
	public static final String vita_src ="vita-instinct.herokuapp.com/ontology";
	
	public static final String vita_ns = vita_src + "#";
	
	private static OntModel omVita;
	
	String vitaOntology_url = "test/vita.owl";

	/**
	 * CLASSES DEF
	 */	
	OntClass ocActivity;
	OntClass ocOrganism;
	OntClass ocEquipment;
	OntClass ocEvent;
	OntClass ocLocation;
	OntClass ocShowcase;
	OntClass ocDescription;
	OntClass ocPicture;
	OntClass ocSector;
	
	/**
	 * PROPERTIES DEF
	 */	
	ObjectProperty targetedOrganism;
	ObjectProperty usedEquipment;
	ObjectProperty isRelatedToEvent;
	ObjectProperty hasLocation;
	ObjectProperty hasDescription;
	ObjectProperty hasPicture;
	ObjectProperty hasSector;
	ObjectProperty hasShowcase;

	public VitaOWL() {

		omVita = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		addClasses();
		addProperties();
		
	}
	
	public void addClasses() {
		ocActivity 		= omVita.createClass( vita_ns + "Activity" 	);
		ocOrganism 		= omVita.createClass( vita_ns + "Organism" 	);
		ocEquipment 	= omVita.createClass( vita_ns + "Equipment" );
		ocEvent 		= omVita.createClass( vita_ns + "Event" 	);
		ocLocation 		= omVita.createClass( vita_ns + "Location" 	);
		ocShowcase 		= omVita.createClass( vita_ns + "Showcase" 	);
		ocDescription 	= omVita.createClass( vita_ns + "Description" );
		ocPicture 		= omVita.createClass( vita_ns + "Picture" 	);
		ocSector 		= omVita.createClass( vita_ns + "Sector" 	);
	}

	public void addProperties() {
		
		/**
		 * PROPERTIES : Domain ACTIVITY
		 */	
		
		targetedOrganism = omVita.createObjectProperty(vita_ns+"targetedOrganism");
		targetedOrganism.addRange(ocOrganism);
		targetedOrganism.addDomain(ocActivity);

		usedEquipment = omVita.createObjectProperty(vita_ns+"usedEquipment");
		usedEquipment.addRange(ocEquipment);
		usedEquipment.addDomain(ocActivity);
		
		isRelatedToEvent = omVita.createObjectProperty( vita_ns + "isRelatedToEvent" );
		isRelatedToEvent.addRange(ocEvent);
		isRelatedToEvent.addDomain(ocActivity);

		/**
		 * PROPERTIES : DOMAIN Showcase
		 */	
		
		hasLocation = omVita.createObjectProperty( vita_ns + "hasLocation" );
		hasLocation.addRange(ocLocation);
		hasLocation.addDomain(ocShowcase);
			
		hasDescription = omVita.createObjectProperty( vita_ns + "hasDescription" );
		hasDescription.addRange(ocDescription);
		hasLocation.addDomain(ocShowcase);
		hasDescription.addIsDefinedBy(RDFS.Literal);
			
		hasPicture = omVita.createObjectProperty( vita_ns + "hasPicture" );
		hasPicture.addRange(ocPicture);
		hasPicture.addDomain(ocShowcase);
			
		hasSector = omVita.createObjectProperty( vita_ns + "hasPicture" );
		hasSector.addRange(ocSector);
		hasSector.addDomain(ocShowcase);

		/**
		 * PROPERTIES : RANGE Showcase
		 */	
			
		hasShowcase = omVita.createObjectProperty( vita_ns + "hasShowcase" );
		hasShowcase.addRange(ocShowcase);
		hasSector.addDomain(ocActivity);
		hasSector.addDomain(ocEquipment);
		hasSector.addDomain(ocOrganism);
		hasSector.addDomain(ocEvent);
		
	 }
	
	 
	public String toString(){
		
		ArrayList<String> classes = new ArrayList<String>();
		classes.add("\n/**\n* CLASSES\n*/");
		ArrayList<String> properties = new ArrayList<String>();
		properties.add("\n/**\n* PROPERTIES\n*/");
		ArrayList<String> others = new ArrayList<String>();
		others.add("\n/**\n* OTHERS\n*/");
	
		String res = "";
		StmtIterator iter = omVita.listStatements();
		while (iter.hasNext()) 
		{
			Statement stmt      = iter.nextStatement();  // get next statement
			Resource  subject   = stmt.getSubject();     // get the subject
			Property  predicate = stmt.getPredicate();   // get the predicate
			RDFNode   object    = stmt.getObject();      // get the object

			String subject_split = subject.toString().substring(subject.toString().lastIndexOf("#")+1);
			String object_split = object.toString().substring(object.toString().lastIndexOf("#")+1);
			
			res+="\n";
			res+="\t##"+subject_split;
			res+="\n";
			res+="\t[subject]:";
			res+=subject.toString();
			res+="\n";
			res+="\t[predicate]:";
			res+=predicate.toString();
			res+="\n";
			res+="\t[object]:";
			if (object instanceof Resource)
				res+=object.toString();
			else
				res+= "\"" + object.toString() + "\"";
			
			res+="\n";
			
			if(object_split.equals("Class")){
				classes.add(res);
			}else if(object_split.equals("ObjectProperty")){
				properties.add(res);
			}else{
				others.add(res);
			}
			
			
			res = "";
		} 
		
		properties.addAll(others);
		classes.addAll(properties);
		return classes.toString();
	}
}
