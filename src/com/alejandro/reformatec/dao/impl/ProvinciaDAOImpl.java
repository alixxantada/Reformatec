package com.alejandro.reformatec.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.ProvinciaDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.Provincia;

public class ProvinciaDAOImpl implements ProvinciaDAO {

	private static Logger logger = LogManager.getLogger(ProvinciaDAOImpl.class);

	public ProvinciaDAOImpl() {

	}




	@Override
	public List<Provincia> findByCriteria (Connection c, Integer idProvincia)
			throws DataException{
		logger.trace("Begin");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<Provincia> lista = new ArrayList<Provincia>();
		Provincia provincia = null;

		try {			
			StringBuilder queryString = new StringBuilder("SELECT pr.ID_PROVINCIA, pr.NOMBRE"
					+ " FROM PROVINCIA pr ");

			boolean first = true;

			if(idProvincia!=null) {
				DAOUtils.addClause(queryString, first," pr.ID_PROVINCIA = ? ");
				first = false;
			}

			queryString.append(" ORDER BY pr.ID_PROVINCIA ASC ");

			//create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);			

			int i = 1;

			if(idProvincia!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, idProvincia);
			}

			logger.trace(preparedStatement);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				provincia = loadNext(rs);
				lista.add(provincia);
			}

			logger.trace("End");

		} catch (SQLException sqle) {			
			logger.error(provincia, sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return lista;
	}



	private static Provincia loadNext(ResultSet rs) 
			throws SQLException { 

		Provincia provincia = new Provincia();
		int i = 1;

		provincia.setIdProvincia(rs.getInt(i++));
		provincia.setNombre(rs.getString(i++));

		return provincia;
	}
}