package converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> result = null;

        try {
            result = objectMapper.readValue(dbData, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
