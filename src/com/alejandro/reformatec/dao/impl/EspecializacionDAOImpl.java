package com.alejandro.reformatec.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;

public class EspecializacionDAOImpl implements EspecializacionDAO{

	private static Logger logger = LogManager.getLogger(EspecializacionDAOImpl.class);

	public EspecializacionDAOImpl() {

	}


	public List<Especializacion> findByCriteria(Connection c, EspecializacionCriteria ec)
			throws DataException{


		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<Especializacion> lista = new ArrayList<Especializacion>();
		Especializacion especializacion = null;

		try {

			StringBuilder queryString = new StringBuilder("SELECT e.ID_ESPECIALIZACION, e.NOMBRE "
					+ " FROM ESPECIALIZACION e "
					+ " LEFT OUTER JOIN USUARIO_ESPECIALIZACION ue ON e.ID_ESPECIALIZACION = ue.ID_ESPECIALIZACION "
					+ " LEFT OUTER JOIN TRABAJO_REALIZADO_ESPECIALIZACION tre ON e.ID_ESPECIALIZACION= tre.ID_ESPECIALIZACION "
					+ " LEFT OUTER JOIN PROYECTO_ESPECIALIZACION pe ON e.ID_ESPECIALIZACION = pe.ID_ESPECIALIZACION ");

			boolean first = true;

			if(ec.getIdEspecializacion()!=null) {
				DAOUtils.addClause(queryString, first," e.ID_ESPECIALIZACION = ? ");
				first = false;
			}

			if(ec.getIdProyecto()!=null) {
				DAOUtils.addClause(queryString, first," pe.ID_PROYECTO = ? ");
				first = false;
			}

			if(ec.getIdTrabajoRealizado()!=null) {
				DAOUtils.addClause(queryString, first," tre.ID_TRABAJO_REALIZADO = ? ");
				first = false;
			}

			if(ec.getIdUsuario()!=null) {
				DAOUtils.addClause(queryString, first," ue.ID_USUARIO = ? ");
				first = false;
			}	
		
			queryString.append(" GROUP BY e.ID_ESPECIALIZACION ORDER BY e.ID_ESPECIALIZACION ASC ");

			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			
			if(ec.getIdEspecializacion()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdEspecializacion());
			}
			
			if(ec.getIdProyecto()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdProyecto());
			}

			if(ec.getIdTrabajoRealizado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdTrabajoRealizado());
			}

			if(ec.getIdUsuario()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdUsuario());
			}	

			logger.trace(preparedStatement);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				especializacion = loadNext(rs);
				lista.add(especializacion);
			}

			logger.trace("End ");

		} catch (SQLException sqle) {			
			logger.error("Error SQL: "+lista, sqle);
			throw new DataException("Error lista: "+lista, sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return lista;
	}




	public void crearEspecializacionUsuario (Connection c, Long idUsuario, List<Integer> idsEspecializaciones)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			for(Integer idEspecializacion : idsEspecializaciones) {

				String sql = " INSERT INTO USUARIO_ESPECIALIZACION(ID_USUARIO, ID_ESPECIALIZACION) "
						+ " VALUES (?,?) ";

				//create prepared statement
				preparedStatement = c.prepareStatement(sql);

				int i  = 1;

				JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
				JDBCUtils.setParameter(preparedStatement, i++, idEspecializacion);

				logger.trace(preparedStatement);
				preparedStatement.executeUpdate();

			}

			logger.trace("End");

		} catch (SQLException sqle) {			
			logger.error(sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}




	public void crearEspecializacionProyecto (Connection c, Long idProyecto, List<Integer> idsEspecializaciones)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			for(Integer idEspecializacion : idsEspecializaciones) {

				String sql = " INSERT INTO PROYECTO_ESPECIALIZACION(ID_PROYECTO, ID_ESPECIALIZACION) "
						+ " VALUES (?,?) ";

				//create prepared statement
				preparedStatement = c.prepareStatement(sql);

				int i  = 1;

				JDBCUtils.setParameter(preparedStatement, i++, idProyecto);
				JDBCUtils.setParameter(preparedStatement, i++, idEspecializacion);

				logger.trace(preparedStatement);
				preparedStatement.executeUpdate();

				logger.trace("End");
			}
		} catch (SQLException sqle) {			
			logger.error(sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}




	public void crearEspecializacionTrabajo (Connection c, Long idTrabajo, List<Integer> idsEspecializaciones)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			for(Integer idEspecializacion : idsEspecializaciones) {

				String sql = " INSERT INTO TRABAJO_REALIZADO_ESPECIALIZACION(ID_TRABAJO_REALIZADO, ID_ESPECIALIZACION) "
						+ " VALUES (?,?) ";

				//create prepared statement
				preparedStatement = c.prepareStatement(sql);

				int i  = 1;

				JDBCUtils.setParameter(preparedStatement, i++, idTrabajo);
				JDBCUtils.setParameter(preparedStatement, i++, idEspecializacion);

				logger.trace(preparedStatement);
				preparedStatement.executeUpdate();
			}

			logger.trace("End");

		} catch (SQLException sqle) {			
			logger.error(sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}




	private static Especializacion loadNext(ResultSet rs) 
			throws SQLException { 

		Especializacion especializacion = new Especializacion();

		int i = 1;

		especializacion.setIdEspecializacion(rs.getInt(i++));
		especializacion.setNombre(rs.getString(i++));

		return especializacion;
	}
}