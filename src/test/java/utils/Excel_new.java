package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_new {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList al = new ArrayList();
		FileInputStream fi = new FileInputStream(
				"C:\\Users\\DELL\\eclipse-workspace\\Herokuapp_restAssured\\TestData.xlsx");
		// Locate excel
		XSSFWorkbook wb = new XSSFWorkbook(fi);

		XSSFSheet sheet = wb.getSheet("Data");

		int rowNum = sheet.getPhysicalNumberOfRows();

		int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();

		for (int i = 0; i < rowNum; i++) {
			
			
			for (int j = 0; j < cellNum; j++) {

				
				XSSFCell cell = wb.getSheet("Data").getRow(i).getCell(j);
				
				switch (cell.getCellType()) {
				case STRING:
					
					System.out.print(cell.getStringCellValue() + " ");
					break;
				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) { // Check if it's a date
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String dateValue = dateFormat.format(cell.getDateCellValue());
						
						System.out.print(dateValue + " ");
					} else {
						
						System.out.print(cell.getNumericCellValue() + " ");
					}
					break;
				case BOOLEAN:
					
					System.out.print(cell.getBooleanCellValue() + " ");
					break;
				default:
					
					System.out.print(cell.getStringCellValue() + " ");
					break;

				}
					}
				}

			
			System.out.println();
		}

	}


