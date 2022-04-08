package com.alejandro.reformatec.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

import com.alejandro.reformatec.exception.DataException;

public class JDBCUtils {

	public static final void setParameter(PreparedStatement ps, Long value) 
			throws SQLException {		
		setParameter(ps, value);
	}


	public static final void setParameter(PreparedStatement ps, int parameterIndex, Long value) 
			throws SQLException {		
		setParameter(ps, parameterIndex, value, false);
	}



	public static final void setParameter(PreparedStatement ps, int parameterIndex, Double value) 
			throws SQLException {        
		setParameter(ps,  parameterIndex, value, false);
	}


	public static final void setParameter(PreparedStatement ps, int parameterIndex, Boolean value) 
			throws SQLException {
		setParameter(ps,  parameterIndex, value, false);
	}


	public static final void setParameter(PreparedStatement ps, int parameterIndex, Integer value) 
			throws SQLException {
		setParameter(ps, parameterIndex, value, false);
	}


	public static void setParameter(PreparedStatement stmt, int parameterIndex, String value) 
			throws SQLException {
		setParameter(stmt, parameterIndex, value, false);
	}

	public static void setParameter(PreparedStatement ps, int parameterIndex, Date value) 
			throws SQLException {
		setParameter(ps,  parameterIndex, value, false);
	}




	// si se pone false es que no puede tener el valor null y si se pone true es que se le puede pasar un valor "null"

	public static final void setParameter(PreparedStatement ps, int parameterIndex, 
			String value, boolean nullable) 
					throws SQLException {
		if (value!=null) {
			ps.setString(parameterIndex, value);		
		} else {
			if (nullable) {
				ps.setNull(parameterIndex, Types.VARCHAR);
			} 
		}
	}


	// si se pone false es que no puede tener el valor null y si se pone true es que se le puede pasar un valor "null"
	public static final void setParameter(PreparedStatement ps, int parameterIndex, 
			Long value, boolean nullable) 
					throws SQLException {
		if (value!=null) {
			ps.setLong(parameterIndex, value);		
		} else {
			if (nullable) {
				ps.setNull(parameterIndex, Types.INTEGER);
			} 
		}
	}


	// si se pone false es que no puede tener el valor null y si se pone true es que se le puede pasar un valor "null"
	public static final void setParameter(PreparedStatement ps, int parameterIndex, 
			Integer value, boolean nullable) 
					throws SQLException {
		if (value!=null) {
			ps.setInt(parameterIndex, value);		
		} else {
			if (nullable) {
				ps.setNull(parameterIndex, Types.INTEGER);
			} 
		}
	}


	// si se pone false es que no puede tener el valor null y si se pone true es que se le puede pasar un valor "null"
	public static final void setParameter(PreparedStatement ps, int parameterIndex, 
			Double value, boolean nullable) 
					throws SQLException {
		if (value!=null) {
			ps.setDouble(parameterIndex, value);		
		} else {
			if (nullable) {
				ps.setNull(parameterIndex, Types.DOUBLE);
			} 
		}
	}


	// si se pone false es que no puede tener el valor null y si se pone true es que se le puede pasar un valor "null"
	public static final void setParameter(PreparedStatement ps, int parameterIndex, 
			Date value, boolean nullable) 
					throws SQLException {
		if (value!=null) {
			ps.setDate(parameterIndex, new java.sql.Date(value.getTime()));		
		} else {
			if (nullable) {
				ps.setNull(parameterIndex, Types.DATE);
			} 
		}
	}	


	// si se pone false es que no puede tener el valor null y si se pone true es que se le puede pasar un valor "null"
	public static final void setParameter(PreparedStatement ps, int parameterIndex, 
			Boolean value, boolean nullable) 
					throws SQLException {
		if (value!=null) {
			ps.setBoolean(parameterIndex, value);		
		} else {
			if (nullable) {
				ps.setNull(parameterIndex, Types.BOOLEAN);
			} 
		}
	}




	// Cierra el resulset
	public static final void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}


	// cierra el preparedstatement
	public static final void close(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {					
				e.printStackTrace();
			}
		}
	}


	// cierra el statement
	public static final void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {					
				e.printStackTrace();
			}
		}
	}



	/**
	 * Metodo de finalizacion de transaccion <b>para el caso de autocommit = false</b>
	 * y de cierre de conexion.
	 *  
	 * Ejecuta commit() o rollback() y a continuacion cierra la conexi�n.
	 *  
	 * @param connection Conexion a cerrar.
	 * @param commitOrRollback Si es true ejecuta commit() y en caso contrario ejecuta rollback().
	 */
	public static void closeConnection(Connection connection, boolean commitOrRollback)
			throws DataException {
		try {
			if (connection != null) {
				if (commitOrRollback) {
					connection.commit();
				} else {
					connection.rollback();                        
				}
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}
}