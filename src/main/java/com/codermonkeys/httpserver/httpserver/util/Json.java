package com.codermonkeys.httpserver.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class Json {

    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        /**
         * FAIL_ON_UNKNOWN_PROPERTIES set to false
         * means that if any properties is missing, it won't crash
         */
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    /**
     * Method that parses Json String to Json Node
     * @param jsonSrc
     * @return
     * @throws JsonProcessingException
     */
    public static JsonNode parse(String jsonSrc) throws JsonProcessingException {
        return myObjectMapper.readTree(jsonSrc);
    }

    /**
     * Method that maps JsonNode to our Configuration POJO lass
     * @param node
     * @param clazz
     * @param <A>
     * @return
     * @throws JsonProcessingException
     */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    /**
     * Method that maps POJO class into JsonNode
     * @param obj
     * @return
     */
    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    /**
     * Auxiliary Method that generate String Representation of JsonNode
     * @param o
     * @param pretty
     * @return
     * @throws JsonProcessingException
     */
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if(pretty)
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(o);
    }
}
