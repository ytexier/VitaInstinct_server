package models;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Vita {
	
	public enum VitaClass {
		Dataset,
		User,
		Activity,
		Sector,
		Location,
		Organism,
		Equipment,
		Event;
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
	protected static final String _NS ="http://vita-instinct.herokuapp.com/api/1.1#%s";
	public static final String NS = String.format(_NS,"");
	
	public static String getURI() {return NS;}
	
	/** Namespace of vocablary as resource */
	public static final Resource NS_r = vita_m.createResource(NS);
	

	
	 /**
     * Object properties
     */
    public static final Property activity = vita_m.createProperty(String.format(_NS, "activity"));
    public static final Property sector = vita_m.createProperty(String.format(_NS, "sector"));
    public static final Property creator = vita_m.createProperty(String.format(_NS, "creator"));
    public static final Property targetOrganism = vita_m.createProperty(String.format(_NS, "targetOrganism"));
    public static final Property location = vita_m.createProperty(String.format(_NS, "location"));
    public static final Property equipments = vita_m.createProperty(String.format(_NS, "equipments"));
    public static final Property isRelatedToEvent = vita_m.createProperty(String.format(_NS, "isRelatedToEvent"));
    public static final Property registrers = vita_m.createProperty(String.format(_NS, "registrers"));
    /**
     * Data properties
     */
    public static final Property value = vita_m.createProperty(String.format(_NS, "value"));
    public static final Property latitude = vita_m.createProperty(String.format(_NS, "value"));
    public static final Property longitude = vita_m.createProperty(String.format(_NS, "value"));

	
	
}
