package controllers;

import forms.AbstractActivityForm;
import forms.AddFriendForm;
import forms.LoginForm;
import forms.Secured;
import models.User;
import views.html.*;
import play.Logger;
import play.data.*;
import play.libs.Json;
import play.mvc.*;

public class Application extends Controller {

	static Form<User> userForm = Form.form(User.class);
	static Form<LoginForm> loginForm = Form.form(LoginForm.class);
	static Form<AddFriendForm> addFriendForm = Form.form(AddFriendForm.class);
	static Form<AbstractActivityForm> activityFrom = Form.form(AbstractActivityForm.class);
	
	
	@Security.Authenticated(Secured.class)
	public static Result index() throws Exception {
	    return ok(
	    	index.render(User.findByEmail(request().username()),activityFrom,addFriendForm)
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
		Form<LoginForm> filledForm = loginForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(login.render(filledForm));
		}
		else {
			if(request().accepts("text/html")) {
				try{
					session().clear();
					session("email", filledForm.get().getEmail());
					return redirect(
							routes.Application.index()
							);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			//JSON
			else if(request().accepts("application/json"))
			{
				try{
					session().clear();
					session("email", filledForm.get().getEmail());
					User rslt = User.findByEmail(filledForm.get().getEmail());
					return ok(Json.toJson(rslt));
				}catch(Exception e){
					e.printStackTrace();
				}
				return ok();
			}
		}


		return null;
	}
    	
    
}
