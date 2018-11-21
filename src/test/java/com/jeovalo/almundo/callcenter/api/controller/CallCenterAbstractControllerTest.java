package com.jeovalo.almundo.callcenter.api.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Clase Abstracta para todo los test de la aplicacion Almundo CallCenter
 * @author jeovalo
 *
 */
public abstract class CallCenterAbstractControllerTest {
  protected static final Logger LOG = LoggerFactory.getLogger(CallCenterAbstractControllerTest.class);

  
  // Maximum threads in Thread Pool to attend by priority queue
  @Value("${callcenter.numThreads}")
  protected Integer maxNumThreads;
  
  @Value("${callcenter.minCallTime}")
  protected Integer minCallTime;
  
  @Value("${callcenter.maxCallTime}")
  protected Integer maxCallTime;

  // Main Controller
  @InjectMocks
  CallApiController controller;

  // Spring Boot Application Context
  @Autowired
  WebApplicationContext context;
  
  // ThreadPool in BeanContext
  @Autowired
  protected ThreadPoolTaskExecutor threadPool;

  // Mocking Spring MVC
  protected MockMvc mvc;

  /**
   * This method is executed before every @Test
   */
  @Before
  public void initTests() {

   LOG.info("Init new Test. Cleaning Employes");

   MockitoAnnotations.initMocks(this);
   mvc = MockMvcBuilders.webAppContextSetup(context).build();
   threadPool.initialize();
   deleteEmpleados();
  }
 
/** 
  * 
  * @param number of Operator ID
  */
 protected void crearOperador(final int id) {
  try {
   JsonNode callNode = FactoryTest.crearOperadorJson(id);

   mvc.perform(post("/almundo/v1/callcenter/empleado/operador").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(callNode.toString())).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated())
   .andExpect(jsonPath("$.codigo").value(4)) // 4 = ok
   .andExpect(jsonPath("$.tipo").value("ok"))
   .andExpect(jsonPath("$.mensaje").value("Se ha agregado un Agente de tipo Operador al Centro de llamadas"));
  } catch (final Exception e) {
   fail("API-REST not Working");
  }
 }

 /**
  * 
  * @param number of Supervisor ID
  */
 protected void crearSupervisor(final int id) {
  try {
    JsonNode callNode = FactoryTest.crearSupervisorJson(id);

    mvc.perform(post("/almundo/v1/callcenter/empleado/supervisor").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(callNode.toString())).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated())
    .andExpect(jsonPath("$.codigo").value(4)) // 4 = ok
    .andExpect(jsonPath("$.tipo").value("ok"))
    .andExpect(jsonPath("$.mensaje").value("Se ha agregado un Agente de tipo Supervisor al Centro de llamadas"));
  } catch (Exception e) {
   fail("API-REST not Working");
  }
 }

 /**
  * 
  * @param number of director ID
  */
 protected void crearDirector(final int id) {

  try {
    JsonNode callNode = FactoryTest.crearDirectorJson(id);

    mvc.perform(post("/almundo/v1/callcenter/empleado/director").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(callNode.toString())).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isCreated())
    .andExpect(jsonPath("$.codigo").value(4)) // 4 = ok
    .andExpect(jsonPath("$.tipo").value("ok"))
    .andExpect(jsonPath("$.mensaje").value("Se ha agregado un Agente de tipo Director al Centro de llamadas"));
  } catch (Exception e) {
   fail("API-REST not Working");
  }
 }

 /**
  * 
  * @param number of call ID
  */
 protected void crearCall(final int id, final Boolean wait) {
  try {
    JsonNode callNode = FactoryTest.crearCallJson(id, wait);
    mvc.perform(post("/almundo/v1/callcenter/call").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(callNode.toString())).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
    .andExpect(jsonPath("$.codigo").value(4)) // 4 = ok
    .andExpect(jsonPath("$.tipo").value("ok"))
    .andExpect(jsonPath("$.mensaje").value("Se ha agregado la Call Correctamente, está pendiente por atenderse"));
  } catch (Exception e) {
   fail("API-REST not Working");
  }
 } 
 
 /**
  * Elimina todos los empleados del centro de llamadas (Queue Manager)
  * 
  */
 protected void deleteEmpleados() {
  try {
   mvc.perform(delete("/almundo/v1/callcenter/empleado/empleados").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
   .andExpect(jsonPath("$.codigo").value(4)) // 4 = ok
   .andExpect(jsonPath("$.tipo").value("ok"))
   .andExpect(jsonPath("$.mensaje").value("Se han Eliminados todos los empleados que estaban atendiendo las llamadas"));
   
  } catch (Exception e) {
   fail("API-REST no está trabajando");
  }
 }
  
}
