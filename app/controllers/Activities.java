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

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import play.Logger;
import play.data.DynamicForm;
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
    
    
	public static Result deleteHunting(String activity_id) throws Exception {
		AbstractActivity activityFound = HuntingActivity.findById(activity_id);
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
	
	public static Result deletePicking(String activity_id) throws Exception {
		AbstractActivity activityFound = PickingActivity.findById(activity_id);
        if (activityFound != null)
        	MorphiaObject.datastore.delete(activityFound);
        return redirect(routes.Application.index());
	}
	
	public static Result deleteFishing(String activity_id) throws Exception {
		AbstractActivity activityFound = FishingActivity.findById(activity_id);
        if (activityFound != null)
        	MorphiaObject.datastore.delete(activityFound);
        return redirect(routes.Application.index());
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
        	
	        	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
	        	
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
        }
	}
	

	public static Result add(String user_id) throws Exception{
        
		  DynamicForm requestData = Form.form().bindFromRequest();

		        ObjectId oid = new ObjectId(user_id);
		        UpdateOperations<User> ops;
		        Query<User> updateQuery = MorphiaObject.datastore.createQuery(User.class).field("_id").equal(oid);
		        String result = "";

		        if(requestData.hasErrors()) {
		        		return badRequest("filledForm.hasErrors()");
		        }
		        else {
		                AbstractActivity post = null;
		                FactorySector factorySector = null;
		                Organism organism = null;
		                
		                String sector = requestData.get("sector");
		                String day = requestData.get("day");
		                String mounth = requestData.get("month");
		                String year = requestData.get("year");
		                String strLatitude = requestData.get("latitude");
		                String strLongitude = requestData.get("longitude");
		                String strAmountOfOrganism = requestData.get("amountOfOrganism");
		                String strOrganism = requestData.get("organism");
		                String strSex = requestData.get("sex");
		                String strActivityEnding = requestData.get("activityEnding");
		                
		                
		                if(sector.contains("hunting")) {
		                 factorySector = new FactoryHuntingSector();
		                    organism = new Mammal(strOrganism);
		                }
		                else if(sector.contains("fishing")) {
		                 factorySector = new FactoryFishingSector();
		                    organism = new Fish(strOrganism);
		                }
		                else if(sector.contains("picking")){
		                 factorySector = new FactoryPickingSector();
		                    organism = new Plant(strOrganism);
		                }
		                
		                post = factorySector.createActivity();
		                
		                if(ActivityEnding.contains(strActivityEnding))
		                	post.setActivityEnding(Enum.valueOf(ActivityEnding.class, strActivityEnding));
		                if(Sex.contains(strSex))
                          organism.setSex(Enum.valueOf(Sex.class, strSex));
		                
		                try {
		                 post.setAmountOfOrganism(Integer.parseInt(strAmountOfOrganism));
		                }
		                catch(NumberFormatException nfe) {
		                 
		                }
		                
		                post.setLocation(new Location(strLongitude, strLatitude));
		                post.setOrganism(organism);
		               
		                
		                
		            	            
		                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
		                Date d = new Date(Integer.parseInt(year), 
		                  Integer.parseInt(mounth), 
		                  Integer.parseInt(day));
		                String fDate = dateFormatter.format(d);
		                
		                post.setDate(fDate);
		                
		                User user = User.findById(user_id);
		                
		                post.setCreator(MorphiaObject.datastore.getKey(user));
		                post.setCreatorName(user.getFullName());
		                
		                Key<AbstractActivity> activityKey = MorphiaObject.datastore.save(post);
		                result = activityKey.getId().toString();

		                UpdateResults<User> res =
		                        MorphiaObject.datastore.update(
		                                        user,
		                                        MorphiaObject.datastore.createUpdateOperations(User.class).add("activities",
		                                          activityKey)
		                        );

		                return ok(Json.toJson(post));
		        }
		  
		 }
	
}