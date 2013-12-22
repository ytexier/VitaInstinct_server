package controllers;

import forms.AbstractActivityAllString;
import forms.AddUserForm;
import forms.LoginForm;
import models.User;
import views.html.*;
import play.Logger;
import play.data.*;
import play.mvc.*;

public class Application extends Controller {

	static Form<User> userForm = Form.form(User.class);
	static Form<LoginForm> loginForm = Form.form(LoginForm.class);
	static Form<AddUserForm> addUserForm = Form.form(AddUserForm.class);
	static Form<AbstractActivityAllString> activityFrom = Form.form(AbstractActivityAllString.class);
	
	
	@Security.Authenticated(forms.Secured.class)
	public static Result index() throws Exception {
	    return ok(
	    	index.render(Users.findByEmail(request().username()),activityFrom,addUserForm)
	    );
	}
	
    public static Result signup() throws Exception {
        return ok(
        	signup.render(Users.all(), userForm)
        );
    }
    
    public static Result login() {
        return ok(
            login.render(loginForm)
        );
    }
    
    public static Result logout() {
        session().clear();
        return redirect(
            routes.Application.login()
        );
    }
    
    public static Result authenticate() {
    	try{
	        Form<LoginForm> filledForm = loginForm.bindFromRequest();
	        if (filledForm.hasErrors()) {
	            return badRequest(login.render(filledForm));
	        } else {
	            session().clear();
	            session("email", filledForm.get().getEmail());
	            return redirect(
	                routes.Application.index()
	            );
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
}
