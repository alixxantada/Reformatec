package com.alejandro.reformatec.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.model.TrabajoRealizadoCriteria;
import com.alejandro.reformatec.model.TrabajoRealizadoDTO;
import com.alejandro.reformatec.service.TrabajoRealizadoService;
import com.alejandro.reformatec.service.impl.TrabajoRealizadoServiceImpl;

public class TrabajoRealizadoServiceTest {

	private static Logger logger = LogManager.getLogger(TrabajoRealizadoServiceTest.class);

	private TrabajoRealizadoService trabajoRealizadoservice = null;
	private TrabajoRealizadoDTO trabajoRealizado = new TrabajoRealizadoDTO();
	private Results<TrabajoRealizadoDTO> results = new Results<TrabajoRealizadoDTO>();
	TrabajoRealizadoCriteria trc = new TrabajoRealizadoCriteria();
	
	public TrabajoRealizadoServiceTest() {
		trabajoRealizadoservice = new TrabajoRealizadoServiceImpl();
	}


	public void testFindByCriteria() {
		logger.trace("Begin...");
		
		////////////////////////////////////////////////////////
		trc.setDescripcion("reto");
		trc.setIdProvincia(null);
		trc.setIdProveedor(null);
		trc.setIdTrabajoRealizado(null);
		trc.setIdEspecializacion(null);
		////////////////////
		trc.setOrderBy(null);
		// OrderBy opcion "VAL" -  ORDER BY AVG(v.NOTA_VALORACION) DESC 
		// OrderBy opcion "NV" -  ORDER BY tr.NUM_VISUALIZACION DESC
		// Orderby opcion "FC" -  ORDER BY tr.FECHA_CREACION ASC 
		////////////////////////////////////////////////////////

		try {			

			int startIndex = 1;
			int pageSize = 3;

			results = trabajoRealizadoservice.findByCriteria(trc, startIndex, pageSize);
			logger.info("Found: "+(results.getTotal()));
			for (TrabajoRealizadoDTO tr: results.getData()) {
				logger.info(tr);
				logger.info(tr.getNotaValoracion());
			}

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(trc, de);
		} catch (ServiceException se) {
			logger.error(trc, se);
		}
	}



	public void testCreate() 
			throws ServiceException, DataException {
		logger.trace("Begin...");
		List<Integer> idsEspecializaciones = new ArrayList<Integer>();
		
		///////////////////////////////////////////////
		String titulo = "Prueba trabajo realizado";
		String descripcion = " este trabajo realizado parece que va tirando";
		Long idUsuarioCreador = 2L;
		Integer idPoblacion = 1;
		Long idProyectoAsociado = null;
		
		idsEspecializaciones.add(1);
		idsEspecializaciones.add(2);
		idsEspecializaciones.add(3);
		//////////////////////////////////////////////
		trabajoRealizado.setTitulo(titulo);
		trabajoRealizado.setDescripcion(descripcion);
		trabajoRealizado.setIdUsuarioCreadorTrabajo(idUsuarioCreador);
		trabajoRealizado.setIdPoblacion(idPoblacion);
		trabajoRealizado.setIdProyectoAsociado(idProyectoAsociado);

		try {

			trabajoRealizadoservice.create(trabajoRealizado, idsEspecializaciones);

			int startIndex = 1;
			int pageSize = 1;

			trc.setIdTrabajoRealizado(trabajoRealizado.getIdTrabajoRealizado());
			results = trabajoRealizadoservice.findByCriteria(trc, startIndex, pageSize);
			
			for(TrabajoRealizadoDTO tr : results.getData()) {
				logger.info("Trabajo Realizado Actualizado!: "+tr);	
			}
			

			logger.trace("End!");
		} catch (DataException de) { 
			logger.error(trabajoRealizado, de);	
			throw de;	
		} catch (ServiceException se) {
			logger.error(trabajoRealizado, se);
		}
	}



	public void testUpdate() {
		logger.trace("Begin...");

		///////////////////////////
		Long idTrabajoRealizado = 4L;
		String titulo = "trabajo de pruebas";
		String descripcion = "probando a cambiar descripcion del trabajo realizado";
		int idPoblacion = 1;
		Long idProyectoAsociado = null;
		
		List<Integer> idsEspecializaciones = new ArrayList<Integer>();
		
		idsEspecializaciones.add(1);
		idsEspecializaciones.add(2);
		idsEspecializaciones.add(3);
		///////////////////////////
		try {

			int startIndex = 1;
			int pageSize = 1;

			trc.setIdTrabajoRealizado(idTrabajoRealizado);
			results = trabajoRealizadoservice.findByCriteria(trc, startIndex, pageSize);
			logger.info("Trabajo Realizado Antes de actualizar!: "+results.getData());

			
			trabajoRealizado.setIdTrabajoRealizado(idTrabajoRealizado);
			trabajoRealizado.setTitulo(titulo);
			trabajoRealizado.setDescripcion(descripcion);
			trabajoRealizado.setIdPoblacion(idPoblacion);
			trabajoRealizado.setIdProyectoAsociado(idProyectoAsociado);
			//TODO solo se deber?a poder asociar proyectos que tuvieran presupuestos aceptados por parte del creador del trabajo realizado..

			trabajoRealizadoservice.update(trabajoRealizado, idsEspecializaciones);

			results = trabajoRealizadoservice.findByCriteria(trc, startIndex, pageSize);
			
			for(TrabajoRealizadoDTO tr : results.getData()) {
				logger.info("Trabajo Realizado Actualizado!: "+tr);	
			}
			

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(trabajoRealizado, de);	
		}  catch(ServiceException se){
			logger.error(trabajoRealizado, se);	
		}
	}



	public void testupdateStatus() {
		logger.trace("Begin...");

		///////////////////////////
		Long idtrabajorealizado = 2L;
		int idestado = 2;
		///////////////////////////
		try {


			int startIndex = 1;
			int pageSize = 1;

			trc.setIdTrabajoRealizado(idtrabajorealizado);			
			results = trabajoRealizadoservice.findByCriteria(trc, startIndex, pageSize);			
			logger.info("Estado del trabajo Trabajo Realizado Antes de actualizar!: "+results.getData());

			trabajoRealizadoservice.updateStatus(idtrabajorealizado, idestado);

			results = trabajoRealizadoservice.findByCriteria(trc, startIndex, pageSize);
			logger.info("Estado del Trabajo Realizado actualizado!: "+results.getData());

			logger.trace("End!");
			
		} catch(DataException de) {
			logger.error(de);	
		}  catch(ServiceException se){
			logger.error(se);	
		}
	}



	public static void main (String args[])
			throws ServiceException, DataException {

		TrabajoRealizadoServiceTest test = new TrabajoRealizadoServiceTest();

		test.testFindByCriteria();
		//test.testCreate();
		//test.testUpdate();
		//test.testupdateStatus();
	}

}