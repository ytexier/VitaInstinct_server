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
	OntClass ocDataset;
	OntClass ocActivity;
	OntClass ocUser;
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
	ObjectProperty activity;
	ObjectProperty sector;
	ObjectProperty creator;
	ObjectProperty targetOrganism;
	ObjectProperty location;
	ObjectProperty equipments;
	ObjectProperty isRelatedToEvent;
	ObjectProperty registrers;
	ObjectProperty value;
	ObjectProperty latitude;
	ObjectProperty longitude;

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
		ocDataset		= om.createClass( Vita.getURI() + "Dataset" 	);
		ocActivity 		= om.createClass( Vita.getURI() + "Activity" 	);
		ocUser			= om.createClass( Vita.getURI() + "User"		);
		ocOrganism 		= om.createClass( Vita.getURI() + "Organism" 	);
		ocEquipment 	= om.createClass( Vita.getURI() + "Equipment" );
		ocEvent 		= om.createClass( Vita.getURI() + "Event" 	);
		ocLocation 		= om.createClass( Vita.getURI() + "Location" 	);
		ocSector 		= om.createClass( Vita.getURI() + "Sector" 	);
	}

	public void addProperties(OntModel om) {
		
		
	
		 /**
	     * Object properties
	     */
		
		activity = om.createObjectProperty(Vita.NS +"activity");
		activity.addRange(ocActivity);
		activity.addDomain(ocDataset);

		sector = om.createObjectProperty(Vita.NS +"sector");
		sector.addRange(ocSector);
		sector.addDomain(ocActivity);
		
		creator = om.createObjectProperty(Vita.NS + "creator" );
		creator.addRange(ocUser);
		creator.addDomain(ocActivity);

		targetOrganism = om.createObjectProperty(Vita.NS + "targetOrganism" );
		targetOrganism.addRange(ocOrganism);
		targetOrganism.addDomain(ocActivity);
			
		location = om.createObjectProperty(Vita.NS + "location" );
		location.addRange(ocLocation);
		location.addDomain(ocActivity);
			
		equipments = om.createObjectProperty(Vita.NS + "equipment" );
		equipments.addRange(ocEquipment);
		equipments.addDomain(ocActivity);
			
		isRelatedToEvent = om.createObjectProperty(Vita.NS + "isRelatedToEvent" );
		isRelatedToEvent.addRange(ocEvent);
		isRelatedToEvent.addDomain(ocActivity);
		
		registrers = om.createObjectProperty(Vita.NS + "registrers" );
		registrers.addRange(ocUser);
		registrers.addDomain(ocEvent);
		
	    /**
	     * Data properties
	     */	
		
		value = om.createObjectProperty(Vita.NS + "value" );
		value.addIsDefinedBy(RDFS.Literal);
		value.addRange(ocSector);
		value.addDomain(ocSector);
		
		value = om.createObjectProperty(Vita.NS + "value" );
		value.addIsDefinedBy(RDFS.Literal);
		value.addRange(ocEquipment);
		value.addDomain(ocEquipment);
		
		value = om.createObjectProperty(Vita.NS + "value" );
		value.addIsDefinedBy(RDFS.Literal);
		value.addRange(ocEvent);
		value.addDomain(ocEvent);
		
		
		value = om.createObjectProperty(Vita.NS + "description" );
		value.addIsDefinedBy(RDFS.Literal);
		value.addRange(ocEvent);
		value.addDomain(ocEvent);
		
		
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
