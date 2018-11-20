package com.jeovalo.almundo.callcenter.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 	Almundo Callcenter 
 * 
 *   Tipo Prioridad del Empleado, se corresponde con el TipoEmpleado
 *  
 * @author jeovalo
 *
 */
public enum TipoPrioridad {

	OPERADOR(1),   // prioridad mas alta
	SUPERVISOR(2), // prioridad media
	DIRECTOR(3);   // prioridad minima 
	
 private final Integer prioridad;
	
	/**
	 * 
	 * @param prioridad Constructor
	 */
	TipoPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}
	
	/**
	 * 
	 * @return prioridad del Empleado
	 */
	public Integer prioridad() {
		return this.prioridad;
	}

 /**
  * 
  * @return nombre del tipo del Empleado
  */
 public String nombre() {
   String nombre = this.name();
     if (nombre == null || nombre.isEmpty()) {
       return nombre;            
     } else {
       return nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase(); 
  }
 }
	
 @Override
 public String toString() {
   return String.valueOf(prioridad);
 }

 @JsonCreator
 public static TipoPrioridad fromValue(Integer p) {
   for (TipoPrioridad b : TipoPrioridad.values()) {
     if (b.prioridad.equals(p)) {
       return b;
     }
   }
   return null;
 }
	
}
