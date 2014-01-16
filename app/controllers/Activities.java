package controllers;

import java.text.SimpleDateFormat;

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

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import exceptions.UnknownSectorException;
import forms.AbstractActivityForm;
import forms.Secured;

public class Activities extends Controller {
	
	static Form<AbstractActivityForm> abstractActivityForm = Form.form(AbstractActivityForm.class);

	public static Result getFromHunt(String activity_id) throws Exception {
		AbstractActivity activityFound = MorphiaObject.datastore.find(HuntingActivity.class)
				.field("_id")
        		.equal(new ObjectId(activity_id))
        		.get();
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
		AbstractActivity activityFound = MorphiaObject.datastore.find(PickingActivity.class)
				.field("_id")
        		.equal(new ObjectId(activity_id))
        		.get();
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
		AbstractActivity activityFound = MorphiaObject.datastore.find(FishingActivity.class)
				.field("_id")
        		.equal(new ObjectId(activity_id))
        		.get();
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
	
	public static Result add(String user_id) throws Exception{
        
		Form<AbstractActivityForm> filledForm = abstractActivityForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest();
        }
        else {
        	
	        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
	        	
				String sector = filledForm.get().sector;
				String strDate = filledForm.get().date;
				String strLatitude = filledForm.get().latitude;
				String strLongitude = filledForm.get().longitude;
				String strAmountOfOrganism = filledForm.get().amountOfOrganism;
				String strOrganism = filledForm.get().organism;
				String strSex = filledForm.get().sex;
				String strActivityEnding = filledForm.get().activityEnding;
				
				Location myLocation = new Location(strLatitude, strLongitude);
				
				FactorySector aSector = null;
				AbstractActivity aActivity = null;
				Organism organism = null;
				
				
				if(sector.equals("hunting")){
					aSector = new FactoryHuntingSector();
					organism = new Mammal(strOrganism);
				}
				
				if(sector.equals("fishing")){
					aSector = new FactoryFishingSector();
					organism = new Fish(strOrganism);				
				}
				
				if(sector.equals("picking")){
					aSector = new FactoryPickingSector();
					organism = new Plant(strOrganism);
				}
				
				aActivity = aSector.createActivity();
			
				if(Sex.contains(strSex))
					organism.setSex(Enum.valueOf(Sex.class, strSex));
				
				aActivity.setOrganism(organism);
				aActivity.setDate(dateFormatter.parse(strDate));
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
				
				Key<AbstractActivity> activityKey = MorphiaObject.datastore.save(aActivity);

				UpdateResults<User> res =
						MorphiaObject.datastore.update(
								user,
								MorphiaObject.datastore.createUpdateOperations(User.class).add("activities", activityKey)
						);
				
       	
				return ok(Json.toJson(aActivity));
		} 
		
	}	
	

	
	@Security.Authenticated(Secured.class)
	public static Result newActivity() throws Exception{
        
		Form<AbstractActivityForm> filledForm = abstractActivityForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest("newActivity : filledForm.hasErrors()");
        }
        else {
        	
	        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
	        	
				String sector = filledForm.get().sector;
				String strDate =	filledForm.get().date;
				String strLatitude = filledForm.get().latitude;
				String strLongitude = filledForm.get().longitude;
				String strAmountOfOrganism = filledForm.get().amountOfOrganism;
				String strOrganism = filledForm.get().organism;
				String strSex = filledForm.get().sex;
				String strActivityEnding = filledForm.get().activityEnding;
				
				Location myLocation = new Location(strLatitude, strLongitude);
				
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
				

				
				if(Sex.contains(strSex))
					organism.setSex(Enum.valueOf(Sex.class, strSex));
				
				aActivity.setOrganism(organism);
				aActivity.setDate(dateFormatter.parse(strDate));
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
				
				Key<AbstractActivity> activityKey = MorphiaObject.datastore.save(aActivity);

				UpdateResults<User> res =
						MorphiaObject.datastore.update(
								user,
								MorphiaObject.datastore.createUpdateOperations(User.class).add("activities", activityKey)
						);
				
				return redirect(routes.Application.index());
		} 
		
	}
	
}