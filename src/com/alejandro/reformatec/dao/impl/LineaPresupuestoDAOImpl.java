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
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.LineaPresupuestoNotFoundException;
import com.alejandro.reformatec.model.LineaPresupuestoCriteria;
import com.alejandro.reformatec.model.LineaPresupuestoDTO;


public class LineaPresupuestoDAOImpl implements LineaPresupuestoDAO{

	private static Logger logger = LogManager.getLogger(LineaPresupuestoDAOImpl.class);

	public LineaPresupuestoDAOImpl() {

	}



	@Override
	public List<LineaPresupuestoDTO> findByCriteria(Connection c, LineaPresupuestoCriteria pc)
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		List<LineaPresupuestoDTO> lista = new ArrayList<LineaPresupuestoDTO>();
		LineaPresupuestoDTO lpresupuesto = null;

		try {			
			StringBuilder queryString = new StringBuilder("SELECT lp.ID_LINEA_PRESUPUESTO, lp.IMPORTE, lp.DESCRIPCION, p.ID_PRESUPUESTO, p.TITULO, pr.ID_PROYECTO, pr.TITULO"
					+ " FROM LINEA_PRESUPUESTO lp "
					+ " INNER JOIN PRESUPUESTO p ON lp.ID_PRESUPUESTO = p.ID_PRESUPUESTO"
					+ " INNER JOIN PROYECTO pr ON p.ID_PROYECTO = pr.ID_PROYECTO");

			boolean first = true;

			if(pc.getIdLineaPresupuesto()!=null) {
				DAOUtils.addClause(queryString, first," pl.ID_LINEA_PRESUPUESTO = ? ");
				first = false;
			}

			if(pc.getIdPresupuesto()!=null) {
				DAOUtils.addClause(queryString, first," p.ID_PRESUPUESTO = ? ");
				first = false;
			}

			if(pc.getIdProyecto()!=null) {
				DAOUtils.addClause(queryString, first," pr.ID_PROYECTO = ? ");
				first = false;
			}

			queryString.append(" ORDER BY ID_LINEA_PRESUPUESTO ASC ");

			//create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if(pc.getIdLineaPresupuesto()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdLineaPresupuesto());
			}

			if(pc.getIdPresupuesto()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdPresupuesto());
			}

			if(pc.getIdProyecto()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdProyecto());
			}

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				lpresupuesto = loadNext(rs);
				lista.add(lpresupuesto);
			}

			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(lpresupuesto, sqle);
			}
			throw new DataException(sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return lista;
	}




	@Override
	public long create(Connection c, LineaPresupuestoDTO lineaPresupuesto) 
			throws DataException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " INSERT INTO LINEA_PRESUPUESTO(DESCRIPCION, ID_PRESUPUESTO, IMPORTE )"
					+ " VALUES (?,?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, lineaPresupuesto.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, lineaPresupuesto.getIdPresupuesto());
			JDBCUtils.setParameter(preparedStatement, i++, lineaPresupuesto.getImporte());

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			
			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					lineaPresupuesto.setIdLineaPresupuesto(rs.getLong(1));
				}

			} else {
				if (logger.isErrorEnabled()) {
					logger.error(lineaPresupuesto);
				}
				throw new DataException(""+lineaPresupuesto.getIdLineaPresupuesto());
			}

			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(lineaPresupuesto, sqle);
			}
			throw new DataException("Linea de Presupuesto: "+lineaPresupuesto.getIdLineaPresupuesto()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return lineaPresupuesto.getIdLineaPresupuesto();
	}


	//TODO No se actualiza una linea de presupuesto, se borran y se crean de nuevo

	@Override
	public long deleteById(Connection c, Long idLineaPresupuesto) 
			throws DataException, LineaPresupuestoNotFoundException{
		
		if (logger.isTraceEnabled()) {
			logger.trace("Begin");
		}

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int deleteRows = 0;

		try {
			String sql =" DELETE FROM LINEA_PRESUPUESTO lp"
					+ "  WHERE ID_LINEA_PRESUPUESTO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);
			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idLineaPresupuesto);

			if (logger.isInfoEnabled()) {
				logger.info(preparedStatement);
			}
			deleteRows = preparedStatement.executeUpdate();

			if (deleteRows!=1) {
				throw new LineaPresupuestoNotFoundException("Linea de Presupuesto: "+idLineaPresupuesto+" cant delete");
			} 

			if (logger.isTraceEnabled()) {
				logger.trace("End");
			}

		} catch (SQLException sqle) {			
			if (logger.isErrorEnabled()) {
				logger.error(idLineaPresupuesto, sqle);
			}
			throw new DataException("Linea de Presupuesto: "+idLineaPresupuesto+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return deleteRows;
	}





	private static LineaPresupuestoDTO loadNext(ResultSet rs) 
			throws SQLException { 

		LineaPresupuestoDTO lpresupuesto = new LineaPresupuestoDTO();

		int i = 1;

		lpresupuesto.setIdLineaPresupuesto(rs.getLong(i++));
		lpresupuesto.setImporte(rs.getDouble(i++));
		lpresupuesto.setDescripcion(rs.getString(i++));
		lpresupuesto.setIdPresupuesto(rs.getLong(i++));
		lpresupuesto.setTituloPresupuesto(rs.getString(i++));
		lpresupuesto.setIdProyecto(rs.getLong(i++));
		lpresupuesto.setTituloProyecto(rs.getString(i++));

		return lpresupuesto;
	}
}