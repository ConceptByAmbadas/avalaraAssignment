package com.Info.ExcelReader;


import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataRepository {
	public String path;
	FileInputStream fis;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	//ReadProperty config=new ReadProperty();
	//ExcelDataConfig  readconfig=new ExcelDataConfig(config.getExeclDataPath());
	public DataRepository(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			int col_Num = 0;
			int index = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(index);
			XSSFRow row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().equals(colName)) {
					col_Num = i;
				}
			}
			row = sheet.getRow(rowNum - 1);
			XSSFCell cell = row.getCell(col_Num);
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				
				return String.valueOf(cell.getNumericCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			}

		} catch (Exception e) {
			System.out.println("Exception is"+e.getMessage());
		}

		return null;
	}
	
	public String getCellData(String sheetName, int colName, int rowNum) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			//sheet = workbook.getSheetAt(index);
			
			XSSFRow row = sheet.getRow(0);
			row = sheet.getRow(rowNum - 1);
			XSSFCell cell = row.getCell(colName);
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			}

		} catch (Exception e) {
			System.out.println("Exception is"+e.getMessage());
		}

		return null;
	}


	public int getRowCount(String sheetName) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				return 0;
			} else {
				sheet = workbook.getSheetAt(index);
				int number = sheet.getLastRowNum() + 1;
				return number;
			}
		} catch (Exception e) {
			System.out.println("Row exception is"+e.getMessage());
		}
		return 0;
	}

	public int getColumnCount(String sheetName) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				return 0;
			} else {
				sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(0);
				return row.getLastCellNum();
			}
		} catch (Exception e) {
			System.out.println("col exception is"+e.getMessage());
		}
		return 0;
	}

	public static void main(String[] args) {
		//String path = System.getProperty("E:\\StudyWorkpace\\Data_Framwork_Using_Selenium\\TestDataFile\\Datasheet.xlsx");
		String path = "E:\\GIT_Project\\com.automation.maven\\TestDataFile\\Datasheet.xlsx";
		DataRepository obj = new DataRepository(path);
		 //System.out.println(obj.getCellData("PUNE", "password", 2));

		//System.out.println(obj.getRowCount("Sheet1"));
		
		//System.out.println(obj.getColumnCount("Sheet1"));
		
		////System.out.println("Data is"+obj.getCellData("Sheet1", 0, 1));
		//System.out.println("Data is"+obj.getCellData("Enquiry_Form", 0, 1));
		//System.out.println("Data is"+obj.getCellData("PostRequriment", 0, 1));
		System.out.println("Data is"+obj.getCellData("Enquiry_Form", 1, 1));
		//System.out.println("Data is"+obj.getCellData("Sheet3", 0, 1));
		//System.out.println("Data is"+obj.getCellData("Sheet6", 0, 1));
	}

}