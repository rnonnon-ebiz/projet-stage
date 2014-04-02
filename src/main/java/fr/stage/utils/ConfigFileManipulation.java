package fr.stage.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileManipulation {

    public static Properties readConfFileAndFill(String fileName) {
	Properties prop = new Properties();
	try {
	    // Read propertie file
	    ClassLoader classLoader = Thread.currentThread()
		    .getContextClassLoader();
	    InputStream file = classLoader.getResourceAsStream(fileName);
	    prop.load(file);
	}
	catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return prop;
    }
}
