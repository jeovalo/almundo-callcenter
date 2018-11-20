package com.jeovalo.almundo.callcenter.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ConvertidorJSON {

    protected static final ThreadLocal<ObjectMapper> MAPPER_THREADLOCAL = ThreadLocal.withInitial(() -> new ObjectMapper());

    /**
     * Convert String as JSON to JsonNode.
     *
     * @param payload
     * @return
     */
    public static JsonNode getJsonFromString(String payload) {
        try {
            return MAPPER_THREADLOCAL.get().readTree(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert JsonNode Object to Map
     *
     * @param payload
     * @return
     */
    public static Map getMapFromJson(JsonNode payload) {
        try {
            return MAPPER_THREADLOCAL.get().readerFor(Map.class).readValue(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert Map Object to JsonNode.
     *
     * @param payload
     * @return
     */
    public static JsonNode getJsonFromMap(Map payload) {
        try {
            return getJsonFromString(MAPPER_THREADLOCAL.get().writeValueAsString(payload));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a Empty Object Node. i.e {}
     *
     * @return
     */
    public static ObjectNode createObjectNode() {
        return MAPPER_THREADLOCAL.get().createObjectNode();
    }

    /**
     * Create a Empty Array Json Object i.e [{},{}]
     *
     * @return
     */
    public static ArrayNode createArrayNode() {
        return MAPPER_THREADLOCAL.get().createArrayNode();
    }

    /**
     * Remove particular key elements from JsonNode.
     *
     * @param payload
     * @param key
     * @return
     */
    public static JsonNode remove(JsonNode payload, String key) {
        ((ObjectNode) payload).remove(key);
        return payload;
    }

    /**
     * {
     * "id":123,
     * "cliente":"Jeovany Lopez",
     * "wait":false,
     * "mensaje":"Consulta Billete",
     * "telefono":"55555555",
     * }
     *
     * @return
     */
    public static JsonNode createCallJson() {
        ObjectNode callNode = createObjectNode();

        callNode.put("id", 123);
        callNode.put("cliente", "Jeovany Lopez");
        callNode.put("wait", false);
        callNode.put("mensaje", "Consulta Billete");
        callNode.put("telefono", "55555555");
        return callNode;
    }

    public static void main(String[] args) {
      System.out.println(" Employee JsonNode >>" + createCallJson());
  }
    
}
