package genericUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	
	public String toReadDataFromExcel(String sheetname,int rowNo, int cellNo) throws Throwable
	{
		FileInputStream fis1 = new FileInputStream("./src/test/resources/TestScriptdata.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		//we must give sheetname,rowNo and cellNo and not hardcode it
		String data = wb.getSheet(sheetname).getRow(rowNo).getCell(cellNo).getStringCellValue();
		return data;
	}
	
	public int togetRowCount(String SheetName) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis=new FileInputStream("./TestData/TestScriptdata.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		int rowcount = wb.getSheet(SheetName).getLastRowNum();
		wb.close();
		return rowcount;
	}
}




