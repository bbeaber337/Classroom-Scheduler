package com.scheduler.services;

import java.io.*;
import java.sql.*;
import java.util.*;




import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import com.scheduler.services.*;
import com.scheduler.valueObjects.*;
import com.scheduler.jsp.*;
import com.scheduler.dbconnector.*;


public class excelServices extends baseJSP {

	private dbConnector conn = null;
	private MyServices ms = null;


	public excelServices(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, JspWriter stream) throws Exception {
		super(session, request, response, stream);
		ms = new MyServices();
		
	    conn = new dbConnector();
	}
	
	
	
	public void addsData(){
	
		if(request.getParameter("fileUpload") != null){
		
			ArrayList<Class1> list = new ArrayList<Class1>(); 

			Class1 classes = new Class1();

		 	try
	        	{
	            	//String file = request.getParameter("file");	
	            	Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    			String fileName = filePart.getSubmittedFileName();
	    			InputStream fileContent = filePart.getInputStream();
	    			System.out.printf("Uploaded File: %s \n",fileName);

	        	} catch(Exception e) {
	        		e.printStackTrace();
	        	}
		 
		}
		
	}
	
	public void addData(){
		
		final String FILE_PATH = "C:/Users/Brandon/Documents/Spring 2016/Capstone/Practice/excel_attempt1.xlsx";
		List<Class1> classList = new ArrayList<Class1>();
		FileInputStream fis = null;
		
		//if(request.getParameter("fileUpload") != null){
			
			try {
				 fis = new FileInputStream(FILE_PATH);
				 
				// Using XSSF for xlsx format, for xls use HSSF
				Workbook workbook = new XSSFWorkbook(fis);

				int numberOfSheets = workbook.getNumberOfSheets();
				
				//looping over each workbook sheet
				for (int i = 0; i < numberOfSheets; i++) {
				    Sheet sheet = workbook.getSheetAt(i);
				    Iterator rowIterator = sheet.iterator();
				    
				  //iterating over each row
				  while (rowIterator.hasNext()) {
					  
					Class1 c = new Class1();
					Row row = (Row) rowIterator.next();
					Iterator cellIterator = row.cellIterator();
					
					//Iterating over each cell (column wise)  in a particular row.
					while (cellIterator.hasNext()) {
						
						Cell cell = (Cell) cellIterator.next();
						//The Cell Containing String will is name.
						
						//All VALUES IN THE EXCEL DOCUMENT ARE RECOGNIZED AS A STRING!!!
						if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
						    	//if (cell.getColumnIndex() == 0) {
						    	//	c.setClassNbr(cell.getStringCellValue());
						    	//}
								if(cell.getColumnIndex() == 1){
									c.setSubject(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 2){
									c.setCatalog(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 3){
									c.setSection(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 4){
									c.setCombo(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 5){
									c.setDescr(cell.getStringCellValue());
								}


								
							    //The Cell Containing numeric value will contain marks
							     
							
						} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
							    	
							    //Cell with index 0 
							    if (cell.getColumnIndex() == 0) {
							    	//cast double to integer
							    	int j = (int) cell.getNumericCellValue();
							    	//Set the integer value
							        c.setClassNbr(j);
							    }
							    /*
							   //Cell with index 2 
							   else if (cell.getColumnIndex() == 2) {
							    	int j = (int) cell.getNumericCellValue();
							      c.setCatalog(j);
							   }
							   //Cell with index 3 
							   //else if (cell.getColumnIndex() == 3) {
							    	//int j = (int) cell.getNumericCellValue();
							       //c.setSection(j);

							 //}*/
						 }			
					}
					//End row, add class
					classList.add(c);
				  }
			}
				fis.close();
				 
			} catch(FileNotFoundException e){
				e.printStackTrace();
			} catch (IOException e) {
	            e.printStackTrace();
	        }
		
			printClasses(classList);

		//}
				
	}
		
	public void printClasses( List<Class1> classList){
		
		for(Class1 c : classList){
			System.out.printf("Here is a Class\n");
			System.out.printf("Class Nbr: %d\n",c.getClassNrb());
			System.out.printf("Subject: %s\n",c.getSubject());
			System.out.printf("Catalog: %s\n",c.getCatalog());
			System.out.printf("Section: %s\n",c.getSection());
			System.out.printf("Combo: %s\n",c.getCombo());
			System.out.printf("Class Description: %s\n\n",c.getDescr());

		}
		
	}
	
}
