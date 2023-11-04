package cmpe202.parser;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser implements Parser {
    private CSVReader reader;
    private CSVWriter writer;
    private List<Map<String, String>> records;
    private int currentIdx;

    public CSVParser(FileReader reader, FileWriter writer) {
        this.reader = new CSVReader(reader);
        this.writer = new CSVWriter(writer);
        records = new ArrayList<>();
        readFile();
        currentIdx = 0;
    }

    private void readFile() {
        try {
            String[] line;
            String[] header = reader.readNext();
            while ((line = reader.readNext()) != null) {
                line[0] = convertScientificNotation(line[0]);
                Map<String, String> record = new HashMap<>();
                for (int i = 0; i < header.length; i++) {
                    record.put(header[i], line[i]);
                }
                records.add(record);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertScientificNotation(String scientificNotation) {
        try {
            BigDecimal value = new BigDecimal(scientificNotation);
            return String.format("%.0f", value);
        } catch (Exception e) {
            return scientificNotation;
        }
    }




    @Override
    public Map<String, String> readRecord(){
        if (currentIdx < records.size()) {
            Map<String, String> record = new HashMap<>();
            record.putAll(records.get(currentIdx));
            currentIdx++;
            return record;
        }
        return null;
    }

    @Override
    public void write(List<Map<String, String>> records) {
        writer.writeNext(new String[] {"cardNumber","cardType"});
        for (Map<String, String> record: records) {
            writer.writeNext(new String[] {
                    record.get("cardNumber"),
                    record.get("cardType")
            });
        }

        try {
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
