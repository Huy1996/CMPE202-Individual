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
    private Map<String, String>[] records;
    private int currentIdx;

    public JSONParser(String input, String output){
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

//        try {
//            JsonNode jsonNode = objectMapper.readTree(new FileReader(input));
//            if (jsonNode.isArray()) {
//                this.entries = objectMapper.convertValue(jsonNode, Map[].class);
//            } else {
//                this.entries = new Map[]{objectMapper.convertValue(jsonNode, Map.class)};
//            }
//            this.currentIdx = 0;
//            this.writer = new FileWriter(output, false);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try {
            JsonNode jsonNode = objectMapper.readTree(new FileReader(input));
            if (jsonNode.has("cards") && jsonNode.get("cards").isArray()) {
                JsonNode cards = jsonNode.get("cards");
                this.records = objectMapper.convertValue(cards, Map[].class);
            }

            this.currentIdx = 0;
            this.writer = new FileWriter(output, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Map<String, String> readRecord() {
        if (currentIdx < records.length) {
            Map<String, String> currentRecord = new HashMap<>();
            currentRecord.putAll(records[currentIdx]);
            currentIdx++;
            return currentRecord;
        }
        return null;
    }

    @Override
    public void write(List<Map<String, String>> records) {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        try {
            for (Map<String, String> record: records) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                for (Map.Entry<String, String> entry : record.entrySet()) {
                    objectNode.put(entry.getKey(), entry.getValue());
                }
                arrayNode.add(objectNode);
            }
            ObjectNode cardsNode = objectMapper.createObjectNode();
            cardsNode.set("cards", arrayNode);

            objectMapper.writeValue(writer, cardsNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
