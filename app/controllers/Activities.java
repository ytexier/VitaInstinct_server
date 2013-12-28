package controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import models.ActivityEnding;
import models.Fish;
import models.Location;
import models.Mammal;
import models.Organism;
import models.Plant;
import models.Sex;
import models.User;
import models.factory.AbstractActivity;
import models.factory.AbstractSector;
import models.fishing.FishingActivity;
import models.fishing.FishingSector;
import models.hunting.HuntingActivity;
import models.hunting.HuntingSector;
import models.picking.PickingActivity;
import models.picking.PickingSector;

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
        	
			int intSector = filledForm.get().sector;//0:Hunt,1:Fish,2:Pick
			String strDate = filledForm.get().date;
			String strLatitude = filledForm.get().latitude;
			String strLongitude = filledForm.get().longitude;
			String strAmountOfOrganism = filledForm.get().amountOfOrganism;
			String strOrganism = filledForm.get().organism;
			String strSex = filledForm.get().sex;
			String strActivityEnding = filledForm.get().activityEnding;
			
			Location myLocation = new Location(strLatitude, strLongitude);
			
			AbstractSector aSector = null;
			AbstractActivity aActivity = null;
			Organism organism = null;
			
        	try {
				switch (intSector)
				{
					//HUNTING
					case 0:
						aSector = new HuntingSector();
						aActivity = aSector.createActivity();
						organism = new Mammal(strOrganism);
		                break;
		            //FISHING    
					case 1:
						aSector = new FishingSector();
						aActivity = aSector.createActivity();
						organism = new Fish(strOrganism);
		                break;
					//PICKKING	
					case 2:
						aSector = new PickingSector();
						aActivity = aSector.createActivity();
						organism = new Plant(strOrganism);
		                break;

					default:
						throw new UnknownSectorException();
				}
				
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
				
			} catch (UnknownSectorException e) {
				return ok("Invalid Sector.");
			}
        	
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
        	
			int intSector = filledForm.get().sector;
			String strDate =	filledForm.get().date;
			String strLatitude = filledForm.get().latitude;
			String strLongitude = filledForm.get().longitude;
			String strAmountOfOrganism = filledForm.get().amountOfOrganism;
			String strOrganism = filledForm.get().organism;
			String strSex = filledForm.get().sex;
			String strActivityEnding = filledForm.get().activityEnding;
			
			Location myLocation = new Location(strLatitude, strLongitude);
			
			AbstractSector aSector = null;
			AbstractActivity aActivity = null;
			Organism organism = null;
			
        	try {
				switch (intSector)
				{
					//HUNTING
					case 0:
						aSector = new HuntingSector();
						aActivity = aSector.createActivity();
						organism = new Mammal(strOrganism);
		                break;
		                
		            //FISHING    
					case 1:
						aSector = new FishingSector();
						aActivity = aSector.createActivity();
						organism = new Fish(strOrganism);
		                break;
		                
					//PICKKING	
					case 2:
						aSector = new PickingSector();
						aActivity = aSector.createActivity();
						organism = new Plant(strOrganism);
		                break;

					default:
						throw new UnknownSectorException();
				}
				
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
				
			} catch (UnknownSectorException e) {
				return ok("Invalid Sector.");
			}
        	
        	return redirect(routes.Application.index());
		} 
		
	}
	
}