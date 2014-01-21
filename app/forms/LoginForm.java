package forms;

import models.User;

public class LoginForm {

    private String email;
    private String password;
    
    public String validate() {
       	User userId = User.authenticate(email, password);
        if (userId == null)
        	return "Wrong user or password";
        return null;
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
    
}
