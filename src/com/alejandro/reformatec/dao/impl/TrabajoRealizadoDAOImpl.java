
package com.alejandro.reformatec.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.TrabajoRealizadoDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.TrabajoRealizadoNotFoundException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.TrabajoRealizadoCriteria;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;

public class TrabajoRealizadoDAOImpl implements TrabajoRealizadoDAO{

	private static Logger logger = LogManager.getLogger(TrabajoRealizadoDAOImpl.class);

	private static Map<String, String> SORTING_CRITERIA_MAP = null;

	static {

		SORTING_CRITERIA_MAP = new HashMap<String, String>();

		SORTING_CRITERIA_MAP.put("VAL", "AVG(v.NOTA_VALORACION) ");
		SORTING_CRITERIA_MAP.put("NV", "tr.NUM_VISUALIZACION ");
		SORTING_CRITERIA_MAP.put("FC", "tr.FECHA_CREACION ");		
	}

	private EspecializacionDAO especializacionDAO = null;

	public TrabajoRealizadoDAOImpl() {
		especializacionDAO = new EspecializacionDAOImpl();
	}


	@Override
	public Results<TrabajoRealizadoDTO> findByCriteria(Connection c, TrabajoRealizadoCriteria trc, int startIndex, int pageSize)
			throws DataException{
		logger.trace("Begin");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Results<TrabajoRealizadoDTO> results = new Results<TrabajoRealizadoDTO>();

		try {

			StringBuilder queryString = new StringBuilder("SELECT tr.ID_TRABAJO_REALIZADO, v.NOTA_VALORACION, tr.TITULO, tr.DESCRIPCION, tr.FECHA_CREACION, "
					+ " tr.NUM_VISUALIZACION, u.ID_USUARIO, u.NOMBRE_PERFIL, pl.ID_POBLACION, pl.NOMBRE, pr.ID_PROVINCIA, pr.NOMBRE, "
					+ " tetr.ID_TIPO_ESTADO_TRABAJO_REALIZADO, tetr.NOMBRE, tre.ID_TRABAJO_REALIZADO, e.NOMBRE "
					+ " FROM TRABAJO_REALIZADO tr "
					+ " INNER JOIN USUARIO u ON tr.ID_USUARIO_CREADOR_TRABAJO = u.ID_USUARIO"
					+ " INNER JOIN POBLACION pl ON tr.ID_POBLACION = pl.ID_POBLACION"
					+ " INNER JOIN PROVINCIA pr ON pl.ID_PROVINCIA = pr.ID_PROVINCIA"
					+ " LEFT OUTER JOIN VALORACION v ON tr.ID_TRABAJO_REALIZADO = v.ID_TRABAJO_REALIZADO_VALORADO "
					+ " LEFT OUTER JOIN TRABAJO_REALIZADO_ESPECIALIZACION tre ON tre.ID_TRABAJO_REALIZADO = tr.ID_TRABAJO_REALIZADO"
					+ " LEFT OUTER JOIN ESPECIALIZACION e ON tre.ID_ESPECIALIZACION = e.ID_ESPECIALIZACION"
					+ " INNER JOIN TIPO_ESTADO_TRABAJO_REALIZADO tetr ON tr.ID_TIPO_ESTADO_TRABAJO_REALIZADO = tetr.ID_TIPO_ESTADO_TRABAJO_REALIZADO ");

			boolean first = true;
			
			if(trc.getDescripcion()!=null) {
				DAOUtils.addClause(queryString, first," (UPPER(tr.DESCRIPCION) LIKE UPPER(?)) OR (UPPER(u.NOMBRE_PERFIL) LIKE UPPER(?)) ");
				first = false;
			}
			if(trc.getIdProvincia()!=null) {
				DAOUtils.addClause(queryString, first," pl.ID_PROVINCIA = ? ");
				first = false;
			}
			if(trc.getIdEspecializacion()!=null) {
				DAOUtils.addClause(queryString, first," tre.ID_ESPECIALIZACION = ? ");
				first = false;
			}
			if(trc.getIdProveedor()!=null) {
				DAOUtils.addClause(queryString, first," u.ID_USUARIO = ? ");
				first = false;
			}
			if(trc.getIdTrabajoRealizado()!=null) {
				DAOUtils.addClause(queryString, first," tr.ID_TRABAJO_REALIZADO = ? ");
				first = false;
			}
			queryString.append(" AND tetr.ID_TIPO_ESTADO_TRABAJO_REALIZADO = 1 AND u.ID_TIPO_USUARIO = 2 AND (u.ID_TIPO_ESTADO_CUENTA = 2 || 1 )  GROUP BY tr.ID_TRABAJO_REALIZADO ");

			if(trc.getOrderBy()!=null) {
				queryString.append(" ORDER BY "+ SORTING_CRITERIA_MAP.get(trc.getOrderBy()));
			} else {
				queryString.append(" ORDER BY AVG(u.NUM_VISUALIZACION) DESC ");
			}

			// Create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if(trc.getDescripcion()!=null) {
				StringBuilder a = new StringBuilder("%");
				a.append(trc.getDescripcion()).append("%");
				JDBCUtils.setParameter(preparedStatement, i++, a.toString()); // PRIMER VALOR (descripcion trabajo)
				JDBCUtils.setParameter(preparedStatement, i++, a.toString()); // SEGUNDO VALOR (nombre perfil)
			}
			
			if(trc.getIdProvincia()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, trc.getIdProvincia());
			}
			
