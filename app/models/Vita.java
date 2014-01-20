package models;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Vita {
	
	public static Model omVita = ModelFactory.createDefaultModel();
	
	protected static final String _NS ="http://vita-instinct.herokuapp.com/api/1.1#%s";
	public static final String NS = String.format(_NS,"");
	public static String getURI() {return NS;}
	public static final Resource NAMESPACE = omVita.createResource(NS);
	
	/**
	 * URLs
	 */
	public static final String user_url 	= vita_src + "/user/";
	public static final String activity_url = vita_src + "/activity/";
	public static final String equipment_url 	= vita_src + "/equipment/";
	public static final String event_url 		= vita_src + "/event/";
	public static final String showcase_url 	= vita_src + "/showcase/";
	public static final String organism_url 	= vita_src + "/organism/";
	public static final String picture_url 		= vita_src + "/picture/";
	public static final String description_url 	= vita_src + "/description/";
	public static final String location_url 	= vita_src + "/location/";


	
	/**
	 * Ressources
	 */

	public static Resource rscActivity = null;

	public static Resource rscUser = null;
	public static Resource rscOrganism = null;
	public static Resource rscEvent = null;
	public static Resource rscSector = null;
	public static Resource rscEquipment = null; 
	public static Resource rscShowcase = null; 

	public static Resource rscPicture = null; 
	public static Resource rscDescription = null;
	public static Resource rscLocation = null;

	/**
	 * Properties	
	 */
	
	//Domain : Activity
	public static Property prtUsedEquipment 	= omVita.createProperty(_NS+"usedEquipment");
	public static Property prtTargetedOrganism 	= omVita.createProperty(_NS+"targetedOrganism");
	public static Property prtIsRelatedToEvent 	= omVita.createProperty(_NS+"isRelatedToEvent");
	
	//Domain : Activity, Equipement, Event, Organim
	public static Property prtHasShowcase 		= omVita.createProperty(_NS+"hasShowcase");
	
	//Domain : Showcase
	public static Property prtHasPicture 		= omVita.createProperty(_NS+"hasPicture");
	public static Property prtHasDescription 	= omVita.createProperty(_NS+"hasDescription");
	public static Property prtHasLocation 		= omVita.createProperty(_NS+"hasLocation");
	

	
}
