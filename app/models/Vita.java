package models;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class Vita {
	
	public static Model omVita = ModelFactory.createDefaultModel();
	
	public static final String vita_src ="http://vita-instinct.herokuapp.com";
	public static final String vita_ns = vita_src + "#";
	
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
	public static Property prtUsedEquipment 	= omVita.createProperty(vita_ns+"usedEquipment");
	public static Property prtTargetedOrganism 	= omVita.createProperty(vita_ns+"targetedOrganism");
	public static Property prtIsRelatedToEvent 	= omVita.createProperty(vita_ns+"isRelatedToEvent");
	
	//Domain : Activity, Equipement, Event, Organim
	public static Property prtHasShowcase 		= omVita.createProperty(vita_ns+"hasShowcase");
	
	//Domain : Showcase
	public static Property prtHasPicture 		= omVita.createProperty(vita_ns+"hasPicture");
	public static Property prtHasDescription 	= omVita.createProperty(vita_ns+"hasDescription");
	public static Property prtHasLocation 		= omVita.createProperty(vita_ns+"hasLocation");
	

	
}
