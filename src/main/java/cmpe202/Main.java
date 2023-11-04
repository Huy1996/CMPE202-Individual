package cmpe202;

import cmpe202.parser.CSVParser;
import cmpe202.parser.JSONParser;
import cmpe202.parser.XMLParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the path to the XML file as a command line argument.");
            return;
        }

        String filePath = args[0];

        FileProcessor processor = new FileProcessor();
        String ext = getFileExtension(filePath);

        FileReader reader;
        FileWriter writer;
        try {
            reader = new FileReader(filePath);
            writer = new FileWriter(filePath.replace("." + ext, "_output." + ext), false);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (ext.equals("csv")) {
            processor.setEngine(new CSVParser(reader, writer));
            processor.setDtf(DateTimeFormatter.ofPattern("yy-MMM"));
        }
        else if (ext.equals("json")) {
            processor.setEngine(new JSONParser(reader, writer));
            processor.setDtf(DateTimeFormatter.ofPattern("MM/yy"));
        }
        else if (ext.equals("xml")) {
            processor.setEngine(new XMLParser(reader, writer));
            processor.setDtf(DateTimeFormatter.ofPattern("MM/yy"));
        }
        processor.processFile();

        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }
}