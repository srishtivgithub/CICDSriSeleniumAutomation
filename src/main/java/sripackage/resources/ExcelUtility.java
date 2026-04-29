package sripackage.resources;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelUtility {

    // ✅ static — no object creation needed, consistent with DataReader pattern
    // Returns List<HashMap> — SAME structure as JSON and DB data
    // so DataProviderUtility handles all three sources identically
    public static List<HashMap<String, String>> getDataFromExcel(
            String filePath, String sheetName) throws IOException {

        List<HashMap<String, String>> dataList = new ArrayList<>();

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Row 0 = header row — used as HashMap keys
        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getLastCellNum();

        // Row 1 onwards = actual data rows
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row dataRow = sheet.getRow(rowIndex);
            if (dataRow == null) continue; // skip empty rows

            HashMap<String, String> rowMap = new HashMap<>();

            for (int colIndex = 0; colIndex < colCount; colIndex++) {
                // key   = header cell value (email, password, prodName etc.)
                // value = data cell value for this row
                String key   = getCellValue(headerRow.getCell(colIndex));
                String value = getCellValue(dataRow.getCell(colIndex));
                rowMap.put(key, value);
            }
            dataList.add(rowMap);
        }

        workbook.close();
        fis.close();
        return dataList;
    }

    // handles all cell types — avoids NullPointerException and type mismatches
    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:  return cell.getStringCellValue().trim();
            case NUMERIC: 
                // avoid "1.0" instead of "1" for numeric cells
                if (DateUtil.isCellDateFormatted(cell))
                    return cell.getDateCellValue().toString();
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default:      return "";
        }
    }
}