package models.picking;

import java.util.Date;

import models.ActivityEnding;
import models.Location;
import models.Plant;
import models.factory.AbstractActivity;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class PickingActivity extends AbstractActivity{

	public void setPlant(Plant _plant){
		super.setOrganism(_plant);
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
