package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Equipment {
	
	@Id private ObjectId id;
	private String label;
	private String comment;
	private String uri;
	@Embedded  private Key<User> creator;
	
	public Equipment(){
	}
	
	public Equipment(String label, String comment, Key<User> creator){
		this.label = label;
		this.comment = comment;		
		this.creator = creator;
		this.setURI(Vita.getURL()+"equipment/");
	}
	/*
	public Model accept(AgentManager v){
		v.spy(this);
	}
	*/
    
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Key<User> getCreator() {
		return creator;
	}

	public void setCreator(Key<User> creator) {
		this.creator = creator;
	}
	

}
