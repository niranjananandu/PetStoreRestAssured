package api.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class TestUtilities {
	
	@DataProvider(name="userData")
	public String[][] userDataProvider() throws IOException {
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\DataFiles\\userData.xlsx");
		XSSFWorkbook workbook  = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int rows = sheet.getLastRowNum();
		int columns = sheet.getRow(0).getLastCellNum();
		String[][] arr = new String[rows][columns];
			
		for(int i =1;i<=rows;i++) {
			XSSFRow currentRow = sheet.getRow(i);
			for(int j=0;j<columns;j++) {
				try {
					arr[i-1][j] = currentRow.getCell(j).toString();
				}catch(Exception e) {
					arr[i-1][j]="";
				}
				
			}
		}
		
		workbook.close();
		file.close();
		return arr;
	}
	

	public static String getTodaysDate() {
		return (new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
	}

	public static String getSystemTime() {
		return (new SimpleDateFormat("HHmmssSSS").format(new Date()));
	}

}
