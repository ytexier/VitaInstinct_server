package models;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;


@Entity
public class Location{
	
	@Id
	private ObjectId id;
	private String latPos;
	private String longPos;
	
	public Location() {
	}
	
	public Location(String _longPos, String _latPos) {
		latPos = _latPos;
		longPos = _longPos;
	}
	
	public String getLatPos() {
		return latPos;
	}

	public void setLatPos(String latPos) {
		this.latPos = latPos;
	}

	public String getLongPos() {
		return longPos;
	}

	public void setLongPos(String longPos) {
		this.longPos = longPos;
	}
}

