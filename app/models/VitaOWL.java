package models;

import java.io.OutputStream;
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
	
	/**
	 * CLASSES DEF
	 */	
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
	ObjectProperty opTargetedOrganism;
	ObjectProperty opUsedEquipment;
	ObjectProperty opIsRelatedToEvent;
	ObjectProperty opHasLocation;
	ObjectProperty opHasDescription;
	ObjectProperty opHasPicture;
	ObjectProperty opHasSector;
	ObjectProperty opHasShowcase;

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
		ocActivity 		= om.createClass( vita_ns + "Activity" 	);
		ocUser			= om.createClass( vita_ns + "User"		);
		ocOrganism 		= om.createClass( vita_ns + "Organism" 	);
		ocEquipment 	= om.createClass( vita_ns + "Equipment" );
		ocEvent 		= om.createClass( vita_ns + "Event" 	);
		ocLocation 		= om.createClass( vita_ns + "Location" 	);
		ocShowcase 		= om.createClass( vita_ns + "Showcase" 	);
		ocDescription 	= om.createClass( vita_ns + "Description" );
		ocPicture 		= om.createClass( vita_ns + "Picture" 	);
		ocSector 		= om.createClass( vita_ns + "Sector" 	);
	}

	public void addProperties(OntModel om) {
		
		/**
		 * PROPERTIES : Domain ACTIVITY
		 */	
		
		opTargetedOrganism = om.createObjectProperty(vita_ns+"targetedOrganism");
		opTargetedOrganism.addRange(ocOrganism);
		opTargetedOrganism.addDomain(ocActivity);

		opUsedEquipment = om.createObjectProperty(vita_ns+"usedEquipment");
		opUsedEquipment.addRange(ocEquipment);
		opUsedEquipment.addDomain(ocActivity);
		
		opIsRelatedToEvent = om.createObjectProperty( vita_ns + "isRelatedToEvent" );
		opIsRelatedToEvent.addRange(ocEvent);
		opIsRelatedToEvent.addDomain(ocActivity);

		/**
		 * PROPERTIES : DOMAIN Showcase
		 */	
		
		opHasLocation = om.createObjectProperty( vita_ns + "hasLocation" );
		opHasLocation.addRange(ocLocation);
		opHasLocation.addDomain(ocShowcase);
			
		opHasDescription = om.createObjectProperty( vita_ns + "hasDescription" );
		opHasDescription.addRange(ocDescription);
		opHasDescription.addDomain(ocShowcase);
		opHasDescription.addIsDefinedBy(RDFS.Literal);
			
		opHasPicture = om.createObjectProperty( vita_ns + "hasPicture" );
		opHasPicture.addRange(ocPicture);
		opHasPicture.addDomain(ocShowcase);
			
		opHasSector = om.createObjectProperty( vita_ns + "hasPicture" );
		opHasSector.addRange(ocSector);
		opHasSector.addDomain(ocShowcase);

		/**
		 * PROPERTIES : RANGE Showcase
		 */	
			
		opHasShowcase = om.createObjectProperty( vita_ns + "hasShowcase" );
		opHasShowcase.addRange(ocShowcase);
		opHasShowcase.addDomain(ocActivity);
		opHasShowcase.addDomain(ocEquipment);
		opHasShowcase.addDomain(ocOrganism);
		opHasShowcase.addDomain(ocEvent);
		
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
