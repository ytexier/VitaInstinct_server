package controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import agents.AgentJena;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;

import models.VitaOWL;
import models.fishing.FishingActivity;
import models.hunting.HuntingActivity;
import models.picking.PickingActivity;
import play.mvc.Controller;
import play.mvc.Result;

public class VitaCtrl extends Controller{
	
	public static String db_hunting = "db/huntingSector.rdf";
	public static String db_fishing = "db/fishingSector.rdf";
	public static String db_picking = "db/pickingSector.rdf";


	public static Result make() throws FileNotFoundException{

		String directory = "dataset" ;
		Dataset dataset = TDBFactory.createDataset(directory) ;
		dataset.begin(ReadWrite.READ) ;
		Model model = dataset.getDefaultModel();
		model.write(System.out, "RDF/XML-ABBREV");
		dataset.end() ;

		return ok(new VitaOWL().toString());
		
	}
	
	public static Result writeRDF(Model newModel, String sector){

		if(sector.equals("hunting"))
    		loadWriteDB(newModel, db_hunting);
     	if(sector.equals("picking"))
     		loadWriteDB(newModel ,db_picking);
     	if(sector.equals("fishing"))
     		loadWriteDB(newModel, db_fishing);
     	
		
		return ok();
	}
	
	
	/**
	 * 
	 * @param model
	 */
	private static void writeOutput(Model model, String db){
		OutputStream os = null;
        try {
			os = new FileOutputStream(db);
			RDFDataMgr.write(os, model, RDFFormat.RDFXML_ABBREV) ;
			os.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param model 
	 */
	private static void loadWriteDB(Model model, String db){
		Model model_loaded = FileManager.get().loadModel(db);
		model = ModelFactory.createUnion(model_loaded, model);
		writeOutput(model, db);
	}
	

	public static Result test(String organism) {
		Model model = ModelFactory.createDefaultModel();
		String result = "Sanglier\n";
		try {
			String service = "http://fr.dbpedia.org/sparql";
			String query = ""
					+ "PREFIX db-owl: <http://dbpedia.org/ontology/>"
					+ "PREFIX dbpedia: <http://fr.dbpedia.org/resource/>"
					+ "select ?resume ?image where { "
					+ "dbpedia:"+organism+" db-owl:abstract ?resume."
					+ "dbpedia:"+organism+" db-owl:thumbnail ?image."
					+ "filter(langMatches(lang(?resume), \"fr\"))"
					+ "}"
					;
			QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
			try {
				ResultSet results = qe.execSelect();
				for (; results.hasNext();) {
					result += results.next().toString()+"\n";
					// Result processing is done here.
				}
			} catch (QueryExceptionHTTP e) {
				System.out.println(service + " is DOWN");
			} finally {
				qe.close();
			} 


		}
		catch(Exception e) {
			String sss = e.getMessage();
			for(StackTraceElement el : e.getStackTrace()) {
				sss += "\n"+el.toString();
			}
			return ok(sss);
		}

		return ok(result);
	} 
}
