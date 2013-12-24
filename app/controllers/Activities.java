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
import models.factory.AbstractSector;
import models.fishing.FishingSector;
import models.hunting.HuntingSector;
import models.picking.PickingSector;

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

	public static Result listActivities(String userId) throws Exception {
   		User user = MorphiaObject.datastore.find(User.class)
        		.field("id").equal(userId).get();
   		return (Result) Json.toJson(user.getActivities());
	}
	
	public static Result listHuntingActivities(String userId) throws Exception {
   		User user = MorphiaObject.datastore.find(User.class)
        		.field("id").equal(userId).get();
   		return (Result) Json.toJson(user.getActivities());
	}
	
	public static Result listFishingActivities(String userId) throws Exception {
   		User user = MorphiaObject.datastore.find(User.class)
        		.field("id").equal(userId).get();
   		return (Result) Json.toJson(user.getActivities());
	}
	
	public static Result listPickingActivities(String userId) throws Exception {
   		User user = MorphiaObject.datastore.find(User.class)
        		.field("id").equal(userId).get();
   		return (Result) Json.toJson(user.getActivities());
	}

	@Security.Authenticated(Secured.class)
	public static Result newActivity() throws Exception{
        
		Form<AbstractActivityForm> filledForm = abstractActivityForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest();
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
						organism = new Mammal(strOrganism, Enum.valueOf(Sex.class, strSex));
		                break;
		                
		            //FISHING    
					case 1:
						
						aSector = new FishingSector();
						aActivity = aSector.createActivity();
						organism = new Fish(strOrganism, Enum.valueOf(Sex.class, strSex));
		                break;
		                
					//PICKKING	
					case 2:
						
						aSector = new PickingSector();
						aActivity = aSector.createActivity();
						organism = new Plant(strOrganism, Enum.valueOf(Sex.class, strSex));
		                break;

					default:
						throw new UnknownSectorException();
				}
				
				aActivity.setOrganism(organism);
				aActivity.setDate(dateFormatter.parse(strDate));
				aActivity.setLocation(myLocation);
				aActivity.setAmountOfOrganism(Integer.parseInt(strAmountOfOrganism));
				aActivity.setActivityEnding(Enum.valueOf(ActivityEnding.class, strActivityEnding));

				User user = Users.findByEmail(request().username());
				
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