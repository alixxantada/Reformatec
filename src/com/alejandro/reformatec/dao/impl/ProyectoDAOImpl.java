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

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.EspecializacionDAO;
import com.alejandro.reformatec.dao.ProyectoDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ProyectoNotFoundException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;
import com.alejandro.reformatec.model.ProyectoCriteria;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.Results;

public class ProyectoDAOImpl implements ProyectoDAO {

	private static Logger logger = LogManager.getLogger(ProyectoDAOImpl.class);	

	private static Map<String, String> SORTING_CRITERIA_MAP = null;

	static {
		SORTING_CRITERIA_MAP = new HashMap<String, String>();

		SORTING_CRITERIA_MAP.put("PMASC", "p.PRESUPUESTO_MAX ASC");
		SORTING_CRITERIA_MAP.put("PMDESC", "p.PRESUPUESTO_MAX DESC");
		SORTING_CRITERIA_MAP.put("FC", "p.FECHA_CREACION DESC");
	}

	private EspecializacionDAO especializacionDAO = null;

	public ProyectoDAOImpl() {
		especializacionDAO = new EspecializacionDAOImpl();
	}


	@Override
	public Results<ProyectoDTO> findByCriteria(Connection c, ProyectoCriteria pc, int startIndex, int pageSize)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Results<ProyectoDTO> results = new Results<ProyectoDTO>();

		try {

			StringBuilder queryString = new StringBuilder("SELECT u.ID_USUARIO, u.NOMBRE_PERFIL, p.ID_PROYECTO, p.TITULO, "
					+ " p.FECHA_HORA_CREACION, p.FECHA_HORA_MODIFICACION, p.DESCRIPCION, pl.ID_POBLACION, pl.NOMBRE, pr.ID_PROVINCIA, pr.NOMBRE, "
					+ " p.PRESUPUESTO_MAX, tep.ID_TIPO_ESTADO_PROYECTO, tep.NOMBRE, e.ID_ESPECIALIZACION, e.NOMBRE "
					+ " FROM PROYECTO p "
					+ " INNER JOIN USUARIO u ON p.ID_USUARIO_CREADOR_PROYECTO = u.ID_USUARIO"
					+ " INNER JOIN POBLACION pl ON p.ID_POBLACION= pl.ID_POBLACION"
					+ " INNER JOIN PROVINCIA pr ON pl.ID_PROVINCIA = pr.ID_PROVINCIA"
					+ " INNER JOIN TIPO_ESTADO_PROYECTO tep ON p.ID_TIPO_ESTADO_PROYECTO = tep.ID_TIPO_ESTADO_PROYECTO"
					+ " LEFT OUTER JOIN PROYECTO_ESPECIALIZACION pe ON pe.ID_PROYECTO = p.ID_PROYECTO"
					+ " LEFT OUTER JOIN ESPECIALIZACION e ON pe.ID_ESPECIALIZACION = e.ID_ESPECIALIZACION");

			boolean first = true;

			if (!StringUtils.isEmpty(pc.getDescripcion())) {
				DAOUtils.addClause(queryString, first," UPPER(p.DESCRIPCION) LIKE UPPER(?) OR (UPPER(p.TITULO) LIKE UPPER(?))");
				first = false;
			}
			
			if (pc.getIdProvincia()!=null) {
				DAOUtils.addClause(queryString, first," pl.ID_PROVINCIA = ? ");
				first = false;
			}
			
			if(pc.getPresupuestoMaxMinimo()!=null) {
				DAOUtils.addClause(queryString, first," p.PRESUPUESTO_MAX >= ? ");
				first = false;
			}
			
			if(pc.getPresupuestoMaxMaximo()!=null) {
				DAOUtils.addClause(queryString, first," p.PRESUPUESTO_MAX <= ? ");
				first = false;
			}

			if(pc.getIdEspecializacion()!=null) {
				DAOUtils.addClause(queryString, first," pe.ID_ESPECIALIZACION = ? ");
				first = false;
			}
			
			if(pc.getIdProyecto()!=null) {
				DAOUtils.addClause(queryString, first," p.ID_PROYECTO = ? ");
				first = false;
			}
			
			if(pc.getIdUsuarioCreador()!=null) {
				DAOUtils.addClause(queryString, first," p.ID_USUARIO_CREADOR_PROYECTO = ? GROUP BY p.ID_PROYECTO ORDER BY p.FECHA_HORA_CREACION DESC  ");
				first = false;
			}

			
			if(pc.getIdProyecto()!=null || pc.getIdUsuarioCreador()!=null) {
				
			} else {
				
				queryString.append(" AND tep.ID_TIPO_ESTADO_PROYECTO = 1 GROUP BY u.ID_USUARIO ");

				if(pc.getOrderBy()!=null) {
					queryString.append(" ORDER BY "+ SORTING_CRITERIA_MAP.get(pc.getOrderBy()));
				} else {
					queryString.append(" ORDER BY p.FECHA_HORA_CREACION DESC ");
				}
			}
			

			// Create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if(pc.getDescripcion()!=null) {
				StringBuilder a = new StringBuilder("%");
				a.append(pc.getDescripcion()).append("%");
				JDBCUtils.setParameter(preparedStatement, i++, a.toString()); // PRIMER VALOR (descripcion proyecto)
				JDBCUtils.setParameter(preparedStatement, i++, a.toString());// SEGUNDO VALOR (nombre perfil)
			}
			
