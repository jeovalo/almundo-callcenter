package com.jeovalo.almundo.callcenter.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.jeovalo.almundo.callcenter.CallCenterApplication;

/**
 * Clase test para el controlador de Llamadas de la aplicacion Almundo CallCenter
 * @author jeovalo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CallCenterApplication.class)
@ActiveProfiles("test")
final public class CallControllerTest extends CallCenterAbstractControllerTest {
  protected static final Logger LOG = LoggerFactory.getLogger(CallControllerTest.class);

  /**
   * This method is executed before every @Test
   */
  @Before
  public void initTests() {
    super.initTests();
  }
  
	/**
	 * No hay empleados libres para atender las llamadas
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldBadRequestBeacauseNotEmployees() throws Exception {
   LOG.info(" ****************** shouldBadRequestBeacauseNotEmployees  *******************");
	  LOG.info(" ************ No hay empleados libres para atender las llamadas  *******************");
   JsonNode callNode = FactoryTest.crearCallJson(123, false);

   mvc.perform(post("/call").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(callNode.toString())).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isBadRequest())
   .andExpect(jsonPath("$.code").value(1)) // 1 = error
   .andExpect(jsonPath("$.type").value("error"))
   .andExpect(jsonPath("$.message").value("No hay empleados libres para atender las llamadas"));
	}

 /**
  * Las llamada debe ser agregada porque el cliente puede esperar
  * No hay empleados libres para atender las llamadas, pero el cliente puede esperar
  * 
  * @throws Exception
  */
 @Test
 public void shouldOKRequestBeacauseClientCanWait() throws Exception {
   LOG.info(" ****************** shouldOKRequestBeacauseClientCanWait  *******************");
   LOG.info(" ************ No hay empleados libres para atender las llamadas, pero el cliente puede esperar  *******************");
   JsonNode callNode = FactoryTest.crearCallJson(123, true);

   mvc.perform(post("/call").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(callNode.toString())).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
   .andExpect(jsonPath("$.codigo").value(4)) // 4 = ok
   .andExpect(jsonPath("$.tipo").value("ok"))
   .andExpect(jsonPath("$.mensaje").value("Se ha agregado la Call Correctamente, está pendiente por atenderse"));
 }
	 
}
