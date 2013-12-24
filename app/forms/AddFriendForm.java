package forms;

import controllers.Users;
import models.User;

public class AddFriendForm {
   
	public String email;
	
    public String validate() throws Exception {
        User userId = Users.findByEmail(email);
        if (userId == null)
            return "email doesn't exist";
        return null;
    }
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
