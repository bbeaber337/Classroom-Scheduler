package com.scheduler.services;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
import javax.servlet.annotation.MultipartConfig;
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

@MultipartConfig
public class excelServices extends baseJSP {

	private dbConnector conn = null;
	private MyServices ms = null;
	private adminServices as = null;


	public excelServices(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, JspWriter stream) throws Exception {
		super(session, request, response, stream);
		ms = new MyServices(session, request, response, stream);
		as = new adminServices(session, request, response, stream);
	    conn = new dbConnector();
	}
	
	
	
	public void exportData() throws Exception {
		
		if(request.getParameter("export") != null){
			
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet classSheet = wb.createSheet("Classes");
		CreationHelper createHelper = wb.getCreationHelper();
		
		//Get Data
		List<Class1> c = new ArrayList<Class1>();
		//List<Classroom> cr = new ArrayList<Classroom>();
		c = ms.getClasses();
		//cr = ms.getClassrooms(); 
		
		//Set Heading
		Row headerRow = classSheet.createRow(0);
		headerRow.createCell(0).setCellValue("Class Nbr");
		headerRow.createCell(1).setCellValue("Subject");
		headerRow.createCell(2).setCellValue("Catalog");
		headerRow.createCell(3).setCellValue("Section");
		headerRow.createCell(4).setCellValue("Comb Sect");
		headerRow.createCell(5).setCellValue("Descr");
		headerRow.createCell(6).setCellValue("Descr");
		headerRow.createCell(7).setCellValue("Acad Group");
		headerRow.createCell(8).setCellValue("Cap Enrl");
		headerRow.createCell(9).setCellValue("Tot Enrl");
		headerRow.createCell(10).setCellValue("Pat");
		headerRow.createCell(11).setCellValue("Mtg Start");
		headerRow.createCell(12).setCellValue("Mtg End");
		headerRow.createCell(13).setCellValue("Facil ID");
		headerRow.createCell(14).setCellValue("Capacity");
		headerRow.createCell(15).setCellValue("Last");
		headerRow.createCell(16).setCellValue("First Name");
		headerRow.createCell(17).setCellValue("Role");
		headerRow.createCell(18).setCellValue("Start Date");
		headerRow.createCell(19).setCellValue("End Date");
		headerRow.createCell(20).setCellValue("Session");
		headerRow.createCell(21).setCellValue("Location");
		headerRow.createCell(22).setCellValue("Mode");
		headerRow.createCell(23).setCellValue("CrsAtr Val");
		headerRow.createCell(24).setCellValue("Component");
		
		//Set Heading Style
		for(int i = 0; i < 25; i++){
			CellStyle headingStyle = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBold(true);
			font.setFontName("Arial Unicode MS");
			font.setFontHeightInPoints((short) 10);
			headingStyle.setFont(font);	
			headingStyle.setBorderBottom(XSSFCellStyle.BORDER_DOUBLE);
			headingStyle.setBorderTop(XSSFCellStyle.BORDER_DOUBLE);
			headingStyle.setBorderRight(XSSFCellStyle.BORDER_DOUBLE);
			headingStyle.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE);
			//headingStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			//headingStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			//headingStyle.setFillBackgroundColor(HSSFColor.GREY_80_PERCENT.index);
			headerRow.getCell(i).setCellStyle(headingStyle);
		}
		
		//Cell sectionHeaderCell = headerRow.createCell(3);
		
		//Row counter
		int row = 1;

		SimpleDateFormat sdfIn = new SimpleDateFormat("h:mm:ss a");
		SimpleDateFormat sdfDate = new SimpleDateFormat("M/d/yyyy");
		
		for(Class1 item : c){
		
			int cap = ms.getClassroomCapacity(item.getClassRoom());
			Date startTime = sdfIn.parse(item.getClassTimeStart());
			Date endTime = sdfIn.parse(item.getClassTimeEnd());
			Date startDate = sdfDate.parse(item.getClassDateStart());
			Date endDate = sdfDate.parse(item.getClassDateEnd());
			
			CellStyle time = wb.createCellStyle();
			time.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss AM/PM"));
			CellStyle date = wb.createCellStyle();
			date.setDataFormat(createHelper.createDataFormat().getFormat("M/d/yyyy"));
			
			Row dataRow = classSheet.createRow(row);
			
			dataRow.createCell(0).setCellValue(item.getClassNumber());
			dataRow.createCell(1).setCellValue(item.getClassSubject());
			dataRow.createCell(2).setCellValue(item.getClassCatalog());
			dataRow.createCell(3).setCellValue(item.getClassSection());
			dataRow.createCell(4).setCellValue(item.getClassCombination());
			dataRow.createCell(5).setCellValue(item.getClassName());
			dataRow.createCell(6).setCellValue(item.getClassDescription());
			dataRow.createCell(7).setCellValue(item.getClassAcadGroup());
			dataRow.createCell(8).setCellValue(item.getClassEnrolled());
			dataRow.createCell(9).setCellValue(item.getClassCapacity());
			dataRow.createCell(10).setCellValue(item.getClassDays());
			
			Cell cellTStart = dataRow.createCell(11);
			cellTStart.setCellValue(startTime);
			cellTStart.setCellStyle(time);
			
			Cell cellTEnd = dataRow.createCell(12);
			cellTEnd.setCellValue(endTime);
			cellTEnd.setCellStyle(time);
			
			cellTEnd.setCellStyle(time);
			dataRow.createCell(13).setCellValue(item.getClassRoom());
			dataRow.createCell(14).setCellValue(cap);
			dataRow.createCell(15).setCellValue(item.getClassInstructLast());
			dataRow.createCell(16).setCellValue(item.getClassInstructFirst());
			dataRow.createCell(17).setCellValue(item.getClassRole());
			
			Cell cellDStart = dataRow.createCell(18);
			cellDStart.setCellValue(startDate);
			cellDStart.setCellStyle(date);
			
			Cell cellDEnd = dataRow.createCell(19);
			cellDEnd.setCellValue(endDate);
			cellDEnd.setCellStyle(date);
		
			
			dataRow.createCell(20).setCellValue(item.getClassSession());
			dataRow.createCell(21).setCellValue(item.getClassCampus());
			dataRow.createCell(22).setCellValue(item.getClassMode());
			dataRow.createCell(23).setCellValue(item.getClassCrsAttrVal());
			dataRow.createCell(24).setCellValue(item.getClassComponent());
			
		/*
			Cell dataClassNbrCell = dataRow.createCell(0);
			dataClassNbrCell.setCellValue(item.getClassNumber());
			
			Cell dataSubjectCell = dataRow.createCell(1);
			dataSubjectCell.setCellValue(item.getClassSubject());
			*/
			row = row+1;
		} 
		
		FileOutputStream fOut = new FileOutputStream(new File("C:\\myDownloads\\PKI.xlsx"));
		wb.write(fOut);
		fOut.close();
		wb.close();
		}
	}
	
	
	
	public void addData() throws Exception {
		
		//Need to pull file path from uploaded file
		List<Class1> classList = new ArrayList<Class1>();
		List<Classroom> classroomList = new ArrayList<Classroom>();
		InputStream fis = null;
		int count = 0;
		
		/*
		// testing that parameters are coming through
		if(this.request.getMethod().equalsIgnoreCase("post")){
			System.out.println("Printing ExcelServices request parameters:");
			Enumeration<String> parameterNames = this.request.getParameterNames();
			while (parameterNames.hasMoreElements()){
				String paramName = parameterNames.nextElement();
				System.out.print(paramName);
				System.out.print("\n");
				
				String[] paramValues = this.request.getParameterValues(paramName);
				for (int i = 0; i < paramValues.length; i++) {
					String paramValue = paramValues[i];
					System.out.print("\t" + paramValue);
					System.out.print("\n");
				}
			}
			System.out.println("Done printing Parameters");
		}

		if(!ServletFileUpload.isMultipartContent(this.request)){
			System.out.println("Is not a multipart request");
			return;
		}
		*/
		
		if(this.request.getMethod().equalsIgnoreCase("post")){
			
			//Clear all current class information
			ms.clearClasses();
			
			//Clear all classroom information
			ms.clearClassrooms();
			try {
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item: items){
					if (item.isFormField()) {
						String fieldName = item.getFieldName();
						String fieldValue = item.getString();
					}
					else {
						String fieldName = item.getFieldName();
						String fileName = FilenameUtils.getName(item.getName());
						fis = item.getInputStream();
					}
				}
				/*
				Part filePart = this.request.getPart("file");
				String fileName = filePart.getSubmittedFileName();
				fis = filePart.getInputStream();
				System.out.println("Successfully got filestream");
				*/
				 
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
					DateFormat tf = new SimpleDateFormat("h:mm:ss a");

					
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
								if(cell.getColumnIndex() == 17){
									c.setClassRole(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 21){
									c.setClassCampus(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 22){
									c.setClassMode(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 23){
									c.setClassCrsAttrVal(cell.getStringCellValue());
								}
								if(cell.getColumnIndex() == 24){
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
										c.setClassTimeStart(tf.format(cell.getDateCellValue()));
									}
									if(cell.getColumnIndex() == 12){
										c.setClassTimeEnd(tf.format(cell.getDateCellValue()));
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
				workbook.close();
			} catch(FileNotFoundException e){
				e.printStackTrace();
			} catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e){
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
