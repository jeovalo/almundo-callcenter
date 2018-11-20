package com.jeovalo.almundo.callcenter.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.jeovalo.almundo.callcenter.api.exception.BusyConcurrentException;
import com.jeovalo.almundo.callcenter.api.exception.EmployeeTypeNotValidException;
import com.jeovalo.almundo.callcenter.api.exception.NotFreeEmployeesException;
import com.jeovalo.almundo.callcenter.api.exception.NotSupportFormatException;
import com.jeovalo.almundo.callcenter.api.exception.ResourceNotFoundException;
import com.jeovalo.almundo.callcenter.api.message.ApiResponseMessage;
import com.jeovalo.almundo.callcenter.model.ApiRespuesta;

/**
 * Almundo CallCenter
 * 
 * @author jeovalo
 * 
 * This class is meant to be extended by all REST resource "controllers". It
 * contains exception mapping and other common REST API functionality
 */
public abstract class RestHandler implements ApplicationEventPublisherAware {

	private static final Logger LOG = LoggerFactory.getLogger(RestHandler.class);
	// Spring Event Publisher
	protected ApplicationEventPublisher eventPublisher;
	
 protected final ObjectMapper objectMapper;
 protected final HttpServletRequest request;
	
 public RestHandler(ObjectMapper objectMapper, HttpServletRequest request) {
     this.objectMapper = objectMapper;
     this.request = request;
 }
	
	/**
	 * 
	 * @param ex when free employees not exist
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NotFreeEmployeesException.class)
	public @ResponseBody ApiResponseMessage handleDataStoreException(NotFreeEmployeesException ex, WebRequest request,
			HttpServletResponse response) {
		LOG.debug("Convirtiendo Error Excepcion a la respuesta: " + ex.getMessage());
  return new ApiResponseMessage(ApiResponseMessage.ERROR, ex.getMessage()); 
	}


	/**
	 * 
	 * @param ex when Threads Pool is busy 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusyConcurrentException.class)
	public @ResponseBody ApiResponseMessage handleDataStoreException(BusyConcurrentException ex, WebRequest request,
			HttpServletResponse response) {
		LOG.debug("Convirtiendo Error Excepcion a la respuesta:" + ex.getMessage());
		return new ApiResponseMessage(ApiResponseMessage.ERROR, ex.getMessage());
	}

	/**
	 * 
	 * @param ex When Resource Rest not found 
	 * @param request 
	 * @param response
	 * @return
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public @ResponseBody ApiResponseMessage handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request,
			HttpServletResponse response) {
		LOG.info("ResourceNotFoundException handler:" + ex.getMessage());
		return new ApiResponseMessage(ApiResponseMessage.ERROR, "No se ha podido encontrar el recurso.");
	}
 
 /**
  * 
  * @param cuando el formato de la entrada(deben ser json o xml) no está soportado
  * @param request
  * @param response
  * @return
  */
 @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
 @ExceptionHandler(EmployeeTypeNotValidException.class)
 public  ResponseEntity<ApiRespuesta> handleEmployeeTypeNotValidException(EmployeeTypeNotValidException ex, WebRequest request,
   HttpServletResponse response) {
   LOG.debug("Convirtiendo Error Excepcion a la respuesta: " + ex.getMessage());
  final HttpHeaders httpHeaders= new HttpHeaders();
 
  httpHeaders.setContentType(MediaType.APPLICATION_JSON);
  ApiRespuesta respuesta = new ApiRespuesta(ApiRespuesta.ERROR, ex.getMessage());

  return new ResponseEntity<ApiRespuesta>(respuesta, httpHeaders, HttpStatus.NOT_IMPLEMENTED);
  
 }

 /**
  * 
  * @param cuando el formato de la entrada(deben ser json o xml) no está soportado
  * @param request
  * @param response
  * @return
  */
 @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
 @ExceptionHandler(NotSupportFormatException.class)
 public  ResponseEntity<ApiRespuesta> handleNotSupportFormatException(NotSupportFormatException ex, WebRequest request,
   HttpServletResponse response) {
   LOG.debug("Convirtiendo Error Excepcion a la respuesta: " + ex.getMessage());
  final HttpHeaders httpHeaders= new HttpHeaders();
 
  httpHeaders.setContentType(MediaType.APPLICATION_JSON);
  ApiRespuesta respuesta = new ApiRespuesta(ApiRespuesta.ERROR, ex.getMessage());

  return new ResponseEntity<ApiRespuesta>(respuesta, httpHeaders, HttpStatus.NOT_IMPLEMENTED);
  
 }
 
 

 /**
  * 
  * @param cuando el formato de la entrada(deben ser json o xml) no está soportado
  * @param request
  * @param response
  * @return
  */
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 @ExceptionHandler(InvalidTypeIdException.class)
 public  ResponseEntity<ApiRespuesta> handleInvalidTypeIdException(InvalidTypeIdException ex, WebRequest request, HttpServletResponse response) {
   LOG.debug("Convirtiendo Error Excepcion a la respuesta: " + ex.getMessage());
  final HttpHeaders httpHeaders= new HttpHeaders();
 
  httpHeaders.setContentType(MediaType.APPLICATION_JSON);
  ApiRespuesta respuesta = new ApiRespuesta(ApiRespuesta.ERROR, ex.getMessage());

  return new ResponseEntity<ApiRespuesta>(respuesta, httpHeaders, HttpStatus.BAD_REQUEST);
  
 } 
	/**
	 * set current EventPublisher <= Spring Context
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;
	}

	/**
	 * 
	 * @param resource
	 * @return
	 * @throws ResourceNotFoundException if resource not exist
	 */
	public static <T> T checkResourceFound(final T resource) {
		if (resource == null) {
			throw new ResourceNotFoundException("resource not found");
		}
		return resource;
	}

 /**
  * Define las cabeceras con los formatos soportados para la aplicacion 
  * @return
  * @throws NotSupportFormatException
  */
 protected HttpHeaders crearHeaders() throws NotSupportFormatException {
   String accept = request.getHeader("Accept");
   final HttpHeaders httpHeaders= new HttpHeaders();
   if (accept != null && accept.contains(MediaType.APPLICATION_XML.toString())) {
     httpHeaders.setContentType(MediaType.APPLICATION_XML);
   } else if(accept != null && accept.contains(MediaType.APPLICATION_JSON.toString())) {
     httpHeaders.setContentType(MediaType.APPLICATION_JSON);
   } else {
     throw new NotSupportFormatException("Formato NO Soportado");
   }
   return httpHeaders;
 }
	
}