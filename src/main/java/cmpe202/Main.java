package cmpe202;

import cmpe202.parser.CSVParser;
import cmpe202.parser.JSONParser;
import cmpe202.parser.XMLParser;

import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the path to the file as a command line argument.");
            return;
        }

        String input = args[0];
        String output = args[1];
        String ext = getFileExtension(input);

        FileProcessor processor = new FileProcessor();


        if ("csv".equals(ext)) {
            processor.setEngine(new CSVParser(input, output));
            processor.setDtf(DateTimeFormatter.ofPattern("yy-MMM"));
        } else if ("json".equals(ext)) {
            processor.setEngine(new JSONParser(input, output));
            processor.setDtf(DateTimeFormatter.ofPattern("MM/yy"));
        } else if ("xml".equals(ext)) {
            processor.setEngine(new XMLParser(input, output));
            processor.setDtf(DateTimeFormatter.ofPattern("MM/yy"));
        }
        processor.processFile();
    }

    public static String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }
}
