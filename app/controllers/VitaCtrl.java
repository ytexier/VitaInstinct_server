package controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;

import models.VitaOWL;
import models.VitaOWL;
import play.mvc.Controller;
import play.mvc.Result;

public class VitaCtrl extends Controller{
	
	public static Result make() throws FileNotFoundException{
		
        String directory = "dataset" ;
        Dataset dataset = TDBFactory.createDataset(directory) ;
        dataset.begin(ReadWrite.READ) ;
        Model model = dataset.getDefaultModel();
        model.write(System.out, "RDF/XML-ABBREV");
        dataset.end() ;
		
		
		return ok(new VitaOWL().toString());
	}
}
