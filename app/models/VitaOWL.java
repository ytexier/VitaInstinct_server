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
	
	private static OntModel omVita;
	
	/**
	 * CLASSES DEF
	 */	
	OntClass ocActivity;
	OntClass ocUser;
	OntClass ocOrganism;
	OntClass ocEquipment;
	OntClass ocEvent;
	OntClass ocLocation;
	
	
	/**
	 * PROPERTIES DEF
	 */	
	ObjectProperty activities;
	ObjectProperty events;
	ObjectProperty equipments;
	ObjectProperty registers;
	ObjectProperty owner;
	ObjectProperty targetOrganism;
	ObjectProperty location;
	ObjectProperty isRelatedTo;
	ObjectProperty latitude;
	ObjectProperty longitude;
	ObjectProperty sector;

	public VitaOWL() {
			omVita = createModel();
	}
	
	public OntModel createModel(){
		OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, null);
		om.setNsPrefix( "vita", Vita.NS );
		addClasses(om);
		addProperties(om);
		return om;
	}

	public void addClasses(OntModel om) {
		ocActivity 		= om.createClass( Vita.getURI() + "Activity" 	);
		ocUser			= om.createClass( Vita.getURI() + "User"		);
		ocOrganism 		= om.createClass( Vita.getURI() + "Organism" 	);
		ocEquipment 	= om.createClass( Vita.getURI() + "Equipment" );
		ocEvent 		= om.createClass( Vita.getURI() + "Event" 	);
		ocLocation 		= om.createClass( Vita.getURI() + "Location" 	);
	}

	public void addProperties(OntModel om) {
		
		
	
		 /**
	     * Object properties
	     */
		
		activities = om.createObjectProperty(Vita.NS +"activities");
		activities.addRange(ocActivity);
		activities.addDomain(ocActivity);

		events = om.createObjectProperty(Vita.NS +"events");
		events.addRange(ocEvent);
		events.addDomain(ocActivity);
		
		equipments = om.createObjectProperty(Vita.NS + "equipment" );
		equipments.addRange(ocEquipment);
		equipments.addDomain(ocActivity);
		
		registers = om.createObjectProperty(Vita.NS + "registers" );
		registers.addRange(ocUser);
		registers.addDomain(ocEvent);

		targetOrganism = om.createObjectProperty(Vita.NS + "targetOrganism" );
		targetOrganism.addRange(ocOrganism);
		targetOrganism.addDomain(ocActivity);
			
		location = om.createObjectProperty(Vita.NS + "location" );
		location.addRange(ocLocation);
		location.addDomain(ocActivity);
			
			
		owner = om.createObjectProperty(Vita.NS + "owner" );
		owner.addRange(ocUser);
		owner.addDomain(ocEquipment);
		
		isRelatedTo = om.createObjectProperty(Vita.NS + "registrers" );
		isRelatedTo.addRange(ocEvent);
		isRelatedTo.addDomain(ocActivity);
		
	    /**
	     * Data properties
	     */	
		
		sector = om.createObjectProperty(Vita.NS + "sector" );
		sector.addIsDefinedBy(RDFS.Literal);
		sector.addRange(ocEvent);
		sector.addDomain(ocEvent);
		
		
		latitude = om.createObjectProperty(Vita.NS + "latitude" );
		latitude.addIsDefinedBy(RDFS.Literal);
		latitude.addRange(ocLocation);
		latitude.addDomain(ocLocation);
		
		longitude = om.createObjectProperty(Vita.NS + "longitude" );
		longitude.addIsDefinedBy(RDFS.Literal);
		longitude.addRange(ocLocation);
		longitude.addDomain(ocLocation);

		
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

