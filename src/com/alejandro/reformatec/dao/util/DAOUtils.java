package com.alejandro.reformatec.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUtils {


	public DAOUtils() {

	}


	/**
	 * Metodo para componer las query de los find criteria
	 * 
	 * @param queryString query
	 * @param first	Indica si pone ,WHERE, AND u OR
	 * @param clause Se indica con que completa la query
	 */
	public static void addClause(StringBuilder queryString, Boolean first, String clause) {
		
		if (first!=null) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
		} else {
			queryString.append(" OR ").append(clause);
		}
	}
	

	
	public static void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" SET ": " , ").append(clause);
	}
	
	
	
	
	/**
	 * Obtencion del total de filas de un resultSet, sin repetir consulta.
	 * Metodo orientado a implementar paginación.
	 * No existe una solución en el API estandar de JDBC.
	 * Esta es un solución para todas las BD pero NO ES LA MEJOR EN RENDIMIENTO.
	 * Por ello en este caso es habitual usar soluciones propietarias
	 * de cada BD (rownum de Oracle, y similar en MySQL).
	 * (En Hibernate, con ScrollableResults, no lo vemos porque lo implementa con el dialecto de cada DB).
	 * 
	 * Encantado de recibir mensajes son soluciones mejores (válidas para todas las BD): 
	 * @author https://www.linkedin.com/in/joseantoniolopezperez
	 * @version 0.2  
	 */
	public static final int getTotalRows(ResultSet resultSet) throws SQLException {
		int totalRows = 0;
		if(resultSet.last()) {
			totalRows = resultSet.getRow();
		}
		resultSet.beforeFirst();
		return totalRows;
	}
}