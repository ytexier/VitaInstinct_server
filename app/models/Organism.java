package models;


import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import play.mvc.Result;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

import agents.AgentManager;


public abstract class Organism {
	@Id
	@Property("id")
	private ObjectId id;
	private String specie;
	@Embedded
	private Sex sex;
	
	private String _abstract;
	private String thumbnail;
	
	private String uri;
    
	public void accept(AgentManager v){
		v.spy(this);
	}
	
	
	public Organism(){}
	
	public Organism(String specie, String thumbnail, String _abstract){
		this.specie = specie;
		this.thumbnail = thumbnail;
		this._abstract = _abstract;
		this.uri = Vita.getURL() + "organism/";
	}
	
	public Organism(String specie, String thumbnail, String _abstract, Sex _sex){
		this(specie, thumbnail, _abstract);
		sex = _sex;
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}


	public String getURI() {
		return uri;
	}


	public void setURI(String url) {
		uri = url;
	}
	
	public static ArrayList<String> getInfos(String organism) {
		ArrayList<String> data = new ArrayList<String>();
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
					QuerySolution qs = results.next();
					String thumbnail = qs.get("image").toString();
					String _abstract = qs.get("resume").toString();
					data.add(thumbnail);
					data.add(_abstract);
					return data;
				}
			} catch (QueryExceptionHTTP e) {
				System.out.println(service + " is DOWN");
			} finally {
				qe.close();
			} 


		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;

	}


	public String get_abstract() {
		return _abstract;
	}


	public void set_abstract(String _abstract) {
		this._abstract = _abstract;
	}


	public String getThumbnail() {
		return thumbnail;
	}


	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	} 
}
