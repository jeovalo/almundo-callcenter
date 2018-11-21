package com.jeovalo.almundo.callcenter.api.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeovalo.almundo.callcenter.api.EmpleadoApi;
import com.jeovalo.almundo.callcenter.api.exception.EmployeeTypeNotValidException;
import com.jeovalo.almundo.callcenter.model.ApiRespuesta;
import com.jeovalo.almundo.callcenter.model.Director;
import com.jeovalo.almundo.callcenter.model.Empleado;
import com.jeovalo.almundo.callcenter.model.Operador;
import com.jeovalo.almundo.callcenter.model.Supervisor;
import com.jeovalo.almundo.callcenter.model.TipoPrioridad;
import com.jeovalo.almundo.callcenter.service.EmpleadoServicio;

import io.swagger.annotations.ApiParam;

/**
 * Controlador de Llamadas 
 * @author jeovalo
 *
 */
@RestController
public class EmpleadoApiController extends RestHandler implements EmpleadoApi {

  private static final Logger log = LoggerFactory.getLogger(EmpleadoApiController.class);

  // Servicio Empleado
  @Autowired
  private EmpleadoServicio empleadoServicio;

  @org.springframework.beans.factory.annotation.Autowired
  public EmpleadoApiController(ObjectMapper objectMapper, HttpServletRequest request) {
    super(objectMapper, request);
  }

  private ResponseEntity<ApiRespuesta> crearEmpleado(Empleado empleado)
      throws EmployeeTypeNotValidException, InterruptedException {
    TipoPrioridad tipoEmpleado = empleado.getTipoPrioridad();
    switch (tipoEmpleado) {
      case OPERADOR:
      case SUPERVISOR:
      case DIRECTOR: {
        this.empleadoServicio.putEmpleado(empleado);
        ApiRespuesta respuesta = new ApiRespuesta(ApiRespuesta.OK, "Se ha agregado un Agente de tipo " + tipoEmpleado.nombre() + " al Centro de llamadas");
        return new ResponseEntity<ApiRespuesta>(respuesta, HttpStatus.CREATED);
      }
      default: {
        throw new EmployeeTypeNotValidException("El Tipo de Empleado no es Valido. Debe ser: Operador, Supervisor o Director");
      }
    }
  }

  public ResponseEntity<ApiRespuesta> crearOperador(
      @ApiParam(value = "Crea un empleado operador", required = true) @Valid @RequestBody Operador operador)
      throws InterruptedException {
    return this.crearEmpleado(operador);
  }

  public ResponseEntity<ApiRespuesta> crearDirector(
      @ApiParam(value = "Crea un empleado director", required = true) @Valid @RequestBody Director director)
      throws InterruptedException {
    return this.crearEmpleado(director);
  }

  public ResponseEntity<ApiRespuesta> crearSupervisor(
      @ApiParam(value = "Crea un empleado supervisor", required = true) @Valid @RequestBody Supervisor supervisor)
      throws InterruptedException {
    return this.crearEmpleado(supervisor);
  }

  public ResponseEntity<ApiRespuesta> deleteEmpleados() throws InterruptedException, ExecutionException {
    this.empleadoServicio.cleanEmpleados();
    ApiRespuesta respuesta = new ApiRespuesta(ApiRespuesta.OK, "Se han Eliminados todos los empleados que estaban atendiendo las llamadas");
    return new ResponseEntity<ApiRespuesta>(respuesta, HttpStatus.OK);
  }

  public ResponseEntity<String> loginEmpleado(
      @NotNull @ApiParam(value = "El nombre de usuario para iniciar sesion", required = true) @Valid @RequestParam(value = "username", required = true) String username,
      @NotNull @ApiParam(value = "La clave para iniciar sesion en texto claro", required = true) @Valid @RequestParam(value = "password", required = true) String password) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/xml")) {
      try {
        return new ResponseEntity<String>(objectMapper.readValue("aeiou", String.class), HttpStatus.NOT_IMPLEMENTED);
      } catch (IOException e) {
        log.error("Couldn't serialize response for content type application/xml", e);
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    if (accept != null && accept.contains("application/json")) {
      try {
        return new ResponseEntity<String>(objectMapper.readValue("\"\"", String.class), HttpStatus.NOT_IMPLEMENTED);
      } catch (IOException e) {
        log.error("Couldn't serialize response for content type application/json", e);
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> logoutEmpleado() {
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }

}
