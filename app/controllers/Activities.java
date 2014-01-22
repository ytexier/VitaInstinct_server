package controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.ActivityEnding;
import models.Fish;
import models.Location;
import models.Mammal;
import models.Organism;
import models.Plant;
import models.Sex;
import models.User;
import models.factory.AbstractActivity;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;
import models.factory.FactorySector;
import models.fishing.FishingActivity;
import models.fishing.FactoryFishingSector;
import models.hunting.HuntingActivity;
import models.hunting.FactoryHuntingSector;
import models.picking.PickingActivity;
import models.picking.FactoryPickingSector;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import agents.AgentJena;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.block.viewer;
import forms.AbstractActivityForm;
import forms.Secured;

public class Activities extends Controller {
	

	
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

		return ok(Json.toJson(activityFound.getCreator()));
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
				
				if(sector.equals("hunting")){
					factorySector = new FactoryHuntingSector();
				}else if(sector.equals("fishing")){
					factorySector = new FactoryFishingSector();
				}else if(sector.equals("picking")){
					factorySector = new FactoryPickingSector();
				}
				
				String formattedDate = dateFormatter.format(date);
				
				User user = User.findByEmail(request().username());
				Key<User> creatorKey = MorphiaObject.datastore.getKey(user);
				
				/*
				 * MORPHIA REQUESTS 
				 * GET EVENT BY ID
				 * GET EQUIPMENT BY ID
				 */
				
				AbstractEvent event = factorySector.createEvent("event", formattedDate, "comment", location, creatorKey);
				AbstractEquipment equipment = factorySector.createEquipment("equi", "comment", creatorKey);
						
				aActivity = factorySector.createActivity(specie, amountOfOrganism, formattedDate, location, creatorKey, event, equipment);

				
				if(Sex.contains(sex))
					aActivity.getOrganism().setSex(Enum.valueOf(Sex.class, sex));

				if(ActivityEnding.contains(activityEnding))
						aActivity.setActivityEnding(Enum.valueOf(ActivityEnding.class, activityEnding));

		
				aActivity.accept(new AgentJena());
				
				Key<AbstractActivity> activityKey = MorphiaObject.datastore.save(aActivity);

				UpdateResults<User> res =
						MorphiaObject.datastore.update(
								user,
								MorphiaObject.datastore.createUpdateOperations(User.class).add("activities", activityKey)
						);
				

				
				
				return redirect(routes.Application.index());
        }
	}
/*
	public static Result add(String user_id) throws Exception{
        
		Form<AbstractActivityForm> filledForm = abstractActivityForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest("newActivity : filledForm.hasErrors()");
        }
        else {
        	
	        	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
	        	
				String sector = filledForm.get().sector;
				Date date =	filledForm.get().date;
				int amountOfOrganism = Integer.parseInt(filledForm.get().amountOfOrganism);
				String specie = filledForm.get().organism;
				String sex = filledForm.get().sex;
				String activityEnding = filledForm.get().activityEnding;
				
				Location location = new Location(filledForm.get().longitude, filledForm.get().latitude);
				
				FactorySector factorySector = null;
				AbstractActivity aActivity = null;
				
				if(sector.equals("hunting")){
					factorySector = new FactoryHuntingSector();
				}else if(sector.equals("fishing")){
					factorySector = new FactoryFishingSector();
				}else if(sector.equals("picking")){
					factorySector = new FactoryPickingSector();
				}
				
				String formattedDate = dateFormatter.format(date);
				User user = User.findByEmail(request().username());
				Key<User> creatorKey = MorphiaObject.datastore.getKey(user);
				
				aActivity = factorySector.createActivity(specie, amountOfOrganism, formattedDate, location, creatorKey);

				
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
				
				
				
				
				return ok(Json.toJson(aActivity));
		} 
		
	}*/
	
}