package qtriptest;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility{
    String currentDir = System.getProperty("user.dir");
    String filePath = currentDir + "/src/test/resources/DatasetsforQTrip.xlsx";
    
    public static Object[][] getData(Method m) throws IOException {
        String currentDir = System.getProperty("user.dir");
        String testDataExcelPath = currentDir + "/src/test/resources/DatasetsforQTrip.xlsx";
        
        FileInputStream ExcelFile = new FileInputStream(testDataExcelPath);
        XSSFWorkbook workBook = new XSSFWorkbook(ExcelFile);
        XSSFSheet sheet = workBook.getSheet(m.getName());

        List<Object[]> testData = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Object[] rowData = new Object[row.getLastCellNum() - 1];
            for (int j = 1; j < row.getLastCellNum(); j++) {
                rowData[j - 1] = row.getCell(j).toString();
            }
            testData.add(rowData);
        }

        Object[][] data = new Object[testData.size()][];
        Iterator<Object[]> it = testData.iterator();
        int i = 0;
        while (it.hasNext()) {
            data[i++] = it.next();
        }
        ExcelFile.close();
        workBook.close();
        
        return data;
    }
}




// package qtriptest;

// import java.io.FileInputStream;
// import java.io.IOException;
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.testng.annotations.DataProvider;

// public class ExcelUtility{
//     String currentDir = System.getProperty("user.dir");
//     String filePath = currentDir + "/src/test/resources/DatasetsforQTrip.xlsx";
    
//     @DataProvider (name = "data-provider")
//     public static String[][] getData(int sheetNum) throws IOException {
//         FileInputStream file = new FileInputStream(filePath);
//         XSSFWorkbook workbook = new XSSFWorkbook(file);
//         XSSFSheet sheet = workbook.getSheetAt(sheetNum);
//         int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
//         Row firstRow = sheet.getRow(0);
//         int colCount = firstRow.getLastCellNum();
//         String[][] data = new String[rowCount - 1][colCount-1];  //[3][2]
//         for (int i = 1; i <= rowCount; i++) {
//             Row row = sheet.getRow(i);   
//             for (int j = 1; j < colCount; j++) {
//                 Cell cell = row.getCell(j);  
//                 data[i - 1][j-1] = cell.getStringCellValue();
//             }
//         }
//         workbook.close();
//         file.close();
//         return data;
//     }
// }
