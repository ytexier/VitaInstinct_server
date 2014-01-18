package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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

public class Vita {
	
	public static final String vita_src ="vita-instinct.herokuapp.com/ontology";
	
	public static final String vita_ns = vita_src + "#";
	
	private static OntModel omVita;
	
	String vitaOntology_url = "test/vita.owl";
	

	OntClass ocActivity;
	OntClass ocOrganism;
	OntClass ocEquipment;
	OntClass ocEvent;
	OntClass ocLocation;
	OntClass ocShowcase;
	OntClass ocDescription;
	OntClass ocPicture;
	OntClass ocSector;
	
	
	
	public Vita() {

		omVita = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		addClasses();
		addProperties();
		
	}
	
	public void addClasses() {
		ocActivity 		= omVita.createClass( vita_ns + "Activity" );
		ocOrganism 		= omVita.createClass( vita_ns + "Organism" );
		ocEquipment 	= omVita.createClass( vita_ns + "Equipment" );
		ocEvent 		= omVita.createClass( vita_ns + "Event" );
		ocLocation 		= omVita.createClass( vita_ns + "Location" );
		ocShowcase 		= omVita.createClass( vita_ns + "Showcase" );
		ocDescription 	= omVita.createClass( vita_ns + "Description" );
		ocPicture 		= omVita.createClass( vita_ns + "Picture" );
		ocSector 		= omVita.createClass( vita_ns + "Sector" );
	}

	public void addProperties() {
		
		/**
		 * PROPERTIES : Domain ACTIVITY
		 */	
		
		//targetedOrganism
		ObjectProperty targetedOrganism 
			= omVita.createObjectProperty( vita_ns + "targetedOrganism" );
		targetedOrganism.addRange(ocOrganism);
		targetedOrganism.addDomain(ocActivity);
		
		//usedEquipment
		ObjectProperty usedEquipment 
			= omVita.createObjectProperty( vita_ns + "usedEquipment" );
		usedEquipment.addRange(ocEquipment);
		usedEquipment.addDomain(ocActivity);
		
		//isRelatedToEvent
		ObjectProperty isRelatedToEvent 
			= omVita.createObjectProperty( vita_ns + "isRelatedToEvent" );
		isRelatedToEvent.addRange(ocEvent);
		isRelatedToEvent.addDomain(ocActivity);

		/**
		 * PROPERTIES : DOMAIN Showcase
		 */	
		
		//hasLocation
		ObjectProperty hasLocation 
			= omVita.createObjectProperty( vita_ns + "hasLocation" );
		hasLocation.addRange(ocLocation);
		hasLocation.addDomain(ocShowcase);
			
		//hasDescription
		ObjectProperty hasDescription 
			= omVita.createObjectProperty( vita_ns + "hasDescription" );
		hasDescription.addRange(ocDescription);
		hasLocation.addDomain(ocShowcase);
		hasDescription.addIsDefinedBy(RDFS.Literal);
			
		//hasPicture
		ObjectProperty hasPicture 
			= omVita.createObjectProperty( vita_ns + "hasPicture" );
		hasPicture.addRange(ocPicture);
		hasPicture.addDomain(ocShowcase);
			
		//hasPicture
		ObjectProperty hasSector 
			= omVita.createObjectProperty( vita_ns + "hasPicture" );
		hasSector.addRange(ocSector);
		hasSector.addDomain(ocShowcase);

		/**
		 * PROPERTIES : RANGE Showcase
		 */	
			
		//hasShowcase
		ObjectProperty hasShowcase 
			= omVita.createObjectProperty( vita_ns + "hasShowcase" );
		hasShowcase.addRange(ocShowcase);
		
	 }
	 
	 
	public String toString(){
		String res = "";
		StmtIterator iter = omVita.listStatements();
		while (iter.hasNext()) 
		{
			Statement stmt      = iter.nextStatement();  // get next statement
			Resource  subject   = stmt.getSubject();     // get the subject
			Property  predicate = stmt.getPredicate();   // get the predicate
			RDFNode   object    = stmt.getObject();      // get the object

			res+="\n";
			res+="//"+subject.toString().substring(subject.toString().lastIndexOf("#"));
			res+="\n";
			res+="[subject]:";
			res+=subject.toString();
			res+="\n";
			res+="[predicate]:";
			res+=predicate.toString();
			res+="\n";
			res+="[object]:";
			if (object instanceof Resource)
				res+=object.toString();
			else
				res+= "\"" + object.toString() + "\"";
			
			res+="\n";
		} 
		return res;
	}
}
