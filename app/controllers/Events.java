package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.ActivityEnding;
import models.Location;
import models.Sex;
import models.User;
import models.factory.AbstractActivity;
import models.factory.AbstractEvent;
import models.factory.FactorySector;
import models.fishing.FactoryFishingSector;
import models.hunting.FactoryHuntingSector;
import models.picking.FactoryPickingSector;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import agents.AgentJena;
import forms.AbstractActivityForm;
import forms.AddEventForm;
import forms.Secured;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class Events extends Controller {

	static Form<AddEventForm> eventForm = Form.form(AddEventForm.class);

	@Security.Authenticated(Secured.class)
	public static Result addEvent() throws Exception {

		Form<AddEventForm> filledForm = eventForm.bindFromRequest();

		if(filledForm.hasErrors()) {
			return badRequest("newEvent : filledForm.hasErrors()");
		}
		else {

			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

			String sector = filledForm.get().sector;
			String comment = filledForm.get().comment;
			Date date =	filledForm.get().date;

			Location location = new Location(filledForm.get().longitude, filledForm.get().latitude);

			FactorySector factorySector = null;
			AbstractEvent aEvent = null;
			String sss = ""+sector+" "+comment;

			if(sector.equals("hunting")){
				factorySector = new FactoryHuntingSector();
				sss += 1;
			}else if(sector.equals("fishing")){
				factorySector = new FactoryFishingSector();
				sss += 2;
			}else if(sector.equals("picking")){
				factorySector = new FactoryPickingSector();
				sss += 3;
			}

			String formattedDate = dateFormatter.format(date);
			sss += formattedDate;
			User user = User.findByEmail(request().username());
			Key<User> creatorKey = MorphiaObject.datastore.getKey(user);

			try {
				aEvent = factorySector.createEvent(formattedDate, comment, location);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ok(sss);
			}

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