package models;

import agents.AgentManager;
import controllers.MorphiaObject;
import models.factory.AbstractActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

import java.util.ArrayList;
import java.util.Date;


@Entity public class User{

	
	@Id 				private ObjectId id;
	
    @Required @Email 	private String email;
    @Required 			private String password;
    
    					private String URI;
    					
    					private String fullName;
    					private String givenName;
    					private String familyName;
    					private String nickName;
    					
    					private Date registration;
    					
    @Reference 			private ArrayList<AbstractActivity> activities; 
    @Reference 			private ArrayList<User> friends;


	public User(String givenName, String familyName, String nickName, String email, String password, Date registration){
		
		this.setURI(Vita.vita_ns+givenName+"."+familyName);
		
		this.setFullName(givenName + " " + familyName);
		this.setGivenName(givenName);
		this.setFamilyName(familyName);
		this.setNickName(nickName);	

        this.setEmail(email);
        this.setPassword(password);
        this.setRegistration(registration);
        
        this.setActivities(new ArrayList<AbstractActivity>());
        this.setFriends(new ArrayList<User>());
    }
    
    public User() {
    	
    	this.setURI("");
    	
    	this.setFullName("");
    	this.setGivenName("");
    	this.setFamilyName("");
    	
    	this.setEmail("");
    	this.setPassword("");
      	this.setRegistration(new Date());
  
      	this.setActivities(new ArrayList<AbstractActivity>());
    	this.setFriends(new ArrayList<User>());
    	
	}
    
	public void accept(AgentManager v){
		v.spy(this);
	}
    
    public static User findById(String id){
    	User user = MorphiaObject.datastore.find(User.class)
    			.field("_id")
    			.equal(new ObjectId(id))
    			.get();
    	return user;
    }
    
    public static User findByEmail(String email){
   		User user = MorphiaObject.datastore.find(User.class)
   				.field("email")
            	.equal(email)
            	.get();
    	return user;
    }

	public static User authenticate(String email, String password) {
		User user = MorphiaObject.datastore.find(User.class)
				.field("email").equal(email)
				.field("password").equal(password)
				.get();
		return user;
	}
   
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public Date getRegistration() {
		return registration;
	}
	public void setRegistration(Date registration) {
		this.registration = registration;
	}
	public ArrayList<AbstractActivity> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList<AbstractActivity> activities) {
		this.activities = activities;
	}
	public ArrayList<User> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}