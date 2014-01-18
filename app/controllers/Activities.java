package controllers;

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
import models.factory.FactorySector;
import models.fishing.FishingActivity;
import models.fishing.FactoryFishingSector;
import models.hunting.HuntingActivity;
import models.hunting.FactoryHuntingSector;
import models.picking.PickingActivity;
import models.picking.FactoryPickingSector;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import forms.AbstractActivityForm;
import forms.Secured;

public class Activities extends Controller {
	
	static Form<AbstractActivityForm> abstractActivityForm = Form.form(AbstractActivityForm.class);

	
    public static Result creator(String id, String sector) throws Exception{
    	AbstractActivity activityFound = null;
    	if(sector.equals("hunting")){
    		activityFound = HuntingActivity.findById(id);
    	}
    	
    	if(sector.equals("picking")){
    		activityFound = PickingActivity.findById(id);
    	}
    	
    	if(sector.equals("fishing")){
    		activityFound = FishingActivity.findById(id);
    	}

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
	
	
	public static Result getFromHunt(String activity_id) throws Exception {
		AbstractActivity activityFound = HuntingActivity.findById(activity_id);
   		if(request().accepts("text/html")){
   			//TODO
   		}
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(activityFound));
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}
   		return ok(Json.toJson(activityFound));
	}
	
	public static Result getFromPick(String activity_id) throws Exception {
		AbstractActivity activityFound = PickingActivity.findById(activity_id);
   		if(request().accepts("text/html")){
   			//TODO
   		}
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(activityFound));
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}
   		return ok(Json.toJson(activityFound));
	}
	
	public static Result getFromFish(String activity_id) throws Exception {
		AbstractActivity activityFound = FishingActivity.findById(activity_id);
   		if(request().accepts("text/html")){
   			//TODO
   		}
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(activityFound));
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}
   		return ok(Json.toJson(activityFound));
	}
	
	

	
	@Security.Authenticated(Secured.class)
	public static Result newActivity() throws Exception{
        
		Form<AbstractActivityForm> filledForm = abstractActivityForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest("newActivity : filledForm.hasErrors()");
        }
        else {
        	
	        	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
	        	
				String sector = filledForm.get().sector;
				
				
				Date date =	filledForm.get().date;			
				String strLatitude = filledForm.get().latitude;
				String strLongitude = filledForm.get().longitude;
				String strAmountOfOrganism = filledForm.get().amountOfOrganism;
				String strOrganism = filledForm.get().organism;
				String strSex = filledForm.get().sex;
				String strActivityEnding = filledForm.get().activityEnding;
				
				Location myLocation = new Location(strLongitude, strLatitude);
				
				FactorySector factorySector = null;
				
				AbstractActivity aActivity = null;
				Organism organism = null;
				
				
				if(sector.equals("hunting")){
					factorySector = new FactoryHuntingSector();
					organism = new Mammal(strOrganism);
				}else if(sector.equals("fishing")){
					factorySector = new FactoryFishingSector();
					organism = new Fish(strOrganism);
				}else if(sector.equals("picking")){
					factorySector = new FactoryPickingSector();
					organism = new Plant(strOrganism);
				}
				
				aActivity = factorySector.createActivity();
				aActivity.setSectorName(sector);

				
				if(Sex.contains(strSex))
					organism.setSex(Enum.valueOf(Sex.class, strSex));
				
				aActivity.setOrganism(organism);
				
				String formattedDate = dateFormatter.format(date);
				
				aActivity.setDate(formattedDate);
				
				
				aActivity.setLocation(myLocation);
				
			    try { 
			    	aActivity.setAmountOfOrganism(Integer.parseInt(strAmountOfOrganism));
			    } catch(NumberFormatException e) { 
			    	aActivity.setAmountOfOrganism(1);
			    }

				if(ActivityEnding.contains(strActivityEnding))
						aActivity.setActivityEnding(Enum.valueOf(ActivityEnding.class, strActivityEnding));

				User user = User.findByEmail(request().username());
				
				aActivity.setCreator(MorphiaObject.datastore.getKey(user));
				aActivity.setCreatorName(user.getFullName());
				
				Key<AbstractActivity> activityKey = MorphiaObject.datastore.save(aActivity);

				UpdateResults<User> res =
						MorphiaObject.datastore.update(
								user,
								MorphiaObject.datastore.createUpdateOperations(User.class).add("activities", activityKey)
						);
				
				return redirect(routes.Application.index());
<<<<<<< HEAD
				
		} 
=======
        } 
>>>>>>> master
		
	}
	
	public static Result add(String user_id) throws Exception{
        
		Form<AbstractActivityForm> filledForm = abstractActivityForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest("newActivity : filledForm.hasErrors()");
        }
        else {
        	
	        	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
	        	
				String sector = filledForm.get().sector;
				Date date =	filledForm.get().date;
				String strLatitude = filledForm.get().latitude;
				String strLongitude = filledForm.get().longitude;
				String strAmountOfOrganism = filledForm.get().amountOfOrganism;
				String strOrganism = filledForm.get().organism;
				String strSex = filledForm.get().sex;
				String strActivityEnding = filledForm.get().activityEnding;
				
				Location myLocation = new Location(strLongitude, strLatitude);
				
				FactorySector factorySector = null;
				
				AbstractActivity aActivity = null;
				Organism organism = null;
				
				if(sector.equals("hunting")){
					factorySector = new FactoryHuntingSector();
					organism = new Mammal(strOrganism);
				}else if(sector.equals("fishing")){
					factorySector = new FactoryFishingSector();
					organism = new Fish(strOrganism);
				}else if(sector.equals("picking")){
					factorySector = new FactoryPickingSector();
					organism = new Plant(strOrganism);
				}
				
				aActivity = factorySector.createActivity();
				aActivity.setSectorName(sector);

				
				if(Sex.contains(strSex))
					organism.setSex(Enum.valueOf(Sex.class, strSex));
				
				aActivity.setOrganism(organism);
				
				String formattedDate = dateFormatter.format(date);
				
				aActivity.setDate(formattedDate);
				
				
				aActivity.setLocation(myLocation);
				
			    try { 
			    	aActivity.setAmountOfOrganism(Integer.parseInt(strAmountOfOrganism));
			    } catch(NumberFormatException e) { 
			    	aActivity.setAmountOfOrganism(1);
			    }

				if(ActivityEnding.contains(strActivityEnding))
						aActivity.setActivityEnding(Enum.valueOf(ActivityEnding.class, strActivityEnding));

				User user = User.findById(user_id);
				
				aActivity.setCreator(MorphiaObject.datastore.getKey(user));
				aActivity.setCreatorName(user.getFullName());

				
				Key<AbstractActivity> activityKey = MorphiaObject.datastore.save(aActivity);

				UpdateResults<User> res =
						MorphiaObject.datastore.update(
								user,
								MorphiaObject.datastore.createUpdateOperations(User.class).add("activities", activityKey)
						);
				
				return ok(Json.toJson(aActivity));
		} 
		
	}
	
}