			if (trc.getIdEspecializacion()!=null ) {
				JDBCUtils.setParameter(preparedStatement, i++, trc.getIdEspecializacion());		
			}
			
			if (trc.getIdProveedor()!=null ) {
				JDBCUtils.setParameter(preparedStatement, i++, trc.getIdProveedor());		
			}
			
			if (trc.getIdTrabajoRealizado()!=null ) {
				JDBCUtils.setParameter(preparedStatement, i++, trc.getIdTrabajoRealizado());		
			}

			logger.trace(preparedStatement);
			rs = preparedStatement.executeQuery();

			List<TrabajoRealizadoDTO> trabajos = new ArrayList<TrabajoRealizadoDTO>();
			TrabajoRealizadoDTO trabajo = null;			
			int resultsLoaded=0;

			if ((startIndex >=1) && rs.absolute(startIndex)) {
				do {
					trabajo = loadNext(c, rs);
					trabajos.add(trabajo);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());
			}

			results.setData(trabajos);
			results.setTotal(DAOUtils.getTotalRows(rs));

			logger.trace("End"+results);

		} catch (SQLException sqle) {			
			logger.error("Error SQL: "+results, sqle);
			throw new DataException("Error lista: "+results, sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return results;
	}



	@Override
	public long create(Connection c, TrabajoRealizadoDTO trabajoRealizado) 
			throws DataException{
		logger.trace("Begin");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " INSERT INTO TRABAJO_REALIZADO(TITULO, DESCRIPCION, FECHA_CREACION, "
					+ " NUM_VISUALIZACION, ID_USUARIO_CREADOR_TRABAJO, ID_POBLACION, ID_TIPO_ESTADO_TRABAJO_REALIZADO, ID_PROYECTO_ASOCIADO ) "
					+ " VALUES (?,?,?,?,?,?,?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getTitulo());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getFechaCreacion());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getNumVisualizaciones());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdUsuarioCreadorTrabajo());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdPoblacion());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdTipoEstadoTrabajoRealizado());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdProyectoAsociado(), true);

			logger.trace(preparedStatement);
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					trabajoRealizado.setIdTrabajoRealizado(rs.getLong(1));
				}

			} else {
				throw new DataException(trabajoRealizado.getTitulo());
			}

			logger.trace("End");

		} catch (SQLException sqle) {			
			logger.error(trabajoRealizado, sqle);
			throw new DataException(trabajoRealizado.getTitulo(), sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return trabajoRealizado.getIdTrabajoRealizado();
	}



	@Override
	public int update(Connection c, TrabajoRealizadoDTO trabajoRealizado) 
			throws DataException, TrabajoRealizadoNotFoundException{
		logger.trace("Begin");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE TRABAJO_REALIZADO "
					+ "	SET		TITULO = ?,"
					+ "			DESCRIPCION= ?,"
					+ "			NUM_VISUALIZACION = ?,"
					+ "			FECHA_CREACION = ?,"
					+ "			ID_USUARIO_CREADOR_TRABAJO = ?,"
					+ "			ID_POBLACION = ?, "
					+ "			ID_TIPO_ESTADO_TRABAJO_REALIZADO = ?, "
					+ "		ID_PROYECTO_ASOCIADO = ? "
					+ "  WHERE ID_TRABAJO_REALIZADO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getTitulo());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getNumVisualizaciones());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getFechaCreacion());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdUsuarioCreadorTrabajo());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdPoblacion());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdTipoEstadoTrabajoRealizado());
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdProyectoAsociado(), true);
			JDBCUtils.setParameter(preparedStatement, i++, trabajoRealizado.getIdTrabajoRealizado());

			logger.trace(preparedStatement);
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {
				throw new TrabajoRealizadoNotFoundException("Trabajo Realizado: "+trabajoRealizado.getIdTrabajoRealizado());
			}

			logger.trace("End");

		} catch (SQLException sqle) {			
			logger.error(trabajoRealizado, sqle);
			throw new DataException("Trabajo Realizado: "+trabajoRealizado.getIdTrabajoRealizado()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}




	@Override
	public int updateStatus(Connection c, Long idTrabajoRealizado, Integer idEstadoTrabajoRealizado) 
			throws DataException, TrabajoRealizadoNotFoundException{
		logger.trace("Begin");

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE TRABAJO_REALIZADO "
					+ "	SET ID_TIPO_ESTADO_TRABAJO_REALIZADO = ? "
					+ " WHERE ID_TRABAJO_REALIZADO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idEstadoTrabajoRealizado);
			JDBCUtils.setParameter(preparedStatement, i++, idTrabajoRealizado);


			logger.trace(preparedStatement);
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {
				throw new TrabajoRealizadoNotFoundException("Trabajo Realizado: "+idTrabajoRealizado);
			}

			logger.trace("End");

		} catch (SQLException sqle) {			
			logger.error(idTrabajoRealizado, sqle);
			throw new DataException("Trabajo Realizado: "+idTrabajoRealizado+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}



	private TrabajoRealizadoDTO loadNext(Connection c, ResultSet rs) 
			throws SQLException, DataException  { 

		TrabajoRealizadoDTO trabajorealizado = new TrabajoRealizadoDTO();

		int i = 1;

		trabajorealizado.setIdTrabajoRealizado(rs.getLong(i++));
		trabajorealizado.setNotaValoracion(rs.getInt(i++));
		trabajorealizado.setTitulo(rs.getString(i++));
		trabajorealizado.setDescripcion(rs.getString(i++));
		trabajorealizado.setFechaCreacion(rs.getDate(i++));
		trabajorealizado.setNumVisualizaciones(rs.getLong(i++));
		trabajorealizado.setIdUsuarioCreadorTrabajo(rs.getLong(i++));
		trabajorealizado.setNombrePerfilUsuarioCreadorTrabajo(rs.getString(i++));
		trabajorealizado.setIdPoblacion(rs.getInt(i++));
		trabajorealizado.setNombrePoblacion(rs.getString(i++));
		trabajorealizado.setIdProvincia(rs.getInt(i++));
		trabajorealizado.setNombreProvincia(rs.getString(i++));
		trabajorealizado.setIdTipoEstadoTrabajoRealizado(rs.getInt(i++));
		trabajorealizado.setNombreTipoEstadoTrabajoRealizado(rs.getString(i++));

		EspecializacionCriteria ec = new EspecializacionCriteria();
		ec.setIdTrabajoRealizado(trabajorealizado.getIdTrabajoRealizado());
		List<Especializacion> especializaciones = especializacionDAO.findByCriteria(c, ec);
		trabajorealizado.setEspecializaciones(especializaciones);

		return trabajorealizado;
	}
}