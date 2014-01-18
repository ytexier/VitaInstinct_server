package controllers;

import models.Vita;
import models.Vita;
import play.mvc.Controller;
import play.mvc.Result;

public class VitaCtrl extends Controller{
	public static Result make(){
		return ok(new Vita().toString());
	}
}
