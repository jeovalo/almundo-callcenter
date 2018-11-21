package com.jeovalo.almundo.callcenter.api;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeovalo.almundo.callcenter.model.ApiRespuesta;
import com.jeovalo.almundo.callcenter.model.Director;
import com.jeovalo.almundo.callcenter.model.Operador;
import com.jeovalo.almundo.callcenter.model.Supervisor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "empleado", description = "the empleado API")
public interface EmpleadoApi {
  
  @ApiOperation(value = "Crea un recurso empleado de tipo operador", nickname = "crearOperador", notes = "Esto solo lo puede hacer un usuario que ha iniciado sesión", response = ApiRespuesta.class, tags={ "empleado", })
  @ApiResponses(value = { 
      @ApiResponse(code = 201, message = "operador creado", response = ApiRespuesta.class),
      @ApiResponse(code = 401, message = "No tiene Autorización", response = ApiRespuesta.class),
      @ApiResponse(code = 403, message = "Operación No Permitida", response = ApiRespuesta.class),
      @ApiResponse(code = 404, message = "Operación No Encontrada", response = ApiRespuesta.class),
      @ApiResponse(code = 405, message = "Entrada invalida", response = ApiRespuesta.class) })
  @RequestMapping(value = "/empleado/operador",
      produces = { "application/json", "application/xml" }, 
      consumes = { "application/json", "application/xml" },
      method = RequestMethod.POST)
  ResponseEntity<ApiRespuesta> crearOperador(@ApiParam(value = "Crea un empleado operador" ,required=true )  @Valid @RequestBody Operador body) throws InterruptedException;

  @ApiOperation(value = "Crea un recurso empleado de tipo supervisor", nickname = "crearSupervisor", notes = "Esto solo lo puede hacer un usuario que ha iniciado sesión", response = ApiRespuesta.class, tags={ "empleado", })
  @ApiResponses(value = { 
      @ApiResponse(code = 201, message = "Supervisor creado", response = ApiRespuesta.class),
      @ApiResponse(code = 401, message = "No tiene Autorización", response = ApiRespuesta.class),
      @ApiResponse(code = 403, message = "Operación No Permitida", response = ApiRespuesta.class),
      @ApiResponse(code = 404, message = "Operación No Encontrada", response = ApiRespuesta.class),
      @ApiResponse(code = 405, message = "Entrada invalida", response = ApiRespuesta.class) })
  @RequestMapping(value = "/empleado/supervisor",
      produces = { "application/json", "application/xml" }, 
      consumes = { "application/json", "application/xml" },
      method = RequestMethod.POST)
  ResponseEntity<ApiRespuesta> crearSupervisor(@ApiParam(value = "Crea un empleado supervisor" ,required=true )  @Valid @RequestBody Supervisor body) throws InterruptedException;

    @ApiOperation(value = "Crea un recurso empleado de tipo director", nickname = "crearDirector", notes = "Esto solo lo puede hacer un usuario que ha iniciado sesión", response = ApiRespuesta.class, tags={ "empleado", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "director creado", response = ApiRespuesta.class),
        @ApiResponse(code = 401, message = "No tiene Autorización", response = ApiRespuesta.class),
        @ApiResponse(code = 403, message = "Operación No Permitida", response = ApiRespuesta.class),
        @ApiResponse(code = 404, message = "Operación No Encontrada", response = ApiRespuesta.class),
        @ApiResponse(code = 405, message = "Entrada invalida", response = ApiRespuesta.class) })
    @RequestMapping(value = "/empleado/director", produces = { "application/json", "application/xml" }, consumes = { "application/json", "application/xml" }, method = RequestMethod.POST)
    ResponseEntity<ApiRespuesta> crearDirector(@ApiParam(value = "Crea un empleado director" ,required=true )  @Valid @RequestBody Director body) throws InterruptedException;



    @ApiOperation(value = "Limpia el Pool de empleados que están atendiendo las llamadas", nickname = "deleteEmpleados", notes = "Esto solo lo puede hacer un usuario que ha iniciado sesión", response = ApiRespuesta.class, tags={ "empleado", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Pool está vacio", response = ApiRespuesta.class),
        @ApiResponse(code = 204, message = "Sin contenido", response = ApiRespuesta.class),
        @ApiResponse(code = 401, message = "No tiene Autorización", response = ApiRespuesta.class),
        @ApiResponse(code = 403, message = "Operación No Permitida", response = ApiRespuesta.class),
        @ApiResponse(code = 404, message = "Operación No Encontrada", response = ApiRespuesta.class) })
    @RequestMapping(value = "/empleado/empleados",
        method = RequestMethod.DELETE)
    ResponseEntity<ApiRespuesta> deleteEmpleados() throws InterruptedException, ExecutionException;


    @ApiOperation(value = "Autenticacion del empleado en el sistema", nickname = "loginEmpleado", notes = "", response = String.class, tags={ "empleado", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "operacion existosa", response = String.class),
        @ApiResponse(code = 400, message = "Nombre de usuario/clave no válidos") })
    @RequestMapping(value = "/empleado/login",
        produces = { "application/json", "application/xml" }, 
        method = RequestMethod.GET)
    ResponseEntity<String> loginEmpleado(@NotNull @ApiParam(value = "El nombre de usuario para iniciar sesion", required = true) @Valid @RequestParam(value = "username", required = true) String username,@NotNull @ApiParam(value = "La clave para iniciar sesion en texto claro", required = true) @Valid @RequestParam(value = "password", required = true) String password);


    @ApiOperation(value = "Logout del empleado actualmente en la session", nickname = "logoutEmpleado", notes = "", tags={ "empleado", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "operacion existosa") })
    @RequestMapping(value = "/empleado/logout",
        produces = { "application/json", "application/xml" }, 
        method = RequestMethod.GET)
    ResponseEntity<Void> logoutEmpleado();

}
