package com.jeovalo.almundo.callcenter.service;

import java.util.concurrent.PriorityBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jeovalo.almundo.callcenter.model.Empleado;


/**
 * Almundo Callcenter PriorityBlockingQueue Empleado Manager Service
 * @author jeovalo
 *
 */
@Service
final public class EmpleadoServicio {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmpleadoServicio.class);
	
	// Cola con Prioridad. 
	private PriorityBlockingQueue<Empleado> blockingQueue = new PriorityBlockingQueue<Empleado>();
	
	/**
	 * Inserta el Empleado especificado a la cola de prioridad. Como la cola con prioridad es Ilimitada nunca se bloquea
	 * 
	 * @param empleado
	 * @throws InterruptedException
	 */
	public void putEmpleado(final Empleado empleado) throws InterruptedException {
		LOG.debug("Insertado un Empleado en la cola: " + empleado.toString());
		blockingQueue.put(empleado);
	}
 
 /**
  *  Recupera y elimina el primer Empleado(el de mayor prioridad) de la cola, esperando si es necesario hasta que un elemento esté disponible.
  *  Proporciona null si la cola está vacia. 
  *  
  * @return 
  * @throws InterruptedException
  */
 public Empleado takeEmpleado() throws InterruptedException {
  final Empleado empleado = blockingQueue.take();
  LOG.debug("Recuperado y eliminado el primer elemento Empleado de la cola:" + empleado.toString());
  return empleado;
 }
	
	/**
	 * Recupera sin eliminar el primer elemento de la cola de prioridad. Proporciona null si la cola está vacia.
	 * 
	 * @return
	 */
	public Empleado peekEmpleado() {
		LOG.debug("Recuperado el primer elemento Empleado de la Cola");
		return blockingQueue.peek();		
	}
	
	/**
	 * Elimina atómicamente todos los elementos de esta cola. La cola estará vacía después del retorno del metodo.
	 */
	public void cleanEmpleados() {
		LOG.debug("Limpia la Cola");
		this.blockingQueue.clear();
	}
	
}
