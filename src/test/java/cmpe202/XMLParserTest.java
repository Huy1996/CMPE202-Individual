package cmpe202;

import cmpe202.parser.Parser;
import cmpe202.parser.XMLParser;
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

public class XMLParserTest {

    private static final String INPUT_FILE_PATH = "input_test.xml";
    private static final String OUTPUT_FILE_PATH = "output_test.xml";

    private Parser xmlParser;

    @Before
    public void setUp() {
        // Create a sample input XML file for testing
        createSampleInputFile();

        // Initialize the XMLParser with the sample input and output files
        xmlParser = new XMLParser(INPUT_FILE_PATH, OUTPUT_FILE_PATH);
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
        Map<String, String> record = xmlParser.readRecord();

        assertNotNull(record);
        assertEquals("John Doe", record.get("cardHolderName"));
        assertEquals("1234567890123456", record.get("cardNumber"));
        assertEquals("2023-12", record.get("expirationDate"));
    }

    @Test
    public void testWrite() {
        List<Map<String, String>> records = Arrays.asList(
                Map.of("cardNumber", "1111222233334444", "cardType", "Master"),
                Map.of("cardNumber", "5555666677778888", "cardType", "Discover")
        );

        xmlParser.write(records);

        // Read the content of the output file and verify it
        try {
            String content = Files.readString(Path.of(OUTPUT_FILE_PATH));

            // Assuming the output file has the expected content
            assertEquals(
                    "<CARDS>\n" +
                            "  <CARD>\n" +
                            "    <CARD_NUMBER>1111222233334444</CARD_NUMBER>\n" +
                            "    <CARD_TYPE>Master</CARD_TYPE>\n" +
                            "  </CARD>\n" +
                            "  <CARD>\n" +
                            "    <CARD_NUMBER>5555666677778888</CARD_NUMBER>\n" +
                            "    <CARD_TYPE>Discover</CARD_TYPE>\n" +
                            "  </CARD>\n" +
                            "</CARDS>\n", content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSampleInputFile() {
        // Create a sample input XML file for testing
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<CARDS>\n" +
                "  <CARD>\n" +
                "    <CARD_HOLDER_NAME>John Doe</CARD_HOLDER_NAME>\n" +
                "    <CARD_NUMBER>1234567890123456</CARD_NUMBER>\n" +
                "    <EXPIRATION_DATE>2023-12</EXPIRATION_DATE>\n" +
                "  </CARD>\n" +
                "  <CARD>\n" +
                "    <CARD_HOLDER_NAME>Jane Doe</CARD_HOLDER_NAME>\n" +
                "    <CARD_NUMBER>9876543210987654</CARD_NUMBER>\n" +
                "    <EXPIRATION_DATE>2024-01</EXPIRATION_DATE>\n" +
                "  </CARD>\n" +
                "</CARDS>";

        try {
            Files.write(Path.of(INPUT_FILE_PATH), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

