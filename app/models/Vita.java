package models;

import play.Logger;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class Vita {
	
	public static final String vitaURL="vita-instinct.herokuapp.com/";
	public static Model vita_model = ModelFactory.createDefaultModel();
	
	public static void printStatements(){
		Logger.debug("[[[[MODEL PRINT STATEMENTS]]]]");
		// list the statements in the Model
		StmtIterator iter = vita_model.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) 
		{
			Statement stmt      = iter.nextStatement();  // get next statement
			Resource  subject   = stmt.getSubject();     // get the subject
			Property  predicate = stmt.getPredicate();   // get the predicate
			RDFNode   object    = stmt.getObject();      // get the object

			Logger.debug(subject.toString());
			Logger.debug(" " + predicate.toString() + " ");
			if (object instanceof Resource) {
				Logger.debug(object.toString());
			} else {
				// object is a literal
				Logger.debug(" \"" + object.toString() + "\"");
			}

			Logger.debug(" .");
		} 
	}
}
