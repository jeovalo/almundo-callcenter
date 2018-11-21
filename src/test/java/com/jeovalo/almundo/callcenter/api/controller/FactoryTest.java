package com.jeovalo.almundo.callcenter.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jeovalo.almundo.callcenter.util.ConvertidorJSON;

/**
 * Clase utilidad que sirve como fabrica para crear objetos dto para las pruebas 
 * @author jeovany
 * @email jeovalo@hotmail.com
 */
final public class FactoryTest {

  protected static JsonNode crearCallJson(int id, Boolean puedeEsperar) {
    // Call call = new Call(1l, "Jeovany López", "55555555", false, "Reserva Billete de Avión");
    ObjectNode callNode = ConvertidorJSON.createObjectNode();

    callNode.put("id", id);
    callNode.put("cliente", "Jeovany López");
    callNode.put("mensaje", "Reserva Billete de Avión");
    callNode.put("telefono", "555555");
    callNode.put("wait", puedeEsperar);
    return callNode;
 }

  protected static JsonNode crearOperadorJson(int id) {
    // Operador dto = new Operador(1l, "Jeovany López", "55555555", false, "Consulta de Ticket Aereo");
    ObjectNode callNode = ConvertidorJSON.createObjectNode();

    callNode.put("id", id);
    callNode.put("nombre", "Juan Pérez");
    callNode.put("sala", "sala de operadores");
    callNode.put("ocupado", false);
    callNode.put("tipoPrioridad", 1);
    return callNode;
 }

  protected static JsonNode crearSupervisorJson(int id) {
    // Supervisor dto = new Supervisor(1l, "Jeovany López", "55555555", false, "Consulta de Ticket Aereo");
    ObjectNode callNode = ConvertidorJSON.createObjectNode();

    callNode.put("id", id);
    callNode.put("nombre", "María Polo");
    callNode.put("sala", "sala de supervisores");
    callNode.put("ocupado", false);
    callNode.put("tipoPrioridad", 2);
    return callNode;
 }

  protected static JsonNode crearDirectorJson(int id) {
    // Director dto = new Director(1l, "Jeovany López Director", "55555555", false, "Consulta de Ticket Aereo");
    ObjectNode callNode = ConvertidorJSON.createObjectNode();

    callNode.put("id", id);
    callNode.put("nombre", "Jeovany López Director");
    callNode.put("sala", "sala de directores");
    callNode.put("ocupado", false);
    callNode.put("tipoPrioridad", 3);
    return callNode;
 }
  
}
