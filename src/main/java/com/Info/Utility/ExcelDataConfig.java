package com.Info.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataConfig {

	XSSFWorkbook wb;
	XSSFSheet sh1;
	String data;

	public ExcelDataConfig(String excelpath)
	{
		try {
			//System.out.println("Excel path"+excelpath);
			File src =new File(excelpath);
			FileInputStream fis= new FileInputStream(src);;

			wb=new XSSFWorkbook(fis);

		} catch (Exception ex) {

			System.out.println("problem is"+ex.getMessage());

		}
	}

	public String getData(int row,int column)
	{
		try
		{
			//System.out.println("sheet No is"+sheetnumber);
			sh1= wb.getSheetAt(0);
			data=sh1.getRow(row).getCell(column).getStringCellValue();

			
		} catch(Exception ex)
		{
			System.out.println("Exception is"+ex.getMessage());
		}
		return data;

	}
	
	
	public String getCellData( String sheetName,String colName,int rowNum) {
		try {
			int col_Num = 0;
			int index = wb.getSheetIndex(sheetName);
			sh1 = wb.getSheetAt(index);
			XSSFRow row = sh1.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().equals(colName)) {
					col_Num = i;
				}
			}
			row = sh1.getRow(rowNum - 1);
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
			e.printStackTrace();
		}

		return null;
	}

	
	
	public static void main(String[] args) {
		//String path = System.getProperty("E:\\StudyWorkpace\\Data_Framwork_Using_Selenium\\TestDataFile\\Datasheet.xlsx");
		String path = "E:\\StudyWorkpace\\Data_Framwork_Using_Selenium\\TestDataFile\\Datasheet.xlsx";
		ExcelDataConfig obj = new ExcelDataConfig(path);
		 System.out.println(obj.getCellData("Enquiry_Form","Name",2));

		/*System.out.println(obj.getRowCount("Sheet1"));
		
		System.out.println(obj.getColumnCount("Sheet1"));
		
		System.out.println(obj.getCellData("Sheet1", 1, 2));*/
	}
}
