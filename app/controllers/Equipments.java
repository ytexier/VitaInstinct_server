package controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Location;
import models.User;
import models.factory.AbstractEquipment;
import models.factory.AbstractEvent;
import models.factory.FactorySector;
import models.fishing.FactoryFishingSector;
import models.fishing.FishingEquipment;
import models.fishing.FishingEvent;
import models.hunting.FactoryHuntingSector;
import models.hunting.HuntingEquipment;
import models.hunting.HuntingEvent;
import models.picking.FactoryPickingSector;
import models.picking.PickingEquipment;
import models.picking.PickingEvent;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import agents.AgentJena;
import agents.AgentWriter;
import forms.AddEquipmentForm;
import forms.AddEventForm;
import forms.Secured;

public class Equipments extends Controller {
	
	static Form<AddEquipmentForm> equipementForm = Form.form(AddEquipmentForm.class);
	
	
	public static Result get(String id, String sector){
		
    	AbstractEquipment equipmentFound = null;
    	
    	if(sector.equals("hunting"))
    		equipmentFound = HuntingEquipment.findById(id);
     	if(sector.equals("picking"))
     		equipmentFound = PickingEquipment.findById(id);
     	if(sector.equals("fishing"))
     		equipmentFound = FishingEquipment.findById(id);
    	

   		if(request().accepts("text/html")){
   			return ok();
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(equipmentFound));
   		
   		else if (request().accepts("application/rdf+xml")){
   			OutputStream out = new ByteArrayOutputStream();
   			equipmentFound.accept(new AgentJena()).write(out, "RDF/XML-ABBREV");
   			return ok(out.toString());
   		}

		return ok(Json.toJson(equipmentFound));
	}
	

	@Security.Authenticated(Secured.class)
	public static Result addEquipment() throws Exception {

		Form<AddEquipmentForm> filledForm = equipementForm.bindFromRequest();

		if(filledForm.hasErrors()) {
			return badRequest("newEquipment : filledForm.hasErrors()");
		}
		else {

			String sector = filledForm.get().sector;
			String comment = filledForm.get().comment;
			String label = filledForm.get().label;

			FactorySector factorySector = null;
			AbstractEquipment aEquipment = null;

			if(sector.equals("hunting")){
				factorySector = new FactoryHuntingSector();
			}else if(sector.equals("fishing")){
				factorySector = new FactoryFishingSector();
			}else if(sector.equals("picking")){
				factorySector = new FactoryPickingSector();
			}

			User user = User.findByEmail(request().username());
			Key<User> creatorKey = MorphiaObject.datastore.getKey(user);

			aEquipment =
					factorySector.createEquipment(label, comment, creatorKey);

			Key<AbstractEquipment> eventKey = MorphiaObject.datastore.save(aEquipment);

			UpdateResults<User> res =
					MorphiaObject.datastore.update(
							user,
							MorphiaObject.datastore.createUpdateOperations(User.class).add("equipments", eventKey)
							);
			
			aEquipment.accept(new AgentWriter());

			return redirect(routes.Application.index());
		}

	}

}
