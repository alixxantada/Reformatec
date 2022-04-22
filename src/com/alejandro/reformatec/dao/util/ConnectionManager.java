package com.alejandro.reformatec.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager {

	private static final String CFGM_PFX = "db.";
	private static final String CLASE = CFGM_PFX + "clase";
	private static final String URL = CFGM_PFX + "url";
	private static final String USER = CFGM_PFX + "user";
	private static final String PASSWORD = CFGM_PFX + "password";
	
	private static Logger logger = LogManager.getLogger(ConnectionManager.class);

	public static Connection getConnection() throws SQLException {
		
		ConfigurationManager cfgM = ConfigurationManager.getInstance();
		
		try {

			Class.forName(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, CLASE));

			Connection con = DriverManager.getConnection(cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, URL),
					cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, USER), 
					cfgM.getParameter(ConstantConfigUtil.WEB_REFORMATEC_PROPERTIES, PASSWORD));
			
			return con;

		}  catch (SQLException sqle) {
			if (logger.isFatalEnabled()) {
				logger.fatal(sqle);
			}
			throw sqle;
		} catch (ClassNotFoundException cnf) {
			if (logger.isFatalEnabled()) {
				logger.fatal(cnf);
			}
			throw new UnknownError(cnf.getMessage());
		}
	}
}