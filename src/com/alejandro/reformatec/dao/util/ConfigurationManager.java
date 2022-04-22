package com.alejandro.reformatec.dao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class ConfigurationManager {

	private static Logger logger = LogManager.getLogger(ConfigurationManager.class.getName());

	private static ConfigurationManager instance = null;

	private Map<String, Properties> configurationsMap = null;


	private ConfigurationManager() { 
		configurationsMap = new HashMap<String, Properties>();
	};




	/**
	 * Singleton Thread-Safe.
	 */
	public static ConfigurationManager getInstance() {
		if (instance == null) {
			synchronized(ConfigurationManager.class) {
				if (instance == null) { // Necesario para proteger una segunda instanciación
					instance = new ConfigurationManager();
				}
			}
		}
		return instance;    	
	}


	/**
	 * Obtiene el valor de un parámetro de configuración.
	 * @param name Nombre del parámetro.
	 * @return Valor del parámetro o null si no se ha encontrado.
	 */
	public String getParameter(String configurationName, String parameterName) {
		try {
			Properties properties = configurationsMap.get(configurationName);
			if (properties==null) {
				properties = loadConfigurationProfiles(configurationName+".properties");
				configurationsMap.put(configurationName, properties);
				logger.info(configurationName+".properties loaded on demand"); 
			}

			String value = (String) properties.getProperty(parameterName);       
			return value;
		} catch (IOException t) {
			logger.fatal("Unable to load system configuration '"+configurationName+"':"+ t.getMessage(), t);
			return null;
		}
	}




	private static Properties loadConfigurationProfiles(String fileName) throws IOException {

		Class configurationParametersManagerClass = ConfigurationManager.class;
		ClassLoader classLoader = configurationParametersManagerClass.getClassLoader();

		//InputStream inputStream = classLoader.getResourceAsStream(CONFIGURATION_FILE_MAP.get(getKeyMap()));
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		Properties properties = new Properties();            
		properties.load(inputStream);

		inputStream.close();

		return properties;

	}
}