package forms;



import controllers.routes;
import play.Logger;
import play.mvc.*;
import play.mvc.Http.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
    	Logger.debug("**YES");
        return ctx.session().get("email");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
    	Logger.debug("**NO");
        return redirect(routes.Application.login());
    }
}