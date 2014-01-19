package controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import models.VitaOWL;
import models.VitaOWL;
import play.mvc.Controller;
import play.mvc.Result;

public class VitaCtrl extends Controller{
	
	public static Result make() throws FileNotFoundException{
		Model model = ModelFactory.createDefaultModel();
		OutputStream os = new FileOutputStream("test/data.rdf");
		RDFDataMgr.write(os, model, RDFFormat.RDFXML) ;
		return ok(new VitaOWL().toString());
	}
}
