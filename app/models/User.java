package models;

import controllers.MorphiaObject;
import models.factory.AbstractActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import java.util.ArrayList;
import java.util.Date;


@Entity public class User{

	
	@Id 				private ObjectId id;
    @Required @Email 	private String email;
    @Required 			private String password;
    					private String username;
    					private Date registration;
    @Reference 			private ArrayList<AbstractActivity> activities; 
    @Reference 			private ArrayList<User> friends;


	public User(String _username, String _email, String _password, Date _registration){
    	this.username = _username;
        this.email = _email;
        this.password = _password;
        this.registration = _registration;
        this.activities = new ArrayList<AbstractActivity>();
        this.friends = new ArrayList<User>();
    }
    
    public User() {
    	this.username = "";
        this.email = "";
        this.password = "";
        this.registration = new Date();
        this.activities = new ArrayList<AbstractActivity>();
        this.friends = new ArrayList<User>();
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
	
    
    public String toRDF(String url_user, String rdf_toInsert, ArrayList<String> urls_seeAlso) 
    {
            String rdf = "";
            
            rdf += 	"<sioc:UserAccount rdf:about=\""+url_user+"?id="+id+"\">"
            		+	"<sioc:email>"+email+"</sioc:email>"
            		+ 	"<rdfs:label>"+username+"</rdfs:label>"
            		+	"<dcterms:created>"+registration+"</dcterms:created>";
            
            if(urls_seeAlso != null && urls_seeAlso.size()>0)
            	for(String url_seeAlso : urls_seeAlso)
            		rdf.concat(toSeeAlso(url_seeAlso));
             
            if(rdf_toInsert != null && rdf_toInsert.length() > 0) 
            	rdf += rdf_toInsert;
            
            rdf += "</sioc:UserAccount>";
            
            return rdf;
    }
    
    
    public String toSeeAlso(String url_seeAlso)
    {
            String rdf = "";
            rdf += "<rdfs:seeAlso rdf:resource=\""+url_seeAlso+"?id="+id.toString()+"\"/>";
            return rdf ;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
}