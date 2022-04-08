package com.alejandro.reformatec.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alejandro.reformatec.exception.DataException;
import com.alejandro.reformatec.exception.ProyectoAlreadyExistsException;
import com.alejandro.reformatec.exception.ServiceException;
import com.alejandro.reformatec.model.ProyectoCriteria;
import com.alejandro.reformatec.model.ProyectoDTO;
import com.alejandro.reformatec.model.Results;
import com.alejandro.reformatec.service.ProyectoService;
import com.alejandro.reformatec.service.impl.ProyectoServiceImpl;

public class ProyectoServiceTest {

	private static Logger logger = LogManager.getLogger(ProyectoServiceTest.class);

	private ProyectoService proyectoservice = null;
	private ProyectoDTO proyecto = new ProyectoDTO();
	private ProyectoCriteria pc = new ProyectoCriteria();
	private Results<ProyectoDTO> results = new Results<ProyectoDTO>();
	
	public ProyectoServiceTest(){
		proyectoservice = new ProyectoServiceImpl();
	}


	public void testFindByCriteria() {
		logger.trace("Begin...");	

		ProyectoCriteria pc = new ProyectoCriteria();
		////////////////////////////////////////////////////////
		pc.setDescripcion(null);
		pc.setIdProvincia(null);
		pc.setPresupuestoMaxMinimo(null);
		pc.setPresupuestoMaxMaximo(null);
		pc.setIdEspecializacion(null);
		pc.setOrderBy(null);
		pc.setIdProyecto(null);
		pc.setIdUsuarioCreador(1L);
		///////////////////////////////////////
		// OrderBy opcion PMASC -  ORDER BY p.PRESUPUESTO_MAX ASC 
		// OrderBy opcion PMDESC -  ORDER BY p.PRESUPUESTO_MAX DESC
		// Orderby opcion FC -  ORDER BY p.FECHA_CREACION DESC
		/////////////////////////////////////////////////////////

		int startIndex = 1;
		int pageSize = 3;
		try {

			Results<ProyectoDTO> results = proyectoservice.findByCriteria(pc, startIndex, pageSize);	
			logger.info("Found "+results.getTotal());
			for (ProyectoDTO p: results.getData()) {
				logger.info(p);
			}

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(pc,de);
		} catch (ServiceException se) {
			logger.error(pc,se);
		}
	}




	public void testCreate() 
			throws ServiceException, DataException {
		logger.trace("Begin...");
		
		List<Integer> idsEspecializaciones = new ArrayList<Integer>();
		//////////////////////////////////////////////////////
		String titulo = "Probaproyecto";
		String descripcion = "Que quede perfecto, gracias ";
		int presupuestoMax = 1700;
		int idPoblacion = 1;
		Long idUsuarioCreadorProyecto = 1L;
		
		idsEspecializaciones.add(1);
		idsEspecializaciones.add(2);
		idsEspecializaciones.add(3);
		/////////////////////////////////////////////////////
		proyecto.setTitulo(titulo);
		proyecto.setDescripcion(descripcion);
		proyecto.setPresupuestoMax(presupuestoMax);
		proyecto.setIdPoblacion(idPoblacion);
		proyecto.setIdUsuarioCreadorProyecto(idUsuarioCreadorProyecto);
		proyecto.setIdsEspecializaciones(idsEspecializaciones);
		
		try {
			
			proyectoservice.create(proyecto);

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdProyecto(proyecto.getIdProyecto());
			results = proyectoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Proyecto Creado!: "+results.getData());

			logger.trace("End!");
		}catch (DataException de) { 
			logger.error(proyecto,de);	
			throw de;	
		}catch (ProyectoAlreadyExistsException pae) {
			logger.error(proyecto,pae);
		} catch (ServiceException se) {
			logger.error(proyecto,se);
		}
	}



	public void testUpdate() {
		logger.trace("Begin...");
		
		ProyectoCriteria pc = new ProyectoCriteria();
		//////////////////////////////////////
		// TODO faltaria añadir las especializaciones por aqui y abajo en el set
		Long idProyecto = 3L;
		String titulo = "trabajo de pruebas";
		String descripcion = "descripcion del trabajo realizado que le vamos ir poniendo aqui";
		int presupuestoMax = 12000;
		int idPoblacion = 1;
		/////////////////////////////////////
		try {

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdProyecto(idProyecto);
			results = proyectoservice.findByCriteria(pc, startIndex, pageSize);
			logger.info("Proyecto Antes de actualizar: "+results.getData());

			proyecto.setTitulo(titulo);
			proyecto.setDescripcion(descripcion);
			proyecto.setPresupuestoMax(presupuestoMax);
			proyecto.setIdPoblacion(idPoblacion);

			proyectoservice.update(proyecto);

			results = proyectoservice.findByCriteria(pc, startIndex, pageSize);
			logger.info("Proyecto Actualizado!: "+results.getData());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(proyecto,de);
		}  catch(ServiceException se){
			logger.error(proyecto,se);
		}
	}



	public void testUpdateStatus() {
		logger.trace("Begin...");
		
		ProyectoCriteria pc = new ProyectoCriteria();
		////////////////////////////////////
		Long idProyecto = 1L;
		int idEstado = 2;
		////////////////////////////////////
		try {

			int startIndex = 1;
			int pageSize = 1;

			pc.setIdProyecto(idProyecto);
			results = proyectoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Proyecto Antes de actualizar estado: "+results.getData());

			proyectoservice.updateStatus(idProyecto, idEstado);

			pc.setIdProyecto(proyecto.getIdProyecto());
			results = proyectoservice.findByCriteria(pc, startIndex, pageSize);
			logger.trace("Estado del Proyecto actualizado!: "+results.getData());

			logger.trace("End!");
		} catch(DataException de) {
			logger.error(proyecto,de);
		}  catch(ServiceException se){
			logger.error(proyecto,se);
		}
	}




	public static void main (String args[]) 
			throws ServiceException, DataException {

		ProyectoServiceTest test = new ProyectoServiceTest();

		test.testFindByCriteria();
		//		test.testCreate();
		//		test.testUpdate();
		//		test.testUpdateStatus();
	}
}