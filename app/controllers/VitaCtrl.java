package controllers;

import java.io.FileNotFoundException;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
<<<<<<< HEAD
=======
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;
>>>>>>> sparql
import com.hp.hpl.jena.tdb.TDBFactory;

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
