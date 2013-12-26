package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;

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

	public static List<User> all() throws Exception{
        if (MorphiaObject.datastore != null) {
                return MorphiaObject.datastore
                		.find(User.class).asList();
        }
        else {
                return new ArrayList<User>();
        }
    }
	
    public static Result get(String user_id){
        User userFound = MorphiaObject.datastore.find(User.class)
        		.field("_id")
        		.equal(new ObjectId(user_id))
        		.get();
   		return (Result) Json.toJson(userFound);
    }
    
    
    public static User findByEmail(String user_email) throws Exception{
   		User user = MorphiaObject.datastore.find(User.class)
            		.field("email")
            		.equal(user_email)
            		.get();
    	return user;
    }
    
    
    public static Result newUser() throws Exception{
        Form<User> filledForm = userForm.bindFromRequest();
        if(filledForm.hasErrors()) {
                return badRequest();
        }
        else {
	        	Date toDay = new Date(); 
            	User user = new User(
            			filledForm.get().getPseudo(), 
            			filledForm.get().getEmail(), 
            			filledForm.get().getPassword(), 
            			toDay
            			);
                MorphiaObject.datastore.save(user);
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
	    	User user = Users.findByEmail(request().username());
	    	if(user!=null){
	    		User friend = Users.findByEmail(friend_email);
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
    
    public static User getUserById(String user_id){
        User userFound = MorphiaObject.datastore.find(User.class)
        		.field("_id")
        		.equal(new ObjectId(user_id))
        		.get();
        if (userFound != null)
        	return userFound;
        return null;
    }
    
    public static Result deleteUser(String user_id) throws Exception{
        User toDelete = MorphiaObject.datastore.find(User.class)
        		.field("_id")
        		.equal(new ObjectId(user_id))
        		.get();
        if (toDelete != null)
        	MorphiaObject.datastore.delete(toDelete);
        return redirect(routes.Application.signup());
    }
    
	public static User authenticate(String user_email, String user_password) {
		User userFound = MorphiaObject.datastore.find(User.class)
				.field("email").equal(user_email)
				.field("password").equal(user_password)
				.get();
		if (userFound != null)
			return userFound;
		return null;
	}
	
    public static Result timeLine(String user_id) throws Exception{
    	
        User userFound = MorphiaObject.datastore.find(User.class)
        		.field("_id")
        		.equal(new ObjectId(user_id))
        		.get();
        
    	List<AbstractActivity> allActivities = userFound.getActivities();
        
   		for(User friend : userFound.getFriends())
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
    
    public static Result activities(String user_id) throws Exception{
    	
        User userFound = MorphiaObject.datastore.find(User.class)
        		.field("_id")
        		.equal(new ObjectId(user_id))
        		.get();
        
    	List<AbstractActivity> activities = userFound.getActivities();

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
	

}
