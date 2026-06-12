package genericUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesFileUtility {

	public String toReadDataFromPropertiesFile(String key) throws Throwable
	{
		FileInputStream fis = new FileInputStream("./src/test/resources/Commondata.properties");
		Properties prop = new Properties(); //creating file type object
		prop.load(fis); //loading all the keys
		//storing all value of the keys in a variable
		String value = prop.getProperty(key);
		return value;//returning the value of the key
	}
}

