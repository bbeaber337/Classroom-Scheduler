package com.scheduler.services;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.*;


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
	
	
/*	
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
 */
	
	public void addData(){
		
		final String FILE_PATH = "C:/Users/Brandon/Documents/Spring 2016/Capstone/Client Documents/PKI_Rooms.xlsx";
		List<Class1> classList = new ArrayList<Class1>();
		FileInputStream fis = null;
		ms.clearClasses();
		
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
					
					//Need to set a format in order to convert Dates into Strings
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					DateFormat tf = new SimpleDateFormat("HH:mm:ss a");

					
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
									c.setName(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 6){
									c.setDescription(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 7){
									c.setAcadGroup(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 10){
									c.setDay(cell.getStringCellValue());
								}

								if(cell.getColumnIndex() == 13){
									c.setFacil(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 16){
									c.setFName(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 15){
									c.setLName(cell.getStringCellValue());
								}

								if(cell.getColumnIndex() == 21){
									c.setLocation(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 22){
									c.setMode(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 23){
									c.setComp(cell.getStringCellValue());
								}
								

								
							    //The Cell Containing numeric value will contain marks
								//cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);

							
						} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
							    	
							    //Cell with index 0 
							    if (cell.getColumnIndex() == 0) {
							    	//cast double to integer
							    	int j = (int) cell.getNumericCellValue();
							    	//Set the integer value
							        c.setClassNbr(j);
							    }
							    if (cell.getColumnIndex() == 8) {
							    	int j = (int) cell.getNumericCellValue();
							        c.setCapacity(j);
							    }
							    if (cell.getColumnIndex() == 9) {
							    	int j = (int) cell.getNumericCellValue();
							        c.setEnrolled(j);
							    }
							    //If this cell is a Date
							    if(DateUtil.isCellDateFormatted(cell)){
									if(cell.getColumnIndex() == 11){
										/*Converting to Strings gives weird errors
										cell.setCellType(Cell.CELL_TYPE_STRING);*/
										
										//Need to convert the Dates into Strings using the format specified above
										c.setSTime(tf.format(cell.getNumericCellValue()));
									}
									if(cell.getColumnIndex() == 12){
										c.setETime(tf.format(cell.getNumericCellValue()));
									}
									
									if(cell.getColumnIndex() == 18){
										//Need to convert the Dates into Strings using the format specified above
										c.setSDate(df.format(cell.getDateCellValue()));
									}
									if(cell.getColumnIndex() == 19){
										c.setEDate(df.format(cell.getDateCellValue()));
									}
							    }

							   
						 }			
					}
					ms.addClass(c);
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
			
			//printClasses(classList);

		//}
				
	}
		
	public void printClasses( List<Class1> classList){
		
		for(Class1 c : classList){
			System.out.printf("Here is a Class\n");
			System.out.printf("Class Nbr: %d\n",c.getClassNbr());
			System.out.printf("Subject: %s\n",c.getSubject());
			System.out.printf("Catalog: %s\n",c.getCatalog());
			System.out.printf("Section: %s\n",c.getSection());
			System.out.printf("Combo: %s\n",c.getCombo());
			System.out.printf("Class Name: %s\n",c.getName());
			System.out.printf("Description: %s\n",c.getDescription());
			System.out.printf("Class Group: %s\n",c.getAcadGroup());
			System.out.printf("Capacity: %d\n",c.getCapacity());
			System.out.printf("Enrolled: %d\n",c.getEnrolled());
			System.out.printf("Days: %s\n",c.getDay());
			System.out.printf("Start Time: %s\n",c.getSTime());
			System.out.printf("End Time: %s\n",c.getETime());
			System.out.printf("Room: %s\n",c.getFacil());
			System.out.printf("Teacher: %s \n",c.getFName());
			System.out.printf("Teacher: %s \n",c.getLName());
			System.out.printf("Start Date: %s\n",c.getSDate());
			System.out.printf("End Date: %s\n",c.getEDate());
			System.out.printf("Location: %s\n",c.getLocation());
			System.out.printf("Mode: %s\n",c.getMode());
			System.out.printf("Component: %s\n\n",c.getComp());


		}
		
	}
	
}
