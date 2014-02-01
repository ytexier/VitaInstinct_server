package controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import models.ActivityEnding;
import models.Location;
import models.Organism;
import models.Sex;
import models.User;
import models.factory.AbstractActivity;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;
import models.factory.FactorySector;
import models.fishing.FactoryFishingSector;
import models.fishing.FishingActivity;
import models.fishing.FishingEquipment;
import models.fishing.FishingEvent;
import models.hunting.FactoryHuntingSector;
import models.hunting.HuntingActivity;
import models.hunting.HuntingEquipment;
import models.hunting.HuntingEvent;
import models.picking.FactoryPickingSector;
import models.picking.PickingActivity;
import models.picking.PickingEquipment;
import models.picking.PickingEvent;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import agents.AgentJena;
import agents.AgentWriter;
import forms.AbstractActivityForm;
import forms.Secured;

public class Activities extends Controller {
	


	public static String db_hunting_ac 	= "db/hunting/db_hunting_ac.rdf";
	public static String db_fishing_ac 	= "db/fishing/db_fishing_ac";
	public static String db_picking_ac 	= "db/fishing/db_picking_ac.rdf";

	
	static Form<AbstractActivityForm> abstractActivityForm = Form.form(AbstractActivityForm.class);


	public static Result get(String id, String sector){
		
    	AbstractActivity activityFound = null;
    	
    	if(sector.equals("hunting"))
    		activityFound = HuntingActivity.findById(id);
     	if(sector.equals("picking"))
    		activityFound = PickingActivity.findById(id);
     	if(sector.equals("fishing"))
    		activityFound = FishingActivity.findById(id);
    	

   		if(request().accepts("text/html")){
   			return ok();
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(activityFound));
   		
   		else if (request().accepts("application/rdf+xml")){
   			OutputStream out = new ByteArrayOutputStream();
   			activityFound.accept(new AgentJena()).write(out, "RDF/XML-ABBREV");
   			return ok(out.toString());
   		}

		return ok(Json.toJson(activityFound));
	}
	
	

	public static Result activities(String sector){
		
		String db = "";
		
    	AbstractActivity activityFound = null;
    	
    	if(sector.equals("hunting"))
    		db=db_hunting_ac;
     	if(sector.equals("picking"))
     		db=db_picking_ac;
     	if(sector.equals("fishing"))
     		db=db_fishing_ac;
     	
		Model model_loaded = FileManager.get().loadModel(db);

   		if(request().accepts("text/html")){
   			return ok();
   		}
   		
   		else if(request().accepts("application/json")){
   			try { 			
   	    	  	if(sector.equals("hunting"))
					return ok(Json.toJson(HuntingActivity.all()));
		     	if(sector.equals("picking"))
	   	     		return ok(Json.toJson(PickingActivity.all()));
	   	     	if(sector.equals("fishing"))
	   	     		return ok(Json.toJson(FishingActivity.all()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		}
   		
   		else if (request().accepts("application/rdf+xml")){
   			OutputStream out = new ByteArrayOutputStream();
   			model_loaded.write(out, "RDF/XML-ABBREV");
   			return ok(out.toString());
   		}

		return ok(Json.toJson(activityFound));
	}


	
	
	
    public static Result creator(String id, String sector) throws Exception{
    	
    	AbstractActivity activityFound = null;
    	if(sector.equals("hunting"))
    		activityFound = HuntingActivity.findById(id);
    	if(sector.equals("picking"))
    		activityFound = PickingActivity.findById(id);
     	if(sector.equals("fishing"))
    		activityFound = FishingActivity.findById(id);

   		if(request().accepts("text/html")){
   			//TODO
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(activityFound.getCreator()));
   		
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}

		return ok(Json.toJson(activityFound.getCreator()));
    }
    
    
    public static Result delete(String id, String sector) throws Exception {
    	
    	AbstractActivity activityFound = null;
    	if(sector.equals("hunting"))
    		activityFound = HuntingActivity.findById(id);
     	if(sector.equals("picking"))
    		activityFound = PickingActivity.findById(id);
     	if(sector.equals("fishing"))
    		activityFound = FishingActivity.findById(id);
    	

		Key<User> userKey = activityFound.getCreator();
		User creator = User.findById(userKey.getId().toString());
		
		UpdateResults<User> res =
				MorphiaObject.datastore.update(
						creator,
						MorphiaObject.datastore.createUpdateOperations(User.class).removeAll("activities", activityFound)
				);
        if (activityFound != null)
        	MorphiaObject.datastore.delete(activityFound);
        return redirect(routes.Application.index());
	}
	
	@Security.Authenticated(Secured.class)
	public static Result newActivity() throws Exception{
        
		Form<AbstractActivityForm> filledForm = abstractActivityForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest("newActivity : filledForm.hasErrors()");
        }
        else {
        	
        		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        		String eventId = filledForm.get().eventId;
        		String equipmentId = filledForm.get().equipmentId;
				String sector = filledForm.get().sector;
				Date date =	filledForm.get().date;
				int amountOfOrganism = Integer.parseInt(filledForm.get().amountOfOrganism);
				String specie = filledForm.get().organism;
				String sex = filledForm.get().sex;
				String activityEnding = filledForm.get().activityEnding;
				
				Location location = new Location(filledForm.get().longitude, filledForm.get().latitude);
				
				FactorySector factorySector = null;
				AbstractActivity aActivity = null;
				AbstractEvent aEvent = null;
				AbstractEquipment aEquipment = null;
				
				if(sector.equals("hunting")){
					factorySector = new FactoryHuntingSector();
					aEvent = HuntingEvent.findById(eventId);
					aEquipment = HuntingEquipment.findById(equipmentId);
				}else if(sector.equals("fishing")){
					factorySector = new FactoryFishingSector();
					aEvent = FishingEvent.findById(equipmentId);
					aEquipment = FishingEquipment.findById(equipmentId);
				}else if(sector.equals("picking")){
					factorySector = new FactoryPickingSector();
					aEvent = PickingEvent.findById(eventId);
					aEquipment = PickingEquipment.findById(equipmentId);
				}
				
				String formattedDate = dateFormatter.format(date);
				
				User user = User.findByEmail(request().username());
				Key<User> creatorKey = MorphiaObject.datastore.getKey(user);
				
				String _abstract = "";
				String thumbnail = "";
				ArrayList<String> rdfData = new ArrayList<String>();
				String organism = org.apache.commons.lang3.StringUtils.capitalize(specie);
				rdfData = Organism.getInfos(organism);
				if(rdfData.size() != 0) {
					thumbnail = rdfData.get(0);
					_abstract = rdfData.get(1);
				}
				
				aActivity = factorySector.createActivity(specie, _abstract, thumbnail, amountOfOrganism, 
						formattedDate, location, creatorKey, aEvent, aEquipment);

				
				if(Sex.contains(sex))
					aActivity.getOrganism().setSex(Enum.valueOf(Sex.class, sex));

				if(ActivityEnding.contains(activityEnding))
						aActivity.setActivityEnding(Enum.valueOf(ActivityEnding.class, activityEnding));

		
	
				Key<AbstractActivity> activityKey = MorphiaObject.datastore.save(aActivity);

				UpdateResults<User> res =
						MorphiaObject.datastore.update(
								user,
								MorphiaObject.datastore.createUpdateOperations(User.class).add("activities", activityKey)
						);
				

				aActivity.accept(new AgentWriter());
				
				return redirect(routes.Application.index());
        }
	}
	
}