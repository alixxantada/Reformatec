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

import com.alejandro.reformatec.dao.ValoracionDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ValoracionNotFoundException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.ValoracionCriteria;
import com.alejandro.reformatec.model.ValoracionDTO;


public class ValoracionDAOImpl implements ValoracionDAO{

	private static Logger logger = LogManager.getLogger(ValoracionDAOImpl.class);

	public ValoracionDAOImpl() {

	}



	@Override
	public Results<ValoracionDTO> findByCriteria(Connection c, ValoracionCriteria vc, int startIndex, int pageSize)
			throws DataException {
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Results<ValoracionDTO> results = new Results<ValoracionDTO>();

		try {			
			StringBuilder queryString = new StringBuilder("SELECT v.ID_VALORACION, v.TITULO, v.DESCRIPCION, v.NOTA_VALORACION, v.FECHA_HORA_CREACION, uu.ID_USUARIO, "
					+ " uu.NOMBRE_PERFIL, tr.ID_TRABAJO_REALIZADO, tr.TITULO, u.ID_USUARIO, u.NOMBRE_PERFIL, tev.ID_TIPO_ESTADO_VALORACION, tev.NOMBRE "
					+ " FROM VALORACION v "
					+ " LEFT OUTER JOIN USUARIO u ON v.ID_PROVEEDOR_VALORADO = u.ID_USUARIO "
					+ " INNER JOIN TIPO_ESTADO_VALORACION tev ON v.ID_TIPO_ESTADO_VALORACION = tev.ID_TIPO_ESTADO_VALORACION "
					+ " LEFT OUTER JOIN USUARIO uu ON v.ID_USUARIO_VALORA = uu.ID_USUARIO "
					+ " LEFT OUTER JOIN TRABAJO_REALIZADO tr ON v.ID_TRABAJO_REALIZADO_VALORADO = tr.ID_TRABAJO_REALIZADO ");

			boolean first = true;

			if(vc.getIdValoracion()!=null) {
				DAOUtils.addClause(queryString, first," v.ID_VALORACION = ? ");
				first = false;
			}

			if(vc.getIdProveedorValorado()!=null) {
				DAOUtils.addClause(queryString, first," ((u.ID_USUARIO= ?) AND (u.ID_TIPO_USUARIO = 2) AND ( v.ID_TIPO_ESTADO_VALORACION = 1 )) ");
				first = false;
			}

			if(vc.getIdTrabajoRealizadoValorado()!=null) {
				DAOUtils.addClause(queryString, first," (( v.ID_TRABAJO_REALIZADO_VALORADO = ? ) AND ( v.ID_TIPO_ESTADO_VALORACION = 1 )) ");
				first = false;
			}

			if(vc.getIdUsuarioValoraProveedor()!=null) {
				DAOUtils.addClause(queryString, first," ((uu.ID_USUARIO= ?) AND (u.ID_TIPO_USUARIO = 2) AND (v.ID_TIPO_ESTADO_VALORACION = 1)) ");
				first = false;
			}

			if(vc.getIdUsuarioValoraTrabajo()!=null) {
				DAOUtils.addClause(queryString, first," (( uu.ID_USUARIO = ? ) AND (v.ID_TIPO_ESTADO_VALORACION = 1) AND (v.ID_TRABAJO_REALIZADO_VALORADO IN (tr.ID_TRABAJO_REALIZADO))) ");
				first = false;
			}

			queryString.append(" ORDER BY v.FECHA_HORA_CREACION DESC ");

			preparedStatement = c.prepareStatement(queryString.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if(vc.getIdValoracion()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, vc.getIdValoracion());
			}

			if(vc.getIdProveedorValorado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, vc.getIdProveedorValorado());
			}

			if(vc.getIdTrabajoRealizadoValorado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, vc.getIdTrabajoRealizadoValorado());
			}

			if(vc.getIdUsuarioValoraProveedor()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, vc.getIdUsuarioValoraProveedor());
			}

			if(vc.getIdUsuarioValoraTrabajo()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, vc.getIdUsuarioValoraTrabajo());
			}

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			List<ValoracionDTO> valoracioness = new ArrayList<ValoracionDTO>();
			ValoracionDTO valoracion = null;			
			int resultsLoaded=0;

			if ((startIndex >=1) && rs.absolute(startIndex)) {
				do {
					valoracion = loadNext(rs);
					valoracioness.add(valoracion);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());
			}

			results.setData(valoracioness);
			results.setTotal(DAOUtils.getTotalRows(rs));

			if (logger.isTraceEnabled()) {
				logger.trace("End valoraciones: "+results);
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(results, sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return results;
	}




	@Override
	public long create(Connection c, ValoracionDTO valoracion)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin creando valoracion: "+valoracion);
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " INSERT INTO VALORACION(TITULO, DESCRIPCION, NOTA_VALORACION, FECHA_HORA_CREACION, ID_USUARIO_VALORA, "
					+ " ID_TIPO_ESTADO_VALORACION, ID_PROVEEDOR_VALORADO, ID_TRABAJO_REALIZADO_VALORADO) "
					+ " VALUES (?,?,?,?,?,?,?,?) ";

			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getTitulo());
			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getNotaValoracion());
			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getFechaHoraCreacion());
			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getIdUsuarioValora());
			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getIdTipoEstadoValoracion());
			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getIdProveedorValorado(), true);
			JDBCUtils.setParameter(preparedStatement, i++, valoracion.getIdTrabajoRealizadoValorado(), true);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					valoracion.setIdValoracion(rs.getLong(1));
				}

			} else {
				throw new DataException(valoracion.getTitulo());
			}

			if (logger.isTraceEnabled()) {
				logger.trace("End valoracion: "+valoracion+" creada");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(valoracion, sqle);
			}
			throw new DataException("Valoración : "+valoracion.getIdValoracion()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return valoracion.getIdValoracion();
	}




	@Override
	public int updateStatus(Connection c, Long idValoracion, Integer idEstadoValoracion) 
			throws DataException, ValoracionNotFoundException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin id valoracion: "+idValoracion);
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE VALORACION "
					+ "	SET ID_TIPO_ESTADO_VALORACION = ? "
					+ " WHERE ID_VALORACION = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idEstadoValoracion);
			JDBCUtils.setParameter(preparedStatement, i++, idValoracion);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {
				throw new ValoracionNotFoundException("Valoracion: "+idValoracion);
			}

			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(idValoracion, sqle);
			}
			throw new DataException("Valoracion: "+idValoracion+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}




	private static ValoracionDTO loadNext(ResultSet rs) 
			throws SQLException { 

		ValoracionDTO valoracion = new ValoracionDTO();
		int i = 1;

		valoracion.setIdValoracion(rs.getLong(i++));
		valoracion.setTitulo(rs.getString(i++));
		valoracion.setDescripcion(rs.getString(i++));
		valoracion.setNotaValoracion(rs.getInt(i++));
		valoracion.setFechaHoraCreacion(rs.getDate(i++));
		valoracion.setIdUsuarioValora(rs.getLong(i++));
		valoracion.setNombrePerfilUsuarioValora(rs.getString(i++));
		valoracion.setIdTrabajoRealizadoValorado(rs.getLong(i++));
		valoracion.setTituloTrabajoRealizadoValorado(rs.getString(i++));
		valoracion.setIdProveedorValorado(rs.getLong(i++));
		valoracion.setNombrePerfilProveedorValorado(rs.getString(i++));
		valoracion.setIdTipoEstadoValoracion(rs.getInt(i++));
		valoracion.setNombreTipoEstadoValoracion(rs.getString(i++));

		return valoracion;
	}	
}