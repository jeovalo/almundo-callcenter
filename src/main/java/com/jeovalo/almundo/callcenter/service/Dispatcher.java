package com.jeovalo.almundo.callcenter.service;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.jeovalo.almundo.callcenter.api.exception.BusyConcurrentException;
import com.jeovalo.almundo.callcenter.api.exception.NotFreeEmployeesException;
import com.jeovalo.almundo.callcenter.model.Empleado;

/**
 *  Despacha las llamadas a los empleados. 
 * 
 * Almundo CallCenter Dispatcher Class
 * @author jeovalo
 *
 */
@Service
final public class Dispatcher {

	private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);

	@Value("${callcenter.minCallTime}")
	private Integer minCallTime;
	
	@Value("${callcenter.maxCallTime}")
	private Integer maxCallTime;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPool;

	@Autowired
	private EmpleadoServicio empleadoServicio;

	/**
	 * 
	 * @param message
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Future<String> dispatchCall(final String mensaje,final boolean wait) throws InterruptedException, ExecutionException {

		LOG.debug("Dispatching mensaje para empleado libre: "+ mensaje );
		LOG.debug("Call Waiting es: "+ wait);
		
		if (!wait && (isPoolMaxConcurrent())) {
			
			LOG.error("-----------------  LLAMADA RECHAZADA --------------------");
			LOG.error("----------------  PoolMaxConcurrent ------------------");
			LOG.error("---------------  MENSAJE: " + mensaje +   "  ----------------");
			LOG.error("------------------------------------------------------");
			
			// Throws BusyConcurrentException => Bad Request response in API Rest
			throw new BusyConcurrentException("PoolMaxConcurrent y no Espera");
		}
		
		if (!wait && notFreeEmployees()) {
			
			LOG.error("-----------------  LLAMADA RECHAZADA --------------------");
			LOG.error("----------------  Empleado No Disponibles, Intente Más Tarde ------------------");		
			LOG.error("---------------  MENSAJE: " + mensaje +   "  ----------------");
			LOG.error("------------------------------------------------------");
			
			// Throws NotFreeEmployeesException => Bad Request response in API Rest
			throw new NotFreeEmployeesException("No hay empleados libres para atender las llamadas");	
		}

		LOG.debug("Submit mensaje Task dentro del ThreadPool: "+ mensaje );
		
		// Envia la Callable Task al Ejecutor de hilos
		return threadPool.submit(new EmpleadoTask(mensaje));
	}

	/**
	 * 
	 * @return true si no hay empleados libres. De lo contrario retorna False
	 */
	private boolean notFreeEmployees() {
		return empleadoServicio.peekEmpleado() == null;
	}

	/**
	 * 
	 * @return true si el Thread Pool esta ocupado. False de otra forma
	 */
	private boolean isPoolMaxConcurrent() {
		return threadPool.getThreadPoolExecutor().getActiveCount() >= threadPool.getMaxPoolSize();
	}

	/**
	 * 	Devuelve un numero rando en un rango especifico
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private static int getRandomNumberInRange(int min, int max) {
		Random random = new Random();
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}



 
 /**
  * Callable para el pool(ThreadPoolExecutar) de tareas
  * 
  * @author jeovalo
  *
  */
 private class EmpleadoTask implements Callable<String> {

  // Mensaje que es procesado
  private String mensaje;

  public EmpleadoTask(final String mensaje) {
   this.mensaje = mensaje;
  }

  @Override
  public String call() throws Exception {
   
   // Recupera un Empleado libre, se bloque si la Cola de empleados esta vacia
   final Empleado empleado = empleadoServicio.takeEmpleado();
   // Procesa la LLamada(Call)
   Thread.sleep(getRandomNumberInRange(minCallTime, maxCallTime) * 1000);
   // Libera el empleado, Nunca se bloque porque la cola nunca se llena, es Ilimitada
   empleadoServicio.putEmpleado(empleado);
   String result = new StringBuilder().append("{  ").append("Llamada Atendida, Por un ").append(empleado.getTipoPrioridad().nombre()).append(" { nombre: ").append(empleado.getNombre())
     .append(" Mensaje:").append(mensaje).append("  }}").toString();
   LOG.info(result);
   return result;
  }
 }

}
