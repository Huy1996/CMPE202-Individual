package cmpe202;

import cmpe202.parser.*;

import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the path to the XML file as a command line argument.");
            return;
        }

        String input = args[0];
        String ext = getFileExtension(input);
        String output = input.replace("." + ext, "_output." + ext);

        FileProcessor processor = new FileProcessor();


        if (ext.equals("csv")) {
            processor.setEngine(new CSVParser(input, output));
            processor.setDtf(DateTimeFormatter.ofPattern("yy-MMM"));
        }
        else if (ext.equals("xlsx")) {
            processor.setEngine(new XLSXParser(input, output));
            processor.setDtf(DateTimeFormatter.ofPattern("yy-MMM"));
        }
        else if (ext.equals("json")) {
            processor.setEngine(new JSONParser(input, output));
            processor.setDtf(DateTimeFormatter.ofPattern("MM/yy"));
        }
        else if (ext.equals("xml")) {
            processor.setEngine(new XMLParser(input, output));
            processor.setDtf(DateTimeFormatter.ofPattern("MM/yy"));
        }
        processor.processFile();
    }

    public static String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }
}