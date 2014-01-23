package controllers;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

import agents.AgentJena;
import agents.AgentWriter;
import forms.AddFriendForm;
import forms.Secured;
import models.User;
import models.factory.AbstractActivity;
import models.fishing.FishingActivity;
import models.hunting.HuntingActivity;
import models.picking.PickingActivity;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


public class Users extends Controller{
	
	public static String db_users 		= "db/user/db_users.rdf";
	
	static Form<User> userForm = Form.form(User.class);
	static Form<AddFriendForm> addFriendFrom = Form.form(AddFriendForm.class);
		
    public static Result delete(String id) throws Exception{
        User user = User.findById(id);
        if (user != null)
        	MorphiaObject.datastore.delete(user);
        return redirect(routes.Application.signup());
    }

	public static List<User> all() throws Exception{
        if (MorphiaObject.datastore != null)
                return MorphiaObject.datastore
                		.find(User.class).asList();
        else
        	return new ArrayList<User>();
    }
	
    public static Result newUser() throws Exception{
        Form<User> filledForm = userForm.bindFromRequest();
        if(filledForm.hasErrors()) {
                return badRequest();
        }
        else {
	        	Date toDay = new Date(); 
            	User user = new User(
            			filledForm.get().getGivenName(), 
            			filledForm.get().getFamilyName(), 
            			filledForm.get().getNickName(),
            			filledForm.get().getEmail(), 
            			filledForm.get().getPassword(), 
            			toDay
            			);
                MorphiaObject.datastore.save(user);
                user.accept(new AgentWriter());
                return redirect(routes.Application.signup());  
        }
    }
    
    @Security.Authenticated(Secured.class)
    public static Result addFriend() throws Exception{
    	Form<AddFriendForm> filledForm = addFriendFrom.bindFromRequest();
        if(filledForm.hasErrors()) {
        	return ok("Error : Friend does not exist.");
	    }
	    else {
	    	String friend_email = filledForm.get().getEmail();
	    	User user = User.findByEmail(request().username());
	    	if(user!=null){
	    		User friend = User.findByEmail(friend_email);
	    		if(friend!=null && !friend.equals(user)){
		    		Key<User> friendKey = MorphiaObject.datastore.getKey(friend);
		    		UpdateResults<User> res =
		    				MorphiaObject.datastore.update(
		    						user,
		    						MorphiaObject.datastore
		    							.createUpdateOperations(User.class)
		    							.add("friends", friendKey)
		    				);  
	    		}
	    	}
	    }
        return redirect(routes.Application.index());
    }
    
     
    public static Result get(String id){
		
    	User user = User.findById(id);

   		if(request().accepts("text/html")){
   			return ok();
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(user));
   		
   		else if (request().accepts("application/rdf+xml")){
   			OutputStream out = new ByteArrayOutputStream();
   			user.accept(new AgentJena()).write(out, "RDF/XML-ABBREV");
   			return ok(out.toString());
   		}

		return ok(Json.toJson(user));
    }
    
	public static Result users(){
		
		String db = db_users;
		
    	AbstractActivity activityFound = null;

    	Model model_loaded = FileManager.get().loadModel(db);

   		if(request().accepts("text/html")){
   			return ok();
   		}
   		
   		else if(request().accepts("application/json"))
			try {
				return ok(Json.toJson(all()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if (request().accepts("application/rdf+xml")){
   			OutputStream out = new ByteArrayOutputStream();
   			model_loaded.write(out, "RDF/XML-ABBREV");
   			return ok(out.toString());
   		}

		return ok(Json.toJson(activityFound));
	}
    
    
    
    public static Result timeLine(String id) throws Exception{
    	
        User user = User.findById(id);
        
    	List<AbstractActivity> allActivities = user.getActivities();
        
   		for(Key<User> friend : user.getFriends()){
   			User friendFound = User.findById(friend.getId().toString());
   			allActivities.addAll(friendFound.getActivities());
   		}

   		if(request().accepts("text/html")){
   			//TODO
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(allActivities));
   		
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}

		return ok(Json.toJson(allActivities));
    }
    
    public static Result activities(String id) throws Exception{
    	
    	User user = User.findById(id);
        
    	List<AbstractActivity> activities = user.getActivities();

   		if(request().accepts("text/html")){
   			//TODO
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(activities));
   		
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}

		return ok(Json.toJson(activities));
    }
    
    public static Result friends(String id) throws Exception{
    	
    	ArrayList<User> allFriends = new ArrayList<User>();	
    	User user = User.findById(id);
        
   		for(Key<User> friend : user.getFriends()){
   			User friendFound = User.findById(friend.getId().toString());
   			allFriends.add(friendFound);
   		}

   		if(request().accepts("text/html")){
   			//TODO
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(allFriends));
   		
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}

		return ok(Json.toJson(allFriends));
    }
    
    

	
	

}
