package com.jeovalo.almundo.callcenter.api.controller;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeovalo.almundo.callcenter.api.CallApi;
import com.jeovalo.almundo.callcenter.model.ApiRespuesta;
import com.jeovalo.almundo.callcenter.model.Call;
import com.jeovalo.almundo.callcenter.service.Dispatcher;

import io.swagger.annotations.ApiParam;

/**
 * Controlador de Llamadas 
 * @author jeovalo
 *
 */
@RestController
public class CallApiController extends RestHandler implements CallApi {
    // Servicio Dispatcher de Llamadas(Calls)
    @Autowired
    private Dispatcher dispatcher;
    
    @org.springframework.beans.factory.annotation.Autowired
    public CallApiController(ObjectMapper objectMapper, HttpServletRequest request) {
      super(objectMapper, request);
    }

    public ResponseEntity<ApiRespuesta> addCall(@ApiParam(value = "Los objetos Call necesitan ser agregados al Callcenter" ,required=true )  @Valid @RequestBody Call call) throws InterruptedException, ExecutionException {
        this.dispatcher.dispatchCall(call.getMensaje(), call.isWait());
        ApiRespuesta respuesta = new ApiRespuesta(ApiRespuesta.OK, "Se ha agregado la Call Correctamente, está pendiente por atenderse");
        return new ResponseEntity<ApiRespuesta>(respuesta, HttpStatus.OK);
    }

}
