package deltajava.objectstore.delta.storage.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import deltajava.objectstore.delta.storage.actions.Action;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for JSON serialization and deserialization.
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    
    static {
        // Configure the mapper for better serialization/deserialization
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // Disable indentation to avoid issues with line parsing
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    /**
     * Converts an object to a JSON string.
     *
     * @param object the object to convert
     * @return the JSON string
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing object to JSON", e);
        }
    }
    
    /**
     * Converts a JSON string to an Action.
     *
     * @param json the JSON string
     * @return the Action
     */
    public static Action fromJson(String json) {
        try {
            if (json == null || json.trim().isEmpty()) {
                throw new IllegalArgumentException("JSON string cannot be null or empty");
            }
            
            // Print the JSON for debugging
            System.out.println("Parsing JSON: " + json);
            
            return mapper.readValue(json, Action.class);
        } catch (IOException e) {
            System.err.println("Failed to parse JSON: " + json);
            e.printStackTrace();
            throw new RuntimeException("Error deserializing JSON to Action: " + e.getMessage(), e);
        }
    }
    
    /**
     * Converts a JSON string to a List of Action objects.
     *
     * @param json the JSON string containing an array of actions
     * @return the List of Action objects
     */
    public static List<Action> jsonToActions(String json) {
        try {
            if (json == null || json.trim().isEmpty()) {
                return Collections.emptyList();
            }
            
            return mapper.readValue(json, new TypeReference<List<Action>>(){});
        } catch (IOException e) {
            System.err.println("Failed to parse JSON to Action list: " + json);
            e.printStackTrace();
            throw new RuntimeException("Error deserializing JSON to Action list: " + e.getMessage(), e);
        }
    }
    
    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param json the JSON string
     * @param clazz the class to convert to
     * @param <T> the type of the class
     * @return the object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            if (json == null || json.trim().isEmpty()) {
                throw new IllegalArgumentException("JSON string cannot be null or empty");
            }
            
            // Print the JSON for debugging
            System.out.println("Parsing JSON for " + clazz.getSimpleName() + ": " + json);
            
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            System.err.println("Failed to parse JSON for " + clazz.getSimpleName() + ": " + json);
            e.printStackTrace();
            throw new RuntimeException("Error deserializing JSON to " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
} 