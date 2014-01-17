package models;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class Vita {
	
	public static final String vita_src ="vita-instinct.herokuapp.com/ontology";
	
	public static final String vita_ns = vita_src + "#";
	
	OntModel omVita = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	
	private void OWLOntologyBuilder(){
		
		/**
		 * CLASSES
		 */
		
		OntClass ocActivity 	= omVita.getOntClass( vita_ns + "Activity" );
		OntClass ocOrganism 	= omVita.getOntClass( vita_ns + "Organism" );
		OntClass ocEquipment 	= omVita.getOntClass( vita_ns + "Equipment" );
		OntClass ocEvent 		= omVita.getOntClass( vita_ns + "Event" );
		OntClass ocLocation 	= omVita.getOntClass( vita_ns + "Location" );
		OntClass ocShowcase 	= omVita.getOntClass( vita_ns + "Showcase" );
		OntClass ocDescription 	= omVita.getOntClass( vita_ns + "Description" );
		OntClass ocPicture 		= omVita.getOntClass( vita_ns + "Picture" );
		OntClass ocSector 		= omVita.getOntClass( vita_ns + "Sector" );
		
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
}
