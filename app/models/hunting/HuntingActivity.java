package models.hunting;

import java.util.Date;

import models.ActivityEnding;
import models.Amniote;
import models.Location;
import models.factory.AbstractActivity;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class HuntingActivity extends AbstractActivity{

	public void setOrganism(Amniote _amniote){
		super.setOrganism(_amniote);
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
