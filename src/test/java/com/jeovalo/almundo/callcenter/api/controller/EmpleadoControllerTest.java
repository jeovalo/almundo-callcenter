package com.jeovalo.almundo.callcenter.api.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jeovalo.almundo.callcenter.CallCenterApplication;
import com.jeovalo.almundo.callcenter.model.Director;
import com.jeovalo.almundo.callcenter.model.Operador;
import com.jeovalo.almundo.callcenter.model.Supervisor;
import com.jeovalo.almundo.callcenter.util.ConvertidorJSON;

/**
 * Almundo CallCenter Application Test
 * @author jeovalo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CallCenterApplication.class)
@ActiveProfiles("test")
final public class EmpleadoControllerTest {
  protected static final Logger LOG = LoggerFactory.getLogger(EmpleadoControllerTest.class);

  
  // Maximum threads in Thread Pool to attend by priority queue
  @Value("${callcenter.numThreads}")
  private Integer maxNumThreads;
  
  @Value("${callcenter.minCallTime}")
  private Integer minCallTime;
  
  @Value("${callcenter.maxCallTime}")
  private Integer maxCallTime;

  // Main Controller
  @InjectMocks
  EmpleadoApiController controller;

  // Spring Boot Application Context
  @Autowired
  WebApplicationContext context;
  
  // ThreadPool in BeanContext
  @Autowired
  private ThreadPoolTaskExecutor threadPool;

  // Mocking Spring MVC
  private MockMvc mvc;

  /**
   * This method is executed before every @Test
   */
  @Before
  public void initTests() {

   LOG.info("Init new Test. Cleaning Employes");

   MockitoAnnotations.initMocks(this);
   mvc = MockMvcBuilders.webAppContextSetup(context).build();
   threadPool.initialize();
   // Delete employees by EndPoint
   deleteEmployees();
  }
  

  /**
   * Check every operator attend every call before the supervisors and directors
   * 
   * @throws Exception
   */
  @Test
  public void shouldOperatorsFirstOK() throws Exception {

   LOG.info(" ********************** shouldOperatorsFirstOK  ************************");

   // First calls dispatch operators by priority
   IntStream.rangeClosed(1, (int)(0.30*maxNumThreads)).forEach(i -> createSupervisor(i));
   IntStream.rangeClosed(1, (int)(0.20*maxNumThreads)).forEach(i -> createDirector(i));
   IntStream.rangeClosed(1, maxNumThreads/2).forEach(i -> createOperador(i));
   IntStream.rangeClosed(1, maxNumThreads).forEach(i -> createCall(i, "true"));

   Thread.sleep(maxNumThreads*1000+(maxCallTime-minCallTime)*1000);

  } 
 private JsonNode createCallJson() {
   // Call call = new Call(1l, "Jeovany López", "55555555", false, "Consulta de Ticket Aereo");
   ObjectNode callNode = ConvertidorJSON.createObjectNode();

   callNode.put("id", 123);
   callNode.put("cliente", "Jeovany Lopez");
   callNode.put("wait", false);
   callNode.put("mensaje", "Consulta Billete");
   callNode.put("telefono", "55555555");
   return callNode;
}
  
  /** 
  * 
  * @param number of Operator ID
  */
 private void createOperador(final int number) {
  try {
   mvc.perform(post("/almundo/v1/callcenter/empleado/operador").content(toJson(new Operador("OPERATOR-" + number)))
     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
     .andExpect(status().isNotFound());
  } catch (final Exception e) {
   fail("API-REST not Working");
  }
 }

 /**
  * 
  * @param number of Supervisor ID
  */
 private void createSupervisor(final int number) {

  try {
   mvc.perform(
     post("/almundo/v1/callcenter/empleado/operador").content(toJson(new Supervisor("SUPERVISOR-" + number)))
       .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
     .andExpect(status().isNotFound());
  } catch (Exception e) {
   fail("API-REST not Working");
  }
 }

 /**
  * 
  * @param number of director ID
  */
 private void createDirector(final int number) {

  try {
   mvc.perform(post("/almundo/v1/callcenter/empleado/operador").content(toJson(new Director("DIRECTOR-" + number)))
     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
     .andExpect(status().isNotFound());
  } catch (Exception e) {
   fail("API-REST not Working");
  }
 }

 /**
  * 
  * @param number of call ID
  */
 private void createCall(final int number, final String wait) {
  try {
   mvc.perform(post("/almundo/v1/callcenter/call").param("message", "CALLING-" + number).param("isWait", wait)
     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
     .andExpect(status().isNotFound());
  } catch (Exception e) {
   fail("API-REST not Working");
  }
 } 
 
 /**
  * Delete all employees in Queue Manager
  * 
  */
 private void deleteEmployees() {
  try {
   mvc.perform(delete("/almundo/v1/callcenter/empleado/empleados")
     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
     .andExpect(status().isNotFound());
  } catch (Exception e) {
   fail("API-REST not Working");
  }
 }
 

 /**
  *   Get byte array Json serializable Object
  * 
  * @param serializable
  * @return
  * @throws Exception
  */
 private byte[] toJson(Object serializable) throws Exception {
  ObjectMapper map = new ObjectMapper();
  byte[] byteArray = map.writeValueAsString(serializable).getBytes();
  LOG.debug("JSON: " + new String(byteArray));
  return byteArray;
 }  
 
 
}