			if(pc.getIdProvincia()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdProvincia());
			}
			
			if(pc.getPresupuestoMaxMinimo()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getPresupuestoMaxMinimo());
			}
			
			if(pc.getPresupuestoMaxMaximo()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getPresupuestoMaxMaximo());
			}
			
			if (pc.getIdEspecializacion()!=null ) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdEspecializacion());		
			}	
			
			if (pc.getIdProyecto()!=null ) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdProyecto());		
			}	
			
			if (pc.getIdUsuarioCreador()!=null ) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdUsuarioCreador());		
			}
			
			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			List<ProyectoDTO> proyectos = new ArrayList<ProyectoDTO>();
			ProyectoDTO proyecto = null;
			int resultsLoaded=0;

			if ((startIndex >=1) && rs.absolute(startIndex)) {
				do {
					proyecto = loadNext(c, rs);
					proyectos.add(proyecto);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());
			}

			results.setData(proyectos);
			results.setTotal(DAOUtils.getTotalRows(rs));

			if (logger.isTraceEnabled()) {
				logger.trace("End"+results);
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
	public long create(Connection c, ProyectoDTO proyecto, List<Integer> especializaciones)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " INSERT INTO PROYECTO(TITULO, FECHA_HORA_CREACION, FECHA_HORA_MODIFICACION, DESCRIPCION, ID_POBLACION, "
					+ " PRESUPUESTO_MAX, ID_USUARIO_CREADOR_PROYECTO, ID_TIPO_ESTADO_PROYECTO) "
					+ " VALUES (?,?,?,?,?,?,?,?) ";

			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getTitulo());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getFechaHoraCreacion());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getFechaHoraModificacion(),true);
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getIdPoblacion());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getPresupuestoMax());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getIdUsuarioCreadorProyecto());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getIdTipoEstadoProyecto());

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					proyecto.setIdProyecto(rs.getLong(1));
				}
				
				if (especializaciones!=null) {
					
					for (Integer idEspecializacion : especializaciones) {
						especializacionDAO.crearEspecializacionProyecto(c, proyecto.getIdProyecto(), idEspecializacion);	
					}									
				}

			} else {
				throw new DataException(proyecto.getTitulo());
			}

			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(proyecto,sqle);
			}
			throw new DataException(proyecto.getTitulo(), sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return proyecto.getIdProyecto();
	}




	@Override
	public int update(Connection c, ProyectoDTO proyecto, List<Integer> especializaciones)
			throws DataException, ProyectoNotFoundException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE PROYECTO "
					+ "	SET		TITULO = ?, "
					+ "			DESCRIPCION= ?, "
					+ "			PRESUPUESTO_MAX= ?, "
					+ "			ID_POBLACION= ?, "
					+ "			FECHA_HORA_MODIFICACION= ? "
					+ "  WHERE ID_PROYECTO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getTitulo());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getPresupuestoMax());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getIdPoblacion());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getFechaHoraModificacion());
			JDBCUtils.setParameter(preparedStatement, i++, proyecto.getIdProyecto());

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			updatedRows = preparedStatement.executeUpdate();
			
			if (especializaciones!=null) {
				
				for (Integer idEspecializacion : especializaciones) {
					especializacionDAO.crearEspecializacionProyecto(c, proyecto.getIdProyecto(), idEspecializacion);	
				}									
			}
			
			if (updatedRows!=1) {
				throw new ProyectoNotFoundException("Proyecto: "+proyecto.getIdProyecto());
			}
			
			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(proyecto,sqle);
			}
			throw new DataException("Proyecto: "+proyecto.getIdProyecto()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}



	@Override
	public int updateStatus(Connection c, Long idProyecto, Integer idEstadoProyecto) 
			throws DataException, ProyectoNotFoundException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;

		try {

			String sql =" UPDATE PROYECTO "
					+ "	SET ID_TIPO_ESTADO_PROYECTO = ? "
					+ " WHERE ID_PROYECTO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idEstadoProyecto);
			JDBCUtils.setParameter(preparedStatement, i++, idProyecto);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {
				throw new ProyectoNotFoundException("Proyecto: "+idProyecto);
			}
			
			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(idProyecto,sqle);
			}
			throw new DataException("Proyecto: "+idProyecto+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}




	private ProyectoDTO loadNext(Connection c, ResultSet rs) 
			throws SQLException, DataException { 

		ProyectoDTO proyecto = new ProyectoDTO();
		int i = 1;

		proyecto.setIdUsuarioCreadorProyecto(rs.getLong(i++));
		proyecto.setNombrePerfilUsuarioCreadorProyecto(rs.getString(i++));
		proyecto.setIdProyecto(rs.getLong(i++));
		proyecto.setTitulo(rs.getString(i++));
		proyecto.setFechaHoraCreacion(rs.getDate(i++));
		proyecto.setFechaHoraModificacion(rs.getDate(i++));
		proyecto.setDescripcion(rs.getString(i++));
		proyecto.setIdPoblacion(rs.getInt(i++));
		proyecto.setNombrePoblacion(rs.getString(i++));
		proyecto.setIdProvincia(rs.getInt(i++));
		proyecto.setNombreProvincia(rs.getString(i++));
		proyecto.setPresupuestoMax(rs.getInt(i++));
		proyecto.setIdTipoEstadoProyecto(rs.getInt(i++));
		proyecto.setNombreTipoEstadoProyecto(rs.getString(i++));

		EspecializacionCriteria ec = new EspecializacionCriteria();
		ec.setIdProyecto(proyecto.getIdProyecto());
		List<Especializacion> especializaciones = especializacionDAO.findByCriteria(c, ec);
		proyecto.setEspecializaciones(especializaciones);

		return proyecto;
	}
}