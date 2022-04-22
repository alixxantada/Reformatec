package com.alejandro.reformatec.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.dao.ContadorDAO;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;
import com.alejandro.reformatec.model.UsuarioDTO;

public class ContadorDAOImpl implements ContadorDAO {

	private static Logger logger = LogManager.getLogger(ContadorDAOImpl.class);

	public ContadorDAOImpl() {

	}


	@Override
	public Integer cliente(Connection c)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Integer clientes = null;		
		List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
		UsuarioDTO usuario = new UsuarioDTO();

		try {			
			String sql ="SELECT u.ID_USUARIO "
					+ " FROM USUARIO u "
					+ " WHERE ((u.ID_TIPO_USUARIO = 1)&&((u.ID_TIPO_ESTADO_CUENTA = 1)||(u.ID_TIPO_ESTADO_CUENTA = 2)))";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int i = 1;
				usuario.setIdUsuario(rs.getLong(i++));
				lista.add(usuario);
			}

			clientes = lista.size();
			
			if (logger.isTraceEnabled()) {
				logger.trace("End cantidad clientes: "+clientes);
			}
			
		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(clientes, sqle);
			}
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return clientes;
	}

	
	
	@Override
	public Integer proveedor(Connection c)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Integer proveedores = null;		
		List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
		UsuarioDTO usuario = new UsuarioDTO();

		try {			
			String sql ="SELECT u.ID_USUARIO "
					+ " FROM USUARIO u "
					+ " WHERE ((u.ID_TIPO_USUARIO = 2)&&((u.ID_TIPO_ESTADO_CUENTA = 1)||(u.ID_TIPO_ESTADO_CUENTA = 2)))";


			//create prepared statement
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int i = 1;
				usuario.setIdUsuario(rs.getLong(i++));
				lista.add(usuario);
			}

			proveedores = lista.size();
			
			if (logger.isTraceEnabled()) {
				logger.trace("End cantidad proveedores: "+proveedores);
			}
			
		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(proveedores, sqle);
			}
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return proveedores;
	}



	@Override
	public Integer proyectoActivo(Connection c)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Integer proyectosActivos = null;		
		List<ProyectoDTO> lista = new ArrayList<ProyectoDTO>();
		ProyectoDTO proyecto = new ProyectoDTO();

		try {			
			String sql ="SELECT p.ID_PROYECTO "
					+ " FROM PROYECTO p "
					+ " WHERE p.ID_TIPO_ESTADO_PROYECTO = 1";


			//create prepared statement
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int i = 1;
				proyecto.setIdProyecto(rs.getLong(i++));
				lista.add(proyecto);
			}

			proyectosActivos = lista.size();
			
			if (logger.isTraceEnabled()) {
				logger.trace("End cantidad proyectos activos: "+proyectosActivos);
			}
			
		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(proyectosActivos, sqle);
			}
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return proyectosActivos;
	}



	@Override
	public Integer proyectoFinalizado(Connection c)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Integer proyectosFinalizados = null;		
		List<ProyectoDTO> lista = new ArrayList<ProyectoDTO>();
		ProyectoDTO proyecto = new ProyectoDTO();

		try {			
			String sql ="SELECT p.ID_PROYECTO "
					+ " FROM PROYECTO p "
					+ " WHERE p.ID_TIPO_ESTADO_PROYECTO = 2";


			//create prepared statement
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int i = 1;
				proyecto.setIdProyecto(rs.getLong(i++));
				lista.add(proyecto);
			}

			proyectosFinalizados = lista.size();
			
			if (logger.isTraceEnabled()) {
				logger.trace("End cantidad proyectos finalizados: "+proyectosFinalizados);
			}
			
		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(proyectosFinalizados, sqle);
			}
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return proyectosFinalizados;
	}



	@Override
	public Integer trabajoRealizado(Connection c)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Integer trabajosRealizados = null;		
		List<TrabajoRealizadoDTO> lista = new ArrayList<TrabajoRealizadoDTO>();
		TrabajoRealizadoDTO proyecto = new TrabajoRealizadoDTO();

		try {			
			String sql ="SELECT tr.ID_TRABAJO_REALIZADO "
					+ " FROM TRABAJO_REALIZADO tr "
					+ " WHERE tr.ID_TIPO_ESTADO_TRABAJO_REALIZADO = 1";


			//create prepared statement
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int i = 1;
				proyecto.setIdTrabajoRealizado(rs.getLong(i++));
				lista.add(proyecto);
			}

			trabajosRealizados = lista.size();
			
			if (logger.isTraceEnabled()) {
				logger.trace("End cantidad de trabajos realizados: "+trabajosRealizados);
			}
			
		} catch (SQLException sqle) {
			if (logger.isErrorEnabled()) {
				logger.error(trabajosRealizados, sqle);
			}
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return trabajosRealizados;
	}
}