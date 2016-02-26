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
	private adminServices as = null;


	public excelServices(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, JspWriter stream) throws Exception {
		super(session, request, response, stream);
		ms = new MyServices();
		as = new adminServices(session, request, response, stream);
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
	
	public void addData() throws Exception {
		
		//Need to pull file path from uploaded file
		final String FILE_PATH = "C:/Users/Brandon/Documents/Spring 2016/Capstone/Client Documents/PKI_Rooms.xlsx";
		List<Class1> classList = new ArrayList<Class1>();
		List<Classroom> classroomList = new ArrayList<Classroom>();
		FileInputStream fis = null;
		int count = 0;

		
		if(request.getParameter("fileUpload") != null){
			
			//Clear all current class information
			ms.clearClasses();
			
			//Clear all classroom information
			ms.clearClassrooms();
			
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
					  
					count++;
					Class1 c = new Class1();
					Classroom cr = new Classroom();
					Row row = (Row) rowIterator.next();
					Iterator cellIterator = row.cellIterator();
					
					//Need to set a format in order to convert Dates into Strings
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					DateFormat tf = new SimpleDateFormat("HH:mm:ss a");

					
					//Iterating over each cell (column wise)  in a particular row.
					if(count > 1) {
					while (cellIterator.hasNext()) {
						
						Cell cell = (Cell) cellIterator.next();
						//The Cell Containing String will is name.
						
						//All VALUES IN THE EXCEL DOCUMENT ARE RECOGNIZED AS A STRING!!!
						if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
						    	//if (cell.getColumnIndex() == 0) {
						    	//	c.setClassNbr(cell.getStringCellValue());
						    	//}
								if(cell.getColumnIndex() == 1){
									c.setClassSubject(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 2){
									c.setClassCatalog(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 3){
									c.setClassSection(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 4){
									c.setClassCombination(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 5){
									c.setClassName(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 6){
									c.setClassDescription(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 7){
									c.setClassAcadGroup(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 10){
									c.setClassDays(cell.getStringCellValue());
								}

								if(cell.getColumnIndex() == 13){
									c.setClassRoom(cell.getStringCellValue());
									cr.setRoomName(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 16){
									c.setClassInstructFirst(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 15){
									c.setClassInstructLast(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 21){
									c.setClassCampus(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 22){
									c.setClassMode(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 23){
									c.setClassComponent(cell.getStringCellValue());
								}
								

								
							    //The Cell Containing numeric value will contain marks
								//cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);

							
						} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
							    	
							    //Cell with index 0 
							    if (cell.getColumnIndex() == 0) {
							    	//cast double to integer
							    	int j = (int) cell.getNumericCellValue();
							    	//Set the integer value
							        c.setClassNumber(j);
							    }
							    if (cell.getColumnIndex() == 8) {
							    	int j = (int) cell.getNumericCellValue();
							        c.setClassCapacity(j);
							    }
							    if (cell.getColumnIndex() == 9) {
							    	int j = (int) cell.getNumericCellValue();
							        c.setClassEnrolled(j);
							    }
								if(cell.getColumnIndex() == 14){
									int j = (int) cell.getNumericCellValue();
							        cr.setRoomCapacity(j);	
								}
								
							    //If this cell is a Date
							    if(DateUtil.isCellDateFormatted(cell)){
									if(cell.getColumnIndex() == 11){
										/*Converting to Strings gives weird errors
										cell.setCellType(Cell.CELL_TYPE_STRING);*/
										
										//Need to convert the Dates into Strings using the format specified above
										c.setClassTimeStart(tf.format(cell.getNumericCellValue()));
									}
									if(cell.getColumnIndex() == 12){
										c.setClassTimeEnd(tf.format(cell.getNumericCellValue()));
									}

									if(cell.getColumnIndex() == 18){
										//Need to convert the Dates into Strings using the format specified above
										c.setClassDateStart(df.format(cell.getDateCellValue()));
									}
									if(cell.getColumnIndex() == 19){
										c.setClassDateEnd(df.format(cell.getDateCellValue()));
									}
							    }							   
						 }			
					}
					cr.setRoomID(ms.addClassroom(cr));
					c.setClassID(ms.addClass(c));
					
					//Set Mon-Sat attributes
					as.setDays(c);
					
					//End row, add class and classroom
					classList.add(c);
					classroomList.add(cr);
				  }
				  }
			}

				fis.close();			 
			} catch(FileNotFoundException e){
				e.printStackTrace();
			} catch (IOException e) {
	            e.printStackTrace();
	        }		

			//Delete duplicate classrooms
			ms.deleteDuplicates();
		
		}			
	}
	

		
	public void printClasses( List<Class1> classList){
		
		for(Class1 c : classList){
			System.out.printf("Here is a Class\n");
			System.out.printf("Class Nbr: %d\n",c.getClassNumber());
			System.out.printf("Subject: %s\n",c.getClassSubject());
			System.out.printf("Catalog: %s\n",c.getClassCatalog());
			System.out.printf("Section: %s\n",c.getClassSection());
			System.out.printf("Combo: %s\n",c.getClassCombination());
			System.out.printf("Class Name: %s\n",c.getClassName());
			System.out.printf("Description: %s\n",c.getClassDescription());
			System.out.printf("Class Group: %s\n",c.getClassAcadGroup());
			System.out.printf("Capacity: %d\n",c.getClassCapacity());
			System.out.printf("Enrolled: %d\n",c.getClassEnrolled());
			System.out.printf("Days: %s\n",c.getClassDays());
			System.out.printf("Start Time: %s\n",c.getClassTimeStart());
			System.out.printf("End Time: %s\n",c.getClassTimeEnd());
			System.out.printf("Room: %s\n",c.getClassRoom());
			System.out.printf("Teacher: %s \n",c.getClassInstructFirst());
			System.out.printf("Teacher: %s \n",c.getClassInstructLast());
			System.out.printf("Start Date: %s\n",c.getClassDateStart());
			System.out.printf("End Date: %s\n",c.getClassDateEnd());
			System.out.printf("Location: %s\n",c.getClassCampus());
			System.out.printf("Mode: %s\n",c.getClassMode());
			System.out.printf("Component: %s\n\n",c.getClassComponent());
		}	
	}
	
}
