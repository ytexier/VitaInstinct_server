package models.hunting;

import models.User;

import org.mongodb.morphia.Key;


public class HuntingAccessory extends HuntingEquipment{

	public HuntingAccessory(String label, String comment, Key<User> creator) {
		super(label, comment, creator);
		// TODO Auto-generated constructor stub
	}

	
}
