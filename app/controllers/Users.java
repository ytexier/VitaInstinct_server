package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

import agents.AgentJena;
import forms.AddFriendForm;
import forms.Secured;
import models.User;
import models.factory.AbstractActivity;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.login;

public class Users extends Controller{
	
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
                
                user.accept(new AgentJena());
                
                return redirect(routes.Application.signup());  
        }
    }
    
    @Security.Authenticated(Secured.class)
    public static Result addFriend() throws Exception{
    	Form<AddFriendForm> filledForm = addFriendFrom.bindFromRequest();
        if(filledForm.hasErrors()) {
        	//TODO
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
   			//TODO
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(user));
   		
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}
   		
		return ok(Json.toJson(user));
    }
    
    
    public static Result timeLine(String id) throws Exception{
    	
        User user = User.findById(id);
        
    	List<AbstractActivity> allActivities = user.getActivities();
        
   		for(User friend : user.getFriends())
   			allActivities.addAll(friend.getActivities());

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
    	
    	User user = User.findById(id);
        
    	List<User> friends = user.getFriends();

   		if(request().accepts("text/html")){
   			//TODO
   		}
   		
   		else if(request().accepts("application/json"))
            return ok(Json.toJson(friends));
   		
   		else if (request().accepts("application/rdf+xml")){
   			//TODO
   		}

		return ok(Json.toJson(friends));
    }
    
    

	
	

}
