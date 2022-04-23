package com.alejandro.reformatec.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.LineaPresupuestoDAO;
import com.alejandro.reformatec.dao.PresupuestoDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.PresupuestoNotFoundException;
import com.alejandro.reformatec.model.LineaPresupuestoCriteria;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;
import com.alejandro.reformatec.model.PresupuestoCriteria;
import com.alejandro.reformatec.model.PresupuestoDTO;
import com.alejandro.reformatec.model.Results;


public class PresupuestoDAOImpl implements PresupuestoDAO {

	private static Logger logger = LogManager.getLogger(PresupuestoDAOImpl.class);

	private LineaPresupuestoDAO lineaPresupuestoDAO = null;
	//private List<LineaPresupuestoDTO> lineas = null;

	public PresupuestoDAOImpl() {
		lineaPresupuestoDAO = new LineaPresupuestoDAOImpl();
		//lineas = new ArrayList<LineaPresupuestoDTO>();

	}
	// cuando borramos/cambiamos estatus de presupuesto, dentro del metodo de borrar o cambiar estado, cogemos y metemos el metodo que cambia el estado/borra las lineas de presupuesto



	public Results<PresupuestoDTO> findByCriteria(Connection c, PresupuestoCriteria pc, int startIndex, int pageSize)
			throws DataException{

		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Results<PresupuestoDTO> results = new Results<PresupuestoDTO>();

		try {			

			StringBuilder queryString = new StringBuilder("SELECT pr.ID_PROYECTO, pr.TITULO, p.ID_PRESUPUESTO, p.TITULO, p.DESCRIPCION, "
					+ " p.FECHA_HORA_CREACION, p.FECHA_HORA_MODIFICACION, p.IMPORTE_TOTAL, u.ID_USUARIO, u.NOMBRE_PERFIL, "
					+ " tep.ID_TIPO_ESTADO_PRESUPUESTO, tep.NOMBRE, uu.ID_USUARIO, uu.NOMBRE_PERFIL "
					+ " FROM PRESUPUESTO p "
					+ " INNER JOIN USUARIO u ON p.ID_USUARIO_CREADOR_PRESUPUESTO = u.ID_USUARIO "
					+ " INNER JOIN TIPO_ESTADO_PRESUPUESTO tep ON p.ID_TIPO_ESTADO_PRESUPUESTO = tep.ID_TIPO_ESTADO_PRESUPUESTO "
					+ " INNER JOIN PROYECTO pr ON p.ID_PROYECTO = pr.ID_PROYECTO "
					+ " INNER JOIN USUARIO uu ON pr.ID_USUARIO_CREADOR_PROYECTO = uu.ID_USUARIO ");


			boolean first = true;

			if(pc.getIdPresupuesto()!=null) {
				DAOUtils.addClause(queryString, first," p.ID_PRESUPUESTO = ? ");
				first = false;
			}

			if(pc.getIdProveedor()!=null) {
				DAOUtils.addClause(queryString, first," u.ID_USUARIO = ? ");
				first = false;
			}

			if(pc.getIdProyecto()!=null) {
				DAOUtils.addClause(queryString, first," pr.ID_PROYECTO = ? ");
				first = false;
			}

			// O filtro por el tipo de estado o enseño todos menos los eliminados
			if(pc.getIdTipoEstadoPresupuesto()!=null) {
				DAOUtils.addClause(queryString, first," p.ID_TIPO_ESTADO_PRESUPUESTO = ? ");
				first = false;
			} else {
				DAOUtils.addClause(queryString, first," p.ID_TIPO_ESTADO_PRESUPUESTO <> 4 ");
				first = false;
			}

			queryString.append(" ORDER BY p.FECHA_HORA_CREACION DESC ");

			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if(pc.getIdPresupuesto()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdPresupuesto());
			}

			if(pc.getIdProveedor()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdProveedor());
			}

