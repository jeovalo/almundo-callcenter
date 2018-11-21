package com.jeovalo.almundo.callcenter.api;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeovalo.almundo.callcenter.model.ApiRespuesta;
import com.jeovalo.almundo.callcenter.model.Call;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

@Api(value = "call", description = "the call API")
public interface CallApi {

    @ApiOperation(value = "Add una nueva call al Callcenter", nickname = "addCall", notes = "Una call es atendida por un Empleado del Callcenter", response = ApiRespuesta.class, authorizations = {
        @Authorization(value = "callcenter_auth", scopes = {
            @AuthorizationScope(scope = "write:calls", description = "modify calls in your account"),
            @AuthorizationScope(scope = "read:calls", description = "read your calls")
            })
    }, tags={ "call", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "call creada", response = ApiRespuesta.class),
        @ApiResponse(code = 405, message = "Entrada invalida") })
    @RequestMapping(value = "/call",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    ResponseEntity<ApiRespuesta> addCall(@ApiParam(value = "Los objetos Call necesitan ser agregados al Callcenter" ,required=true )  @Valid @RequestBody Call body) throws InterruptedException, ExecutionException;

}
