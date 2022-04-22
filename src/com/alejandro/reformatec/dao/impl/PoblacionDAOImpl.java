package com.alejandro.reformatec.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.PoblacionDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.PoblacionCriteria;
import com.alejandro.reformatec.model.PoblacionDTO;

public class PoblacionDAOImpl implements PoblacionDAO {

	private static Logger logger = LogManager.getLogger(PoblacionDAOImpl.class);

	public PoblacionDAOImpl() {

	}

	
	
	@Override
	public List<PoblacionDTO> findByCriteria(Connection c, PoblacionCriteria pc)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<PoblacionDTO> lista = new ArrayList<PoblacionDTO>();
		PoblacionDTO poblacion = null;

		try {			
			StringBuilder queryString = new StringBuilder("SELECT po.ID_POBLACION, po.NOMBRE, pr.ID_PROVINCIA, pr.NOMBRE"
					+ " FROM POBLACION po "
					+ " INNER JOIN PROVINCIA pr ON po.ID_PROVINCIA = pr.ID_PROVINCIA");
			
			boolean first = true;

			if(pc.getIdPoblacion()!=null) {
				DAOUtils.addClause(queryString, first," po.ID_POBLACION = ? ");
				first = false;
			}
			
			if(pc.getIdProvincia()!=null) {
				DAOUtils.addClause(queryString, first," pr.ID_PROVINCIA = ? ");
				first = false;
			}
			
			queryString.append(" ORDER BY po.ID_POBLACION ASC ");

			preparedStatement = c.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		
			
			int i = 1;

			if(pc.getIdPoblacion()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdPoblacion());
			}
			
			if(pc.getIdProvincia()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdProvincia());
			}
			
			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				poblacion = loadNext(rs);
				lista.add(poblacion);
			}
			
			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}
			
		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(poblacion, sqle);
			}
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return lista;
	}


	

	private static PoblacionDTO loadNext(ResultSet rs) 
			throws SQLException { 
		
		PoblacionDTO poblacion = new PoblacionDTO();

		int i = 1;

		poblacion.setIdPoblacion(rs.getInt(i++));
		poblacion.setNombre(rs.getString(i++));
		poblacion.setIdProvincia(rs.getInt(i++));
		poblacion.setNombreProvincia(rs.getString(i++));
		
		return poblacion;
	}
}