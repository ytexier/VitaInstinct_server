package models;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Vita {
	
	/** VitaClass make easely the Ontology classes */
	public enum VitaClass {
		Activity,
		Event,
		Equipment,
		User,
		Organism,
		Location,
		;
		public String getNS() {
			return String.format(_NS, toString());
		}
		public OntClass getOntClass(OntModel model) {
			return model.getOntClass(getNS());
		}
		public OntClass createOntClass(OntModel model) {
			return model.createClass(getNS());
		}		
		public Property createProperty(OntModel model) {
			return model.createProperty(getNS());
		}			
	};
	
	
	/** RDF model */
	public static Model vita_m = ModelFactory.createDefaultModel();
	
	/** Namespace as a string */
	protected static final String _NS ="http://vita-instinct.herokuapp.com/rdf-syntax-ns#%s";
	public static final String NS = String.format(_NS,"");
	
	public static String getURI() {return NS;}
	public static String getURL() {return "http://vita-instinct.herokuapp.com/";}
	
	/** Namespace of vocablary as resource */
	public static final Resource NS_r = vita_m.createResource(NS);

	 /**
     * Object properties
     */
    public static final Property activities = vita_m.createProperty(String.format(_NS, "activities"));
    public static final Property events = vita_m.createProperty(String.format(_NS, "events"));
    public static final Property equipments = vita_m.createProperty(String.format(_NS, "equipments"));  
    public static final Property registers = vita_m.createProperty(String.format(_NS, "registers"));  
    
   
    //public static final Property creator = vita_m.createProperty(String.format(_NS, "creator"));
    public static final Property targetOrganism = vita_m.createProperty(String.format(_NS, "targetOrganism"));
    public static final Property location = vita_m.createProperty(String.format(_NS, "location"));
    public static final Property isRelatedTo = vita_m.createProperty(String.format(_NS, "isRelatedTo"));
    
    /**
     * Data properties
     */
    public static final Property latitude = vita_m.createProperty(String.format(_NS, "latitude"));
    public static final Property longitude = vita_m.createProperty(String.format(_NS, "longitude"));

	//ResourceFactory.createTypedLiteral("2012-03-11", XSDDatatype.XSDdate)
    //DC.creator : Nom, identifiant
	
}
