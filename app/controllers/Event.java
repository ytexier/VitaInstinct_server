package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.Location;
import forms.AddEventForm;
import forms.Secured;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Event extends Controller {
	
	public Form<AddEventForm> eventForm = Form.form(AddEventForm.class);
	

	@Security.Authenticated(Secured.class)
	public static Result newActivity() throws Exception{
        
		Form<AddEventForm> filledForm = eventForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
                return badRequest("newActivity : filledForm.hasErrors()");
        }
        else {
        	
        		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        	
				String sector = filledForm.get().sector;
				String date =	filledForm.get().date;
				
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
				

				
				aActivity.accept(new AgentJena());
				
				return redirect(routes.Application.index());
        }
	}

}
