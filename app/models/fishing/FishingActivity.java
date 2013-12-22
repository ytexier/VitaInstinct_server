package models.fishing;

import java.util.Date;

import models.ActivityEnding;
import models.Fish;
import models.Location;
import models.factory.AbstractActivity;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class FishingActivity extends AbstractActivity{
	public void setFish(Fish _fish){
		super.setOrganism(_fish);
	}
	public void setDate(Date _date) {
		super.setDate(_date);		
	}
	
	public void setLocation(Location _location) {
		super.setLocation(_location);		
	}
	
	
	public void setActivityEnding(ActivityEnding _ActivityEnding) {
		super.setActivityEnding(_ActivityEnding);		
	}
	
	
	public void setAmountOfOrganism(Integer _amountOfOrganism){
		super.setAmountOfOrganism(_amountOfOrganism);
	}
}
