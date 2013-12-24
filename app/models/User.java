package models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.factory.AbstractActivity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;


@Entity
public class User{
	@Id 
    private ObjectId id;
    @Required @Email
    private String email;
    @Required
    private String password;
    private String pseudo;
    private Date registration;
    @Reference
    private ArrayList<AbstractActivity> activities; 
    @Reference
    private ArrayList<User> friends;

	public User(String _pseudo, String _email, String _password, Date _registration){
    	this.pseudo = _pseudo;
        this.email = _email;
        this.password = _password;
        this.registration = _registration;
        this.activities = new ArrayList<AbstractActivity>();
        this.friends = new ArrayList<User>();
    }
    
    public User() {
    	this.pseudo = "";
        this.email = "";
        this.password = "";
        this.registration = new Date();
        this.activities = new ArrayList<AbstractActivity>();
        this.friends = new ArrayList<User>();
	}
    
    public List<String> getNS() {
        ArrayList<String> nss = new ArrayList<String>();
        nss.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
        nss.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
        nss.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
        nss.add("xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"");
        nss.add("xmlns:sioc=\"http://rdfs.org/sioc/ns#\"");
        return nss;
    }
    
    public String toRDF(String url_user, String rdf_toInsert, ArrayList<String> urls_seeAlso) 
    {
            String rdf = "";
            
            rdf += 	"<sioc:UserAccount rdf:about=\""+url_user+"?id="+id+"\">"
            		+	"<sioc:email>"+email+"</sioc:email>"
            		+ 	"<rdfs:label>"+pseudo+"</rdfs:label>"
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
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
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