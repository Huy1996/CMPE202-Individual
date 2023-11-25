package cmpe202;

import cmpe202.parser.JSONParser;
import cmpe202.parser.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JSONParserTest {

    private static final String INPUT_FILE_PATH = "input_test.json";
    private static final String OUTPUT_FILE_PATH = "output_test.json";

    private Parser jsonParser;

    @Before
    public void setUp() {
        // Create a sample input JSON file for testing
        createSampleInputFile();

        // Initialize the JSONParser with the sample input and output files
        jsonParser = new JSONParser(INPUT_FILE_PATH, OUTPUT_FILE_PATH);
    }

    @After
    public void tearDown() {
        // Clean up resources and delete the sample files
        try {
            Files.deleteIfExists(Path.of(INPUT_FILE_PATH));
            Files.deleteIfExists(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadRecord() {
        Map<String, String> record = jsonParser.readRecord();

        assertNotNull(record);
        assertEquals("1234567890123456", record.get("cardNumber"));
        assertEquals("Visa", record.get("cardType"));
    }

    @Test
    public void testWrite() {
        List<Map<String, String>> records = Arrays.asList(
                Map.of("cardNumber", "1111222233334444", "cardType", "Master"),
                Map.of("cardNumber", "5555666677778888", "cardType", "Discover")
        );

        jsonParser.write(records);

        // Read the content of the output file and verify it
        try {
            String content = Files.readString(Path.of(OUTPUT_FILE_PATH));

            // Assuming the output file has the expected content
            assertEquals("{\n  \"cards\" : [ {\n    \"cardNumber\" : \"1111222233334444\",\n    \"cardType\" : \"Master\"\n  }," +
                    " {\n    \"cardNumber\" : \"5555666677778888\",\n    \"cardType\" : \"Discover\"\n  } ]\n}", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSampleInputFile() {
        // Create a sample input JSON file for testing
        String content = "{\"cards\":[{\"cardNumber\":\"1234567890123456\",\"cardType\":\"Visa\"}," +
                "{\"cardNumber\":\"9876543210987654\",\"cardType\":\"Master\"}]}";

        try {
            Files.write(Path.of(INPUT_FILE_PATH), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
