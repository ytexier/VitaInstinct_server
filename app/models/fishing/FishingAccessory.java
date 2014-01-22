package models.fishing;

import models.User;

import org.mongodb.morphia.Key;


public class FishingAccessory extends FishingEquipment{
	
	public FishingAccessory(String label, String comment, Key<User> creator) {
		super(label, comment, creator);
		// TODO Auto-generated constructor stub
	}

}