			if(pc.getIdProyecto()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdProyecto());
			}

			if(pc.getIdTipoEstadoPresupuesto()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdTipoEstadoPresupuesto());
			}

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			List<PresupuestoDTO> presupuestos = new ArrayList<PresupuestoDTO>();
			PresupuestoDTO presupuesto = null;
			int resultsLoaded = 0;
			// dirigimos el puntero a donde empezamos a mostrar resultados en nuestro resulset(si hay almenos 1 resultado)
			if ((startIndex >=1) && rs.absolute(startIndex)) {
				// empezamos recorriendolo ya 1 vez para sacar el primer resultado! luego si hay mas lo repetimos! de lo contrario se salta el primero
				do { 
					presupuesto = loadNext(c, rs);
					presupuestos.add(presupuesto);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());				
			}

			results.setData(presupuestos);
			results.setTotal(DAOUtils.getTotalRows(rs));

			if (logger.isTraceEnabled()) {
				logger.trace("End ");
			}


		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error("Error SQL: "+results, sqle);
			}			
			throw new DataException("Error lista: "+results, sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return results;
	}





	@Override
	public long create(Connection c, PresupuestoDTO presupuesto, List<LineaPresupuestoDTO> lineas) 
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " INSERT INTO PRESUPUESTO(TITULO, DESCRIPCION, FECHA_HORA_CREACION, FECHA_HORA_MODIFICACION, "
					+ " IMPORTE_TOTAL, ID_TIPO_ESTADO_PRESUPUESTO, ID_PROYECTO, ID_USUARIO_CREADOR_PRESUPUESTO) "
					+ " VALUES (?,?,?,?,?,?,?,?) ";

			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getTitulo());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getFechaHoraCreacion());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getFechaHoraModificacion(),true);
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getImporteTotal());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getIdTipoEstadoPresupuesto());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getIdProyecto());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getIdUsuarioCreadorPresupuesto());

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			int insertedRows = preparedStatement.executeUpdate();
			
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					presupuesto.setIdPresupuesto(rs.getLong(1));
				}
			} else {				
				if (logger.isErrorEnabled()) {
					logger.error(presupuesto);
				}				
				throw new DataException(""+presupuesto.getIdPresupuesto());
			}

			for (LineaPresupuestoDTO lineaPresupuestoDTO : lineas) {
				lineaPresupuestoDTO.setIdPresupuesto(presupuesto.getIdPresupuesto());
				lineaPresupuestoDAO.create(c,lineaPresupuestoDTO);
			}
			
			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(presupuesto, sqle);
			}
			throw new DataException("Presupuesto: "+presupuesto.getIdPresupuesto()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return presupuesto.getIdPresupuesto();
	}




	@Override
	public int update(Connection c, PresupuestoDTO presupuesto, List<LineaPresupuestoDTO> lineas) 
			throws DataException, PresupuestoNotFoundException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE PRESUPUESTO "
					+ "	SET		TITULO = ?,"
					+ "			DESCRIPCION= ?,"
					+ "			FECHA_HORA_MODIFICACION = ?,"
					+ "			IMPORTE_TOTAL = ?,"
					+ "			ID_TIPO_ESTADO_PRESUPUESTO = ?,"
					+ "			ID_PROYECTO = ?,"
					+ "			ID_USUARIO_CREADOR_PRESUPUESTO = ? "
					+ "  WHERE ID_PRESUPUESTO = ? ";

			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getTitulo());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getFechaHoraModificacion());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getImporteTotal());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getIdTipoEstadoPresupuesto());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getIdProyecto());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getIdUsuarioCreadorPresupuesto());
			JDBCUtils.setParameter(preparedStatement, i++, presupuesto.getIdPresupuesto());

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {				
				if (logger.isErrorEnabled()) {
					logger.error(presupuesto);
				}				
				throw new PresupuestoNotFoundException("Presupuesto: "+presupuesto.getIdPresupuesto());
			}

			for (LineaPresupuestoDTO lineaPresupuestoDTO : lineas) {
				lineaPresupuestoDTO.setIdPresupuesto(presupuesto.getIdPresupuesto());
				lineaPresupuestoDAO.create(c,lineaPresupuestoDTO);
			}
			
			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(presupuesto, sqle);
			}
			throw new DataException("Presupuesto: "+presupuesto.getIdPresupuesto()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}




	@Override
	public int updateStatus(Connection c, Long idPresupuesto, Integer idEstadoPresupuesto) 
			throws DataException, PresupuestoNotFoundException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE PRESUPUESTO "
					+ "	SET ID_TIPO_ESTADO_PRESUPUESTO = ? "
					+ " WHERE ID_PRESUPUESTO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idEstadoPresupuesto);
			JDBCUtils.setParameter(preparedStatement, i++, idPresupuesto);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {
				throw new PresupuestoNotFoundException("Presupuesto: "+idPresupuesto);
			}
			
			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(sqle);
			}
			throw new DataException("Presupuesto: "+idPresupuesto+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}




	private PresupuestoDTO loadNext(Connection c, ResultSet rs) 
			throws SQLException, DataException { 

		PresupuestoDTO presupuesto = new PresupuestoDTO();

		int i = 1;

		presupuesto.setIdProyecto(rs.getLong(i++));
		presupuesto.setTituloProyecto(rs.getString(i++));
		presupuesto.setIdPresupuesto(rs.getLong(i++));
		presupuesto.setTitulo(rs.getString(i++));
		presupuesto.setDescripcion(rs.getString(i++));
		presupuesto.setFechaHoraCreacion(rs.getDate(i++));
		presupuesto.setFechaHoraModificacion(rs.getDate(i++));
		presupuesto.setImporteTotal(rs.getDouble(i++));
		presupuesto.setIdUsuarioCreadorPresupuesto(rs.getLong(i++));
		presupuesto.setNombrePerfilUsuarioCreadorPresupuesto(rs.getString(i++));
		presupuesto.setIdTipoEstadoPresupuesto(rs.getInt(i++));
		presupuesto.setNombreTipoEstadoPresupuesto(rs.getString(i++));
		presupuesto.setIdUsuarioCreadorProyecto(rs.getLong(i++));
		presupuesto.setNombrePerfilUsuarioCreadorProyecto(rs.getString(i++));

		LineaPresupuestoCriteria lpc = new LineaPresupuestoCriteria();
		lpc.setIdPresupuesto(presupuesto.getIdPresupuesto());
		List<LineaPresupuestoDTO> lineas = lineaPresupuestoDAO.findByCriteria(c, lpc);
		presupuesto.setLineas(lineas);

		return presupuesto;
	}
}