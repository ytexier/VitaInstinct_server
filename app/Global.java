//https://github.com/natoine/Argumentea/blob/master/ArgumenteaServer/app/Global.java


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;


import org.mongodb.morphia.Morphia;

import play.Application;
import play.GlobalSettings;
import play.Logger;

import com.mongodb.Mongo;

import controllers.MorphiaObject;

public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application application) {
		super.beforeStart(application);
		Logger.debug("** onStart **");
		Properties prop = new Properties();

		try {

			prop.load(new FileInputStream("conf/mongo.properties"));
			
			MorphiaObject.mongo = new Mongo(
					prop.getProperty("url"), 
					Integer.parseInt(prop.getProperty("port")));		
	        
			boolean auth = MorphiaObject.mongo
					.getDB(prop.getProperty("db"))
					.authenticate(
							prop.getProperty("user"), 
							prop.getProperty("pwd").toCharArray());
			
    	
		}catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MorphiaObject.morphia = new Morphia();
        MorphiaObject.datastore = MorphiaObject.morphia
        		.createDatastore(
        				MorphiaObject.mongo, 
        				prop.getProperty("db"));

        MorphiaObject.datastore.ensureIndexes();   
        MorphiaObject.datastore.ensureCaps(); 
        
    	Logger.debug("** Morphia datastore: " + MorphiaObject.datastore.getDB());
	}
}
