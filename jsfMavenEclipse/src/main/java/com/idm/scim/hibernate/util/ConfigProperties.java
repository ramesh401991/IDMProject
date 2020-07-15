package com.idm.scim.hibernate.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

	public static String MYSQL_DRIVER;
	public static String MYSQL_URL;
	public static String MYSQL_USER;
	public static String MYSQL_PASS;
	public static String MYSQL_DIALECT;
	public static String MYSQL_SHOW_SQL;
	public static String MYSQL_CURRENT_SESSION_CONTEXT_CLASS;
 
	public static void LoadPropValues() throws IOException {
 
		InputStream stream = null;
		
		try {
			Properties prop = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();           
			stream = loader.getResourceAsStream("config.properties");
						
			if (stream != null) {
				prop.load(stream);
			} else {
				throw new FileNotFoundException("property file '" + "config.properties" + "' not found in the classpath");
			}
 
			// get the property value and print it out
			MYSQL_DRIVER = prop.getProperty(ConfigProps.MYSQL_DRIVER.toString());
			MYSQL_URL=prop.getProperty(ConfigProps.MYSQL_URL.toString());
			MYSQL_USER=prop.getProperty(ConfigProps.MYSQL_USER.toString());
			MYSQL_PASS=prop.getProperty(ConfigProps.MYSQL_PASS.toString());
			MYSQL_DIALECT=prop.getProperty(ConfigProps.MYSQL_DIALECT.toString());
			MYSQL_SHOW_SQL=prop.getProperty(ConfigProps.MYSQL_SHOW_SQL.toString());
			MYSQL_CURRENT_SESSION_CONTEXT_CLASS=prop.getProperty(ConfigProps.MYSQL_CURRENT_SESSION_CONTEXT_CLASS.toString());
			
		
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			stream.close();
		}
	}
	
}
