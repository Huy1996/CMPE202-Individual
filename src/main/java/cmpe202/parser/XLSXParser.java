package cmpe202.parser;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XLSXParser implements Parser{
    private String output;
    private List<Map<String, String>> records;
    private int currentIdx;

    public XLSXParser(String input, String output){
        this.output = output;
        records = new ArrayList<>();
        currentIdx = 0;
        try {
            FileInputStream file= new FileInputStream(input);
            Workbook workbook = new XSSFWorkbook(file);
            readFile(workbook);
            file.close();
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFile(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Row header = sheet.getRow(0);
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Map<String, String> record = new HashMap<>();
            for (int j = 0; j < header.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                record.put(header.getCell(j).getStringCellValue(), cell.getStringCellValue());
            }
            records.add(record);
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

    public void write(List<Map<String, String>> records) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < records.size() + 1; i++) {
            Row row = sheet.createRow(i);
            Cell cell1 = row.createCell(0);
            Cell cell2 = row.createCell(1);

            if (i == 0) {
                cell1.setCellValue("cardNumber");
                cell2.setCellValue("cardType");
            } else {
                cell1.setCellValue(records.get(i - 1).get("cardNumber"));
                cell2.setCellValue(records.get(i - 1).get("cardType"));
            }
        }
        try (FileOutputStream fileOut = new FileOutputStream(output, false)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the workbook
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
