package com.jeovalo.almundo.callcenter.api.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.stream.IntStream;

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
final public class EmpleadoControllerTest extends CallCenterAbstractControllerTest {
  protected static final Logger LOG = LoggerFactory.getLogger(EmpleadoControllerTest.class);

  /**
   * This method is executed before every @Test
   */
  @Before
  public void initTests() {
    super.initTests();
  }

  /**
   * Crea un recurso empleado de tipo Operador
   * @throws Exception
   */
  @Test
  public void shouldOKRequestCreateOperator() throws Exception {
    LOG.info(" ****************** shouldOKRequestCreateOperator  *******************");
    crearOperador(1);
  }

  /**
   * Crea un recurso empleado de tipo Supervisor
   * @throws Exception
   */
  @Test
  public void shouldOKRequestCreateSupervisor() throws Exception {
    LOG.info(" ****************** shouldOKRequestCreateSupervisor  *******************");
    crearSupervisor(2);
  }

  /**
   * Crea un recurso empleado de tipo Director
   * @throws Exception
   */
  @Test
  public void shouldOKRequestCreateDirector() throws Exception {
    LOG.info(" ****************** shouldOKRequestCreateDirector  *******************");
    crearDirector(3);
  }

  /**
   * Comprobar que cada operador atiende una llamada
   * 
   * @throws Exception
   */
  @Test
  public void shouldAllOperatorReceiveCallsOK() throws Exception {

   LOG.info(" ****************** shouldAllOperatorReceiveCallsOK  *******************");

   IntStream.rangeClosed(1, maxNumThreads).forEach(i -> crearOperador(i));
   IntStream.rangeClosed(1, maxNumThreads).forEach(i -> crearCall(i, true));
   
   Thread.sleep(maxNumThreads*1000+(maxCallTime-minCallTime)*1000);

  }
  
  /**
   * Verificar que todos los operadores atienden llamadas antes que los supervisores y directores.
   * 
   * @throws Exception
   */
  @Test
  public void shouldOperatorsFirstOK() throws Exception {

   LOG.info(" ********************** shouldOperatorsFirstOK  ************************");

   // Primeras llamadas despachan operadores por prioridad
   IntStream.rangeClosed(1, (int)(0.30*maxNumThreads)).forEach(i -> crearSupervisor(i));
   IntStream.rangeClosed(1, (int)(0.20*maxNumThreads)).forEach(i -> crearDirector(i));
   IntStream.rangeClosed(1, maxNumThreads/2).forEach(i -> crearOperador(i));
   IntStream.rangeClosed(1, maxNumThreads).forEach(i -> crearCall(i, true));

   Thread.sleep(maxNumThreads*1000+(maxCallTime-minCallTime)*1000);

  }
  

  /**
   * Verificar las llamadas en espera, para esto doblar el número de llamadas con respecto al número de empleados
   * 
   * @throws Exception
   */
  @Test
  public void shouldWaitingFreeThreads() throws Exception {

   LOG.info(" *********************** shouldWaitingFreeThreads  **********************");

   IntStream.rangeClosed(1, (int)(0.30*maxNumThreads)).forEach(i -> crearSupervisor(i));
   IntStream.rangeClosed(1, (int)(0.20*maxNumThreads)).forEach(i -> crearDirector(i));
   IntStream.rangeClosed(1, maxNumThreads/2).forEach(i -> crearOperador(i));
   
   IntStream.rangeClosed(1, maxNumThreads*2).forEach(i -> crearCall(i, true));

   Thread.sleep(maxNumThreads*1000+(maxCallTime-minCallTime)*1000*2);
  }
  

  /**
   *  Check Not waiting throwing double calls with isWait parameter to false 
   * 
   * @throws Exception
   */
  @Test
  public void shouldNotWaitingFreeThreads() throws Exception {

   LOG.info(" *********************** shouldNotWaitingFreeThreads  ********************");

   // Last ten calls not waiting and finish
   IntStream.rangeClosed(1, (int)(0.30*maxNumThreads)).forEach(i -> crearSupervisor(i));
   IntStream.rangeClosed(1, (int)(0.20*maxNumThreads)).forEach(i -> crearDirector(i));
   IntStream.rangeClosed(1, maxNumThreads/2).forEach(i -> crearOperador(i));
   
   // Simula el mismo número de llamadas que el número máximo de llamadas procesadas al mismo tiempo
   IntStream.rangeClosed(1, maxNumThreads).forEach(i -> crearCall(i, false));

   // Simula el mismo número de llamadas que el número máximo de llamadas procesadas al mismo tiempo. Algunas llamadas no se atenderán
   // Algunas peticiones tienes la causa => BusyConcurrentException 
   IntStream.rangeClosed(1, maxNumThreads).forEach(i -> {
    try {
     
      JsonNode callNode = FactoryTest.crearCallJson(i, false);
      mvc.perform(post("/almundo/v1/callcenter/call").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(callNode.toString())).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    } catch (Exception e) {
     fail("API-REST not Working");
    }
   });

   Thread.sleep(maxNumThreads*1000+(maxCallTime-minCallTime)*1000*2);
  }
  
  
}
