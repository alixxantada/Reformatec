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
import com.alejandro.reformatec.dao.UsuarioDAO;
import com.alejandro.reformatec.dao.util.DAOUtils;
import com.alejandro.reformatec.dao.util.JDBCUtils;
import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.UserNotFoundException;
import com.alejandro.reformatec.model.Especializacion;
import com.alejandro.reformatec.model.EspecializacionCriteria;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.UsuarioCriteria;
import com.alejandro.reformatec.model.UsuarioDTO;


public class UsuarioDAOImpl implements UsuarioDAO {

	private static Logger logger = LogManager.getLogger(UsuarioDAOImpl.class);

	private static Map<String, String> SORTING_CRITERIA_MAP = null;

	static {
		SORTING_CRITERIA_MAP = new HashMap<String, String>();

		SORTING_CRITERIA_MAP.put("VAL", "AVG(v.NOTA_VALORACION) DESC");
		SORTING_CRITERIA_MAP.put("NV", "u.NUM_VISUALIZACION DESC");
	}


	private EspecializacionDAO especializacionDAO = null;

	public UsuarioDAOImpl() {
		especializacionDAO = new EspecializacionDAOImpl();
	}




	@Override
	public Results<UsuarioDTO> findByCriteria(Connection c, UsuarioCriteria pc, int startIndex, int pageSize)
			throws DataException{
		logger.trace("Begin "+pc);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Results<UsuarioDTO> results = new Results<UsuarioDTO>();

		try {

			StringBuilder queryString = new StringBuilder("SELECT u.ID_USUARIO, u.NOMBRE, u.APELLIDO1, u.APELLIDO2, u.NIF, u.TELEFONO1, u.TELEFONO2, u.EMAIL, u.NOMBRE_PERFIL, "
					+ " u.NOMBRE_CALLE, u.COD_POSTAL, u.ENCRYPTED_PASSWORD, u.ID_POBLACION_USUARIO, pl.NOMBRE, pl.ID_PROVINCIA, "
					+ " pr.NOMBRE, u.ID_TIPO_USUARIO, tu.NOMBRE, u.ID_TIPO_ESTADO_CUENTA, tec.NOMBRE , AVG(v.NOTA_VALORACION), COUNT(DISTINCT(v.ID_VALORACION)), "
					+ " u.NUM_VISUALIZACION, u.DESCRIPCION, u.DIRECCION_WEB, u.CIF, u.SERVICIO24, u.EXPERTO_NEGOCIO, u.PROVEEDOR_VERIFICADO, "
					+ " e.ID_ESPECIALIZACION, e.NOMBRE "
					+ " FROM USUARIO u "
					+ " LEFT OUTER JOIN USUARIO_ESPECIALIZACION ue ON u.ID_USUARIO = ue.ID_USUARIO "
					+ " LEFT OUTER JOIN ESPECIALIZACION e ON ue.ID_ESPECIALIZACION = e.ID_ESPECIALIZACION "
					+ " LEFT OUTER JOIN VALORACION v ON u.ID_USUARIO = v.ID_PROVEEDOR_VALORADO "
					+ " INNER JOIN TIPO_ESTADO_CUENTA tec ON u.ID_TIPO_ESTADO_CUENTA= tec.ID_TIPO_ESTADO_CUENTA "
					+ " INNER JOIN TIPO_USUARIO tu ON u.ID_TIPO_USUARIO= tu.ID_TIPO_USUARIO "
					+ " INNER JOIN POBLACION pl ON u.ID_POBLACION_USUARIO= pl.ID_POBLACION "
					+ " INNER JOIN PROVINCIA pr ON pl.ID_PROVINCIA = pr.ID_PROVINCIA ");

			boolean first = true;

			if(pc.getIdUsuario()!=null) {
				DAOUtils.addClause(queryString, first," u.ID_USUARIO = ? ");
				first = false;
			}

			if(pc.getEmail()!=null) {
				DAOUtils.addClause(queryString, first," (( UPPER(u.EMAIL) LIKE UPPER(?) ) AND ( u.ID_TIPO_ESTADO_CUENTA = 2 )) ");
				first = false;
			}

			if(pc.getIdUsuarioFavorito()!=null) {
				DAOUtils.addClause(queryString, first," ((uf.ID_USUARIO_SIGUE = ?) AND (u.ID_TIPO_ESTADO_CUENTA = 2))  ");
				first = false;
			}
			if(pc.getDescripcion()!=null) {
				DAOUtils.addClause(queryString, first," (UPPER(u.NOMBRE_PERFIL) LIKE UPPER(?)) OR (UPPER(u.DESCRIPCION) LIKE UPPER(?)) ");
				first = false;
			}

			if(pc.getIdProvincia()!=null){
				DAOUtils.addClause(queryString, first," pr.ID_PROVINCIA = ? ");
				first = false;
			}

			if(pc.getIdPoblacion()!=null) {
				DAOUtils.addClause(queryString, first," pl.ID_POBLACION = ? ");
				first = false;
			}
			if(pc.getExpertoNegocio()!=null) {
				DAOUtils.addClause(queryString, first," u.EXPERTO_NEGOCIO = ? ");
				first = false;
			}

			if(pc.getServicio24()!=null) {
				DAOUtils.addClause(queryString, first," u.SERVICIO24 = ? ");
				first = false;
			}

			if(pc.getProveedorVerificado()!=null) {
				DAOUtils.addClause(queryString, first," u.PROVEEDOR_VERIFICADO = ? ");
				first = false;
			}

			if(pc.getIdEspecializacion()!=null) {
				DAOUtils.addClause(queryString, first," ue.ID_ESPECIALIZACION = ? ");
				first = false;
			}


			DAOUtils.addClause(queryString, first, " u.ID_TIPO_ESTADO_CUENTA = 2 AND u.ID_TIPO_USUARIO = 2 GROUP BY u.ID_USUARIO ");

			if(pc.getOrderBy()!=null) {
				queryString.append(" ORDER BY "+ SORTING_CRITERIA_MAP.get(pc.getOrderBy()));
			} else {
				queryString.append(" ORDER BY AVG(u.NUM_VISUALIZACION) DESC ");
			}

			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;

			if(pc.getIdUsuario()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdUsuario());
			}

			if(pc.getEmail()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getEmail());
			}

