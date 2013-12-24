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
	
    public static Result get(String id){
   		User user = MorphiaObject.datastore.find(User.class)
        		.field("id").equal(id).get();
   		return (Result) Json.toJson(user);
    }
    
    public static User findByEmail(String email) throws Exception{
   		User user = MorphiaObject.datastore.find(User.class)
            		.field("email").equal(email).get();
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
            	//return ok(user.getEmail());
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
	    	User user = findByEmail(request().username());
	    	if(user!=null){
	    		User friend = findByEmail(friend_email);
	    		if(friend!=null && !friend.equals(user)){
		    		Key<User> friendKey = MorphiaObject.datastore.getKey(friend);
		    		UpdateResults<User> res =
		    				MorphiaObject.datastore.update(
		    						user,
		    						MorphiaObject.datastore.createUpdateOperations(User.class).add("friends", friendKey)
		    				);  
	    		}
	    	}
	    }
        return redirect(routes.Application.index());
    }
    
    
    public static Result deleteUser(String _id) throws Exception{
        User toDelete = MorphiaObject.datastore.find(User.class)
        		.field("_id").equal(new ObjectId(_id)).get();
        if (toDelete != null)
        	MorphiaObject.datastore.delete(toDelete);
        return redirect(routes.Application.signup());
    }
    
    /*
     * Authenticate
     */
	public static User authenticate(String _email, String _password) {
		
		User userFound = MorphiaObject.datastore.find(User.class)
				.field("email").equal(_email)
				.field("password").equal(_password)
				.get();
		
		if (userFound != null)
			return userFound;

		return null;
	}
	

}
