package cmpe202;

import cmpe202.parser.CSVParser;
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

public class CSVParserTest {

    private static final String INPUT_FILE_PATH = "input_test.csv";
    private static final String OUTPUT_FILE_PATH = "output_test.csv";

    private Parser csvParser;

    @Before
    public void setUp() {
        // Create a sample input CSV file for testing
        createSampleInputFile();

        // Initialize the CSVParser with the sample input and output files
        csvParser = new CSVParser(INPUT_FILE_PATH, OUTPUT_FILE_PATH);
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
        Map<String, String> record = csvParser.readRecord();

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

        csvParser.write(records);

        // Read the content of the output file and verify it
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assuming the output file has a header line
        assertEquals("\"cardNumber\",\"cardType\"", lines.get(0));
        // Assuming the output file has two data lines with quotes
        assertEquals("\"1111222233334444\",\"Master\"", lines.get(1));
        assertEquals("\"5555666677778888\",\"Discover\"", lines.get(2));
    }

    private void createSampleInputFile() {
        // Create a sample input CSV file for testing
        List<String> lines = Arrays.asList(
                "cardNumber,cardType",
                "1234567890123456,Visa",
                "9876543210987654,Master"
        );

        try {
            Files.write(Path.of(INPUT_FILE_PATH), lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