			if(pc.getIdUsuarioFavorito()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdUsuarioFavorito());
			}

			if(pc.getDescripcion()!=null) {
				StringBuilder a = new StringBuilder("%");
				a.append(pc.getDescripcion()).append("%");
				// Lo mete en el primer interrogante
				JDBCUtils.setParameter(preparedStatement, i++, a.toString()); // descripcion
				// Lo mete en el segundo interrogante
				JDBCUtils.setParameter(preparedStatement, i++, a.toString()); // nombre_perfil
			}			

			if(pc.getIdProvincia()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdProvincia());
			}

			if(pc.getIdPoblacion()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdPoblacion());
			}

			if(pc.getExpertoNegocio()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getExpertoNegocio());
			}

			if(pc.getServicio24()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getServicio24());
			}

			if(pc.getProveedorVerificado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getProveedorVerificado());
			}	

			if (pc.getIdEspecializacion()!=null ) {
				JDBCUtils.setParameter(preparedStatement, i++, pc.getIdEspecializacion());		
			}


			logger.trace(preparedStatement);
			rs = preparedStatement.executeQuery();

			List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
			UsuarioDTO usuario = null;
			int resultsLoaded = 0;
			// dirigimos el puntero a donde empezamos a mostrar resultados en nuestro resulset(si hay almenos 1 resultado)
			if ((startIndex >=1) && rs.absolute(startIndex)) {
				// empezamos recorriendolo ya 1 vez para sacar el primer resultado! luego si hay mas lo repetimos! de lo contrario se salta el primero
				do { 
					usuario = loadNext(c, rs);
					usuarios.add(usuario);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());				
			}

			results.setData(usuarios);
			results.setTotal(DAOUtils.getTotalRows(rs));

			logger.trace("End "+results);

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
	public void anhadirFavorito(Connection c , Long idCliente, Long idProveedor)
			throws DataException{
		logger.trace("Begin id Cliente: "+idCliente+", Id Proveedor: "+idProveedor);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " INSERT INTO USUARIO_FAVORITO(ID_USUARIO_SIGUE, ID_USUARIO_SEGUIDO) "
					+ " VALUES (?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idCliente);
			JDBCUtils.setParameter(preparedStatement, i++, idProveedor);

			logger.trace(preparedStatement);
			preparedStatement.executeUpdate();

			logger.trace("End id Cliente: "+idCliente+", Id Proveedor: "+idProveedor);

		} catch (SQLException sqle) {			
			logger.error("Error id Cliente: "+idCliente+", id Proveedor: "+idProveedor+sqle);
			throw new DataException("Error id Cliente: "+idCliente+", id Proveedor: "+idProveedor+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}



	@Override
	public void deleteFavorito(Connection c, Long idCliente, Long idProveedor)
			throws DataException {
		logger.trace("Begin id Cliente: "+idCliente+", Id Proveedor: "+idProveedor);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " DELETE FROM USUARIO_FAVORITO "
					+ " WHERE (ID_USUARIO_SIGUE = ? AND ID_USUARIO_SEGUIDO= ?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idCliente);
			JDBCUtils.setParameter(preparedStatement, i++, idProveedor);

			logger.trace(preparedStatement);
			preparedStatement.executeUpdate();

			logger.trace("End id Cliente: "+idCliente+", Id Proveedor: "+idProveedor);

		} catch (SQLException sqle) {			
			logger.error("Error id Cliente: "+idCliente+", id Proveedor: "+idProveedor+sqle);
			throw new DataException("Error id Cliente: "+idCliente+", id Proveedor: "+idProveedor+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}

	
	
	@Override
	public Boolean compruebaFavorito(Connection c, Long idCliente, Long idProveedor) 
			throws DataException{

		logger.trace("Begin id cliente: "+idCliente+", id Proveedor: "+idProveedor);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Boolean favorito = null;

		try {

			String sql =" SELECT u.ID_USUARIO, u.NOMBRE, u.APELLIDO1, u.APELLIDO2, u.NIF, u.TELEFONO1, u.TELEFONO2, u.EMAIL, u.NOMBRE_PERFIL, "
					+ " u.NOMBRE_CALLE, u.COD_POSTAL, u.ENCRYPTED_PASSWORD, u.ID_POBLACION_USUARIO, pl.NOMBRE, pl.ID_PROVINCIA, "
					+ " pr.NOMBRE, u.ID_TIPO_USUARIO, tu.NOMBRE, u.ID_TIPO_ESTADO_CUENTA, tec.NOMBRE , AVG(v.NOTA_VALORACION), COUNT(DISTINCT(v.ID_VALORACION)), "
					+ " u.NUM_VISUALIZACION, u.DESCRIPCION, u.DIRECCION_WEB, u.CIF, u.SERVICIO24, u.EXPERTO_NEGOCIO, u.PROVEEDOR_VERIFICADO, "
					+ " e.ID_ESPECIALIZACION, e.NOMBRE "
					+ " FROM USUARIO_FAVORITO uf "
					+ " INNER JOIN USUARIO u ON u.ID_USUARIO = uf.ID_USUARIO_SEGUIDO "  
					+ " LEFT OUTER JOIN USUARIO_ESPECIALIZACION ue ON u.ID_USUARIO = ue.ID_USUARIO "
					+ " LEFT OUTER JOIN ESPECIALIZACION e ON ue.ID_ESPECIALIZACION = e.ID_ESPECIALIZACION "
					+ " LEFT OUTER JOIN VALORACION v ON u.ID_USUARIO = v.ID_PROVEEDOR_VALORADO "
					+ " INNER JOIN TIPO_ESTADO_CUENTA tec ON u.ID_TIPO_ESTADO_CUENTA= tec.ID_TIPO_ESTADO_CUENTA " 
					+ " INNER JOIN TIPO_USUARIO tu ON u.ID_TIPO_USUARIO = tu.ID_TIPO_USUARIO "
					+ " INNER JOIN POBLACION pl ON u.ID_POBLACION_USUARIO = pl.ID_POBLACION "
					+ " INNER JOIN PROVINCIA pr ON pl.ID_PROVINCIA = pr.ID_PROVINCIA "
					+ " WHERE ((uf.ID_USUARIO_SIGUE = ?) AND (uf.ID_USUARIO_SEGUIDO = ?)) "
					+ " GROUP BY uf.ID_USUARIO_SEGUIDO ";


			//create prepared statement
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			JDBCUtils.setParameter(preparedStatement, 1, idCliente);
			JDBCUtils.setParameter(preparedStatement, 2, idProveedor);

			logger.trace(preparedStatement);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				favorito = true;
			}

		}catch (SQLException sqle) {			
			logger.error(sqle);
			throw new DataException("Error favorito: "+favorito, sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return favorito;
	}




	public void visualiza(Connection c, Long id)
			throws DataException, UserNotFoundException{
		logger.trace("Begin "+id);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		UsuarioDTO usuario = null;

		try {			 
			String sql =" UPDATE USUARIO "
					+ "	SET NUM_VISUALIZACION = NUM_VISUALIZACION +1 "
					+ " WHERE ID_USUARIO = ? ";


			//create prepared statement
			preparedStatement = c.prepareStatement(sql);
			JDBCUtils.setParameter(preparedStatement, 1, id);

			logger.trace(preparedStatement);
			preparedStatement.executeUpdate();


			logger.trace("End ");

		} catch (SQLException sqle) {			
			logger.error(usuario, sqle);
			throw new DataException("Error id usuario: "+id, sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}




	@Override
	public long create(Connection c, UsuarioDTO usuario) 
			throws DataException {
		logger.trace("Begin "+usuario);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String sql = " INSERT INTO USUARIO(NOMBRE, APELLIDO1, APELLIDO2, NIF, TELEFONO1, TELEFONO2, EMAIL, NOMBRE_PERFIL, "
					+ " NOMBRE_CALLE, COD_POSTAL, ENCRYPTED_PASSWORD, ID_POBLACION_USUARIO, ID_TIPO_USUARIO, ID_TIPO_ESTADO_CUENTA, CIF, "
					+ " DESCRIPCION, DIRECCION_WEB, SERVICIO24, EXPERTO_NEGOCIO, PROVEEDOR_VERIFICADO) "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			//create prepared statement
			logger.trace(preparedStatement);
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getApellido1());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getApellido2());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNif());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono1());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono2(), true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getEmail());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombrePerfil());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombreCalle());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getCodigoPostal());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getEncryptedPassword());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdPoblacion());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdTipoUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdTipoEstadoCuenta());
			// poniendo true indicamos que puede ser null(JDBCutils)
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getCif(), true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getDescripcion(), true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getDireccionWeb(), true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getServicio24(), true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getExpertoNegocio(), true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getProveedorVerificado(), true); 

			logger.trace(preparedStatement);
			int insertedRows = preparedStatement.executeUpdate();
			// si inserta una fila, en la primera posicion genera clave AI con el metodo generatedkeys
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					usuario.setIdUsuario(rs.getLong(1));
				}

			} else {
				throw new DataException(usuario.getNombrePerfil());
			}

			logger.trace("End "+usuario);

		} catch (SQLException sqle) {			
			logger.error("Error usuario: "+usuario, sqle);
			throw new DataException("User: "+usuario.getIdUsuario()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return usuario.getIdUsuario();
	}




	@Override
	public int update(Connection c, UsuarioDTO usuario) 
			throws DataException, UserNotFoundException {
		logger.trace("Begin "+usuario);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE USUARIO "
					+ " SET		TELEFONO1 = ?,"
					+ "			TELEFONO2 = ?,"
					+ "			NOMBRE_PERFIL = ?,"
					+ "			NOMBRE_CALLE = ?,"
					+ "			COD_POSTAL = ?,"
					+ "			ENCRYPTED_PASSWORD = ?,"
					+ "			ID_POBLACION_USUARIO = ?,"
					+ "			DESCRIPCION = ?,"
					+ "			DIRECCION_WEB = ?,"
					+ "			SERVICIO24 = ? ,"
					+ "			EXPERTO_NEGOCIO = ? "
					+ "  WHERE ID_USUARIO = ? ";	

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono1());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono2(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombrePerfil());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getNombreCalle());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getCodigoPostal());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getEncryptedPassword());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdPoblacion());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getDescripcion(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getDireccionWeb(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getServicio24(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getExpertoNegocio(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdUsuario());

			logger.trace(preparedStatement);
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {
				throw new UserNotFoundException("User: "+usuario.getIdUsuario());
			}

			logger.trace("End "+usuario);

		} catch (SQLException sqle) {			
			logger.error("Error usuario: "+usuario, sqle);
			throw new DataException("User: "+usuario.getIdUsuario()+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}




	@Override
	public int updateStatus(Connection c, Long idUsuario, Integer idEstadoCuenta) 
			throws DataException, UserNotFoundException{
		logger.trace("Begin "+idUsuario, idEstadoCuenta);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int updatedRows = 0;

		try {

			String sql =" UPDATE USUARIO "
					+ "	SET ID_TIPO_ESTADO_CUENTA = ? "
					+ " WHERE ID_USUARIO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idEstadoCuenta);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);

			logger.trace(preparedStatement);
			updatedRows = preparedStatement.executeUpdate();

			if (updatedRows!=1) {
				throw new UserNotFoundException("User: "+idUsuario);
			}

			logger.trace("End "+idUsuario, idEstadoCuenta);

		} catch (SQLException sqle) {			
			logger.error("Error id Usuario: "+idUsuario, sqle);
			throw new DataException("User: "+idUsuario+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}




	@Override
	public long deleteById(Connection c, Long idUsuario) 
			throws DataException, UserNotFoundException {
		logger.trace("Begin "+idUsuario);

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		int deleteRows = 0;

		try {

			String sql =" DELETE FROM USUARIO "
					+ "  WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);
			JDBCUtils.setParameter(preparedStatement, idUsuario);

			logger.trace(preparedStatement);
			deleteRows = preparedStatement.executeUpdate();

			if (deleteRows!=1) {
				throw new UserNotFoundException("User: "+idUsuario+" cant delete");
			}

			logger.trace("End "+idUsuario);

		} catch (SQLException sqle) {			
			logger.error("Error id Usuario: "+idUsuario, sqle);
			throw new DataException("User: "+idUsuario+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return deleteRows;
	}




	private UsuarioDTO loadNext(Connection c, ResultSet rs) 
			throws SQLException, DataException { 

		UsuarioDTO usuario = new UsuarioDTO();

		int i = 1;

		usuario = new UsuarioDTO();
		usuario.setIdUsuario(rs.getLong(i++));
		usuario.setNombre(rs.getString(i++));
		usuario.setApellido1(rs.getString(i++));
		usuario.setApellido2(rs.getString(i++));
		usuario.setNif(rs.getString(i++));
		usuario.setTelefono1(rs.getString(i++));
		usuario.setTelefono2(rs.getString(i++));
		usuario.setEmail(rs.getString(i++));
		usuario.setNombrePerfil(rs.getString(i++));
		usuario.setNombreCalle(rs.getString(i++));
		usuario.setCodigoPostal(rs.getString(i++));
		usuario.setEncryptedPassword(rs.getString(i++));
		usuario.setIdPoblacion(rs.getInt(i++));
		usuario.setNombrePoblacion(rs.getString(i++));
		usuario.setIdProvincia(rs.getInt(i++));
		usuario.setNombreProvincia(rs.getString(i++));
		usuario.setIdTipoUsuario(rs.getInt(i++));
		usuario.setNombreTipoUsuario(rs.getString(i++));
		usuario.setIdTipoEstadoCuenta(rs.getInt(i++));
		usuario.setNombreTipoEstadoCuenta(rs.getString(i++));


		// Atributos de proveedores		
		usuario.setValoracionMedia(rs.getDouble(i++));
		usuario.setNumeroValoraciones(rs.getInt(i++));
		usuario.setNumeroVisualizaciones(rs.getLong(i++));
		usuario.setDescripcion(rs.getString(i++));
		usuario.setDireccionWeb(rs.getString(i++));
		usuario.setCif(rs.getString(i++));

		Object servicio24 = rs.getObject(i);

		if(servicio24!=null) {
			usuario.setServicio24(rs.getBoolean(i++));
		}else {
			usuario.setServicio24(null);
			i++;
		}

		Object expertoNegocio = rs.getObject(i);
		if (expertoNegocio!=null) {
			usuario.setExpertoNegocio(rs.getBoolean(i++));
		}

		Object proveedorVerificado = rs.getObject(i);
		if(proveedorVerificado!=null) {
			usuario.setProveedorVerificado(rs.getBoolean(i++));
		}

		EspecializacionCriteria ec = new EspecializacionCriteria();
		ec.setIdUsuario(usuario.getIdUsuario());
		List<Especializacion> especializaciones = especializacionDAO.findByCriteria(c, ec);
		usuario.setEspecializaciones(especializaciones);

		return usuario;
	}
}