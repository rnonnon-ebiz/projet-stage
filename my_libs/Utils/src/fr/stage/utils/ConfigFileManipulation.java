package fr.stage.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileManipulation {

    public static Properties readConfFileAndFill(String fileName) {
	Properties prop = new Properties();
	try {
	    // Read propertie file
	    FileInputStream file = new FileInputStream(fileName);
	    prop.load(file);
	}
	catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return prop;
    }

}
