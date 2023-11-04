package cmpe202.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser implements Parser{

    private final ObjectMapper objectMapper;
    private FileWriter writer;
    private Map<String, String>[] entries;;
    private int currentIdx;

    public JSONParser(FileReader reader, FileWriter writer){
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            JsonNode jsonNode = objectMapper.readTree(reader);
            if (jsonNode.isArray()) {
                this.entries = objectMapper.convertValue(jsonNode, Map[].class);
            } else {
                this.entries = new Map[]{objectMapper.convertValue(jsonNode, Map.class)};
            }
            this.currentIdx = 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.writer = writer;
    }
    @Override
    public Map<String, String> readRecord() {
        if (currentIdx < entries.length) {
            Map<String, String> currentRecord = new HashMap<>();
            currentRecord.putAll(entries[currentIdx]);
            currentIdx++;
            return currentRecord;
        }
        return null;
    }

    @Override
    public void write(List<Map<String, String>> records) {
        ArrayNode output = objectMapper.createArrayNode();
        try {
            ObjectNode objectNode = objectMapper.createObjectNode();
            for (Map<String, String> record: records) {
                for (Map.Entry<String, String> entry : record.entrySet()) {
                    objectNode.put(entry.getKey(), entry.getValue());
                }
                output.add(objectNode);
            }

            objectMapper.writeValue(writer, records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
