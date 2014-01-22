package controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Location;
import models.User;
import models.factory.AbstractActivity;
import models.factory.AbstractEvent;
import models.factory.FactorySector;
import models.fishing.FactoryFishingSector;
import models.fishing.FishingActivity;
import models.fishing.FishingEvent;
import models.hunting.FactoryHuntingSector;
import models.hunting.HuntingActivity;
import models.hunting.HuntingEvent;
import models.picking.FactoryPickingSector;
import models.picking.PickingActivity;
import models.picking.PickingEvent;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import agents.AgentJena;
import forms.AddEventForm;
import forms.Secured;

public class Events extends Controller {

	static Form<AddEventForm> eventForm = Form.form(AddEventForm.class);
	
	public static Result get(String id, String sector){
		
    	AbstractEvent eventFound = null;
    	
    	if(sector.equals("hunting"))
    		eventFound = HuntingEvent.findById(id);
     	if(sector.equals("picking"))
     		eventFound = PickingEvent.findById(id);
     	if(sector.equals("fishing"))
     		eventFound = FishingEvent.findById(id);
    	

   		if(request().accepts("text/html")){
   			return ok();
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(eventFound));
   		
   		else if (request().accepts("application/rdf+xml")){
   			OutputStream out = new ByteArrayOutputStream();
   			eventFound.accept(new AgentJena()).write(out, "RDF/XML-ABBREV");
   			return ok(out.toString());
   		}

		return ok(Json.toJson(eventFound.getCreator()));
	}

	@Security.Authenticated(Secured.class)
	public static Result addEvent() throws Exception {

		Form<AddEventForm> filledForm = eventForm.bindFromRequest();

		if(filledForm.hasErrors()) {
			return badRequest("newEvent : filledForm.hasErrors()");
		}
		else {

			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

			String sector = filledForm.get().sector;
			String label = filledForm.get().label;
			String comment = filledForm.get().comment;
			Date date =	filledForm.get().date;

			Location location = new Location(filledForm.get().longitude, filledForm.get().latitude);

			FactorySector factorySector = null;
			AbstractEvent aEvent = null;

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

			aEvent =
					factorySector.createEvent(label, formattedDate, comment, location, creatorKey);

			Key<AbstractEvent> eventKey = MorphiaObject.datastore.save(aEvent);

			UpdateResults<User> res =
					MorphiaObject.datastore.update(
							user,
							MorphiaObject.datastore.createUpdateOperations(User.class).add("events", eventKey)
							);

			aEvent.accept(new AgentJena());

			return redirect(routes.Application.index());
		}

	}

}
