package utils;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import pojo.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class methods {
	public static RequestSpecification req;
	public static ResponseSpecification resp;

	public static RequestSpecification RequestSpecification() throws IOException {
	if(req==null) {
	        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	        String logFileName = "logging_" + timestamp + ".txt"; // Unique file per test run
	        PrintStream log = new PrintStream(new FileOutputStream(logFileName)); 
	        req = new RequestSpecBuilder()
	                .setBaseUri(global_Properties("base_url"))
	                .addFilter(RequestLoggingFilter.logRequestTo(log))
	                .addFilter(ResponseLoggingFilter.logResponseTo(log))
	                .setContentType(ContentType.JSON)
	                .build();
	}
	    return req;
		
	}

	public static ResponseSpecification ResponseSpecification() {

		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(anyOf(equalTo(200), equalTo(201)))
				.build();

		return resp;
	}

	public static String authBody(String Username, String password) {
		return "{\r\n" + "    \"username\" : \"" + Username + "\",\r\n" + "    \"password\" : \"" + password + "\"\r\n"
				+ "}";
	}
	// Deprecated methods
	/*
	 * public static String authBody(String Username, String password) { return
	 * "{\r\n" + "    \"username\" : \""+Username+"\",\r\n" +
	 * "    \"password\" : \""+password+"\"\r\n" + "}"; }
	 * 
	 * public static booking_pojo creatBookingDataSet() { booking_pojo bp = new
	 * booking_pojo() ; bp.setFirstname("Ram"); bp.setLastname("Ram");
	 * bp.setTotalprice(120); bp.setDepositpaid(true);
	 * bp.setAdditionalneeds("Breakfast, lunch , dinner"); bookingDatesPojo dates =
	 * new bookingDatesPojo(); dates.setCheckin("2025-02-19");
	 * dates.setCheckout("2025-02-27"); bp.setBookingdates(dates); return bp;
	 * 
	 * }
	 * 
	 * //When integrated with Excel no need to creat double methods to input data
	 * above method can be re used public static booking_pojo creatBookingDataSet2()
	 * { booking_pojo bp = new booking_pojo() ; bp.setFirstname("Ram1");
	 * bp.setLastname("Ram1"); bp.setTotalprice(1201); bp.setDepositpaid(true);
	 * bp.setAdditionalneeds("Breakfast, lunch"); bookingDatesPojo dates = new
	 * bookingDatesPojo(); dates.setCheckin("2025-02-20");
	 * dates.setCheckout("2025-02-28"); bp.setBookingdates(dates); return bp;
	 * 
	 * }
	 */

	public static ArrayList readExcelData(String Sheetname, String tescaseName) throws IOException {
		// Apache POI is needed to read data from Excel
		// poi and Poi-ooxml
		ArrayList al = new ArrayList();
		FileInputStream fi = new FileInputStream(
				"C:\\Users\\DELL\\eclipse-workspace\\Herokuapp_restAssured\\TestData.xlsx");
		// Locate excel
		XSSFWorkbook wb = new XSSFWorkbook(fi);

		XSSFSheet sheet = wb.getSheet(Sheetname);

		int rowNum = sheet.getPhysicalNumberOfRows();

		int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();

		for (int i = 0; i < rowNum; i++) {
			XSSFRow row = sheet.getRow(i);

			for (int j = 0; j < cellNum; j++) {

				if (row.getCell(0).getStringCellValue().equalsIgnoreCase(tescaseName)) {

					XSSFCell cell = row.getCell(j);
					switch (cell.getCellType()) {
					case STRING:
						al.add(cell.getStringCellValue());
						System.out.print(cell.getStringCellValue() + " ");
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) { // Check if it's a date
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String dateValue = dateFormat.format(cell.getDateCellValue());
							al.add(dateValue);
							System.out.print(dateValue + " ");
						} else {
							al.add(cell.getNumericCellValue());
							System.out.print(cell.getNumericCellValue() + " ");
						}
						break;
					case BOOLEAN:
						al.add(cell.getBooleanCellValue());
						System.out.print(cell.getBooleanCellValue() + " ");
						break;
					default:
						al.add(cell.getStringCellValue());
						System.out.print(cell.getStringCellValue() + " ");
						break;

					}
				}

			}
			System.out.println();
		}

		return al;

	}

	public static HashMap herokuapp_body(String Sheetname, String tescaseName) throws IOException {

		ArrayList al = readExcelData(Sheetname, tescaseName);
		HashMap hm = new HashMap();
		HashMap checkdates = new HashMap();
		checkdates.put("checkin", al.get(5));
		checkdates.put("checkout", al.get(6));
		hm.put("firstname", al.get(0));
		hm.put("lastname", al.get(1));
		hm.put("totalprice", ((Double) al.get(2)).intValue());
		hm.put("depositpaid", al.get(3));
		hm.put("bookingdates", checkdates);
		hm.put("additionalneeds", al.get(4));

		return hm;
	}

	public static HashMap hePatch_body(String Sheetname, String tescaseName) throws IOException {

		ArrayList al = readExcelData(Sheetname, tescaseName);
		HashMap hm = new HashMap();

		hm.put("firstname", al.get(0));
		hm.put("lastname", al.get(1));

		return hm;
	}
	
	public static String global_Properties(String key) throws IOException {
		
		Properties pd = new Properties();
		FileInputStream fi = new FileInputStream("C:\\Users\\DELL\\eclipse-workspace\\Herokuapp_restAssured\\src\\test\\java\\resources\\global.properties") ;
		pd.load(fi);
		
		return pd.getProperty(key);
	}
}
