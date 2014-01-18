package controllers;

import models.VitaOWL;
import models.VitaOWL;
import play.mvc.Controller;
import play.mvc.Result;

public class VitaCtrl extends Controller{
	public static Result make(){
		return ok(new VitaOWL().toString());
	}
}
