package com.scheduler.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.scheduler.services.*;
import com.scheduler.valueObjects.*;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder.Header;

/**
 * Servlet implementation class Download
 */
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathinfo = request.getPathInfo();
		if (pathinfo == null){
			pathinfo = "";
		} else {
			pathinfo = pathinfo.substring(1);
		}
		request.getServletContext().getRequestDispatcher("/Admin/download.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession(false);
		String semester = session.getAttribute("semester").toString();
		String pathinfo = request.getPathInfo();
		if (pathinfo == null){
			pathinfo = "";
		} else {
			pathinfo = pathinfo.substring(1);
		}
		if (pathinfo.equalsIgnoreCase("excel")){
			
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet classSheet = wb.createSheet("Classes");
			CreationHelper createHelper = wb.getCreationHelper();
			
			//Set Headings
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
			List<String> headers = dbServices.getHeaders(semester);
			Row headerRow = classSheet.createRow(0);
			for (int i = 0; i < headers.size(); i++){
				Cell cell = headerRow.createCell(i);
				if (!headers.get(i).startsWith("!")){
					cell.setCellValue(headers.get(i));
				}
				cell.setCellStyle(headingStyle);
			}
			//Cell sectionHeaderCell = headerRow.createCell(3);
			
			//Row counter
			int row = 1;

			//Get Data
			Classlist c = new Classlist();
			c = dbServices.getClasses(semester);
			Map<String, String> ournames = dbServices.getOurNames(semester);

			SimpleDateFormat sdfIn = new SimpleDateFormat("h:mm:ss a");
			SimpleDateFormat sdfDate = new SimpleDateFormat("M/d/yyyy");
			
			for (Class1 item : c) {
							
				CellStyle time = wb.createCellStyle();
				time.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss AM/PM"));
				CellStyle date = wb.createCellStyle();
				date.setDataFormat(createHelper.createDataFormat().getFormat("M/d/yyyy"));
				
				Row dataRow = classSheet.createRow(row);
				for(int i = 0; i < headers.size(); i++){
					if (headers.get(i).equalsIgnoreCase(ournames.get("startdate")) || headers.get(i).equalsIgnoreCase(ournames.get("enddate"))){
						Cell t = dataRow.createCell(i);
						Date d = null;
						try{ d = sdfDate.parse(item.get(headers.get(i))); } catch (Exception e) { /*ignore*/ }
						if (d != null) {
							t.setCellValue(d);
						}
						t.setCellStyle(date);
					} else if (headers.get(i).equalsIgnoreCase(ournames.get("starttime")) || headers.get(i).equalsIgnoreCase(ournames.get("endtime"))){
						Cell t = dataRow.createCell(i);
						Date d = null;
						try{ d = sdfIn.parse(item.get(headers.get(i))); } catch (Exception e) { /*ignore*/ }
						if (d != null) {
							t.setCellValue(d);
						}
						t.setCellStyle(time);
					} else {
						/*
						try {
							dataRow.createCell(i).setCellValue(Integer.parseInt(item.get(headers.get(i))));
						} catch (Exception e){
							// not a number so get string value instead
							dataRow.createCell(i).setCellValue(item.get(headers.get(i)));
						} */
						dataRow.createCell(i).setCellValue(item.get(headers.get(i)));
					}
				}
				row = row + 1;
			}
			/*
			File file = new File("PKI.xls");
			FileInputStream in = null;
			OutputStream fOut = null;
			//response.setContentType("application/octet-stream");
			//response.setHeader( "Content-Disposition",String.format("attachment; filename=\"%s\"", file.getName()));
			try{
				response.reset();
				in = new FileInputStream(file);
				response.setContentType("application/vnd.ms-excel");
				//response.setContentLength((int) file.length());
				response.addHeader("content-disposition", "attachment; filename=data.xlsx");
				fOut = response.getOutputStream();
		    	IOUtils.copyLarge(in, fOut);
			}
			catch(Exception e){
				System.out.println("Unable to download file");
			} */
			//FileOutputStream fOut = new FileOutputStream(new File("C:\\myDownloads\\PKI.xlsx"));
			//FileOutputStream fOut = new FileOutputStream(new File(file.getName()));
	        // This should send the file to browser
	        //OutputStream fOut = response.getOutputStream();
	        //FileInputStream in = new FileInputStream(file);
			//wb.write(fOut);
			//in.close();
			//fOut.close();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=PKI"+ session.getAttribute("semester").toString() +".xlsx");
			OutputStream fOut = response.getOutputStream();
			wb.write(fOut);
			fOut.close();
			//stream.print(wb.toString());
			wb.close();
			
		} else if (pathinfo.equalsIgnoreCase("word")) {
			//Get all classes
			Classlist items = new Classlist();
			if (request.getParameterValues("subjects") != null){
				for (String subject: request.getParameterValues("subjects")){
					items.addAll(dbServices.getClassesBySubject(semester, subject));
				}
			} else {
				items.addAll(dbServices.getClasses(semester));
			}
			Map<String,String> ournames = dbServices.getOurNames(semester);
			String str = null;
			
			//Capitalize the Semester
			String sessionVal = session.getAttribute("semester").toString();
			sessionVal = sessionVal.substring(0, 1).toUpperCase() + sessionVal.substring(1);
			
			try{
			XWPFDocument document= new XWPFDocument();
			
			XWPFParagraph paragraph = document.createParagraph();
			XWPFParagraph paragraph1 = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun run = paragraph.createRun();
			XWPFRun run2 = paragraph.createRun();
			run.setBold(true);
			run.setFontSize(15);
			run.setText("University of Nebraska-Omaha");
			run.addBreak();
			run.setText("Schedule of Classes for " + sessionVal);
			run.addBreak();
			run.setText("Regular Academic Session");
			run.addBreak();
			run.setText("Information Science & Technology - Computer Science - Subject: Computer Science");
			run.addBreak();
			run.addBreak();
			
			/*  REMOVED HEADING AND ADDED IT TO EACH PARAPGRAPH INSTEAD
			paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			run = paragraph.createRun();
			run.setBold(true);
			//run.setUnderline(null);
			run.setText("Subject");
			run.addTab();
			run.addTab();
			run.setText("Catalog Nbr");
			run.addTab();
			run.setText("Section");
			run.addTab();
			run.setText("Class Nbr");
			run.addTab();
			run.setText("Course Title");
			run.addTab();
			run.addTab();
			run.addTab();
			run.setText("Component");
			run.addBreak();*/
			
			
			for (Class1 c : items){
				paragraph1 = document.createParagraph();
				paragraph1.setAlignment(ParagraphAlignment.CENTER);
				run2 = paragraph1.createRun();
				run2.setUnderline(UnderlinePatterns.THICK);
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				/*run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();*/
				paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				run = paragraph.createRun();
				run.setBold(false);
				run.setFontSize(12);
				if (c.get(ournames.get("subject")) != null){
					run.setText("Subject: " + c.get(ournames.get("subject")));
				} else {run.setText("Subject: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("catalog")) != null){
					run.setText("Catalog Nbr: " + c.get(ournames.get("catalog")));
				} else {run.setText("Catalog Nbr: Not set");}
				run.addTab();
				if (c.get(ournames.get("section")) != null){
					run.setText("Section: " + c.get(ournames.get("section")));
				} else {run.setText("Section: Not set");}
				run.addTab();
				if (c.get(ournames.get("classnumber")) != null){
					run.setText("Class Nbr: " + c.get(ournames.get("classnumber")));
				} else {run.setText("Class Nbr: Not set");}
				run.addTab();
				if (c.get(ournames.get("classname")) != null){
					run.setText("Course Title: " + c.get(ournames.get("classname")));
				} else {run.setText("Course Title: Not set");}
				run.addTab();
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("component")) != null){
					run.setText("Component: " + c.get(ournames.get("component")));
				} else {run.setText("Component: Not set");}
				run.addBreak();

				if (c.get(ournames.get("classroom")) != null){
					run.setText("Room: " + c.get(ournames.get("classroom")));
				} else {run.setText("Room: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("days")) != null){
					run.setText("Days: " + c.get(ournames.get("days")));
				} else {run.setText("Days: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("starttime")) != null || c.get(ournames.get("endtime")) != null){
					run.setText("Time: " + c.get(ournames.get("starttime")) + "-" + c.get(ournames.get("endtime")));
				} else {run.setText("Time: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("instructorfirst")) != null || c.get(ournames.get("instructorlast")) != null){
					run.setText("Instructor: " + c.get(ournames.get("instructorfirst")) + " " + c.get(ournames.get("instructorlast")));
				} else {run.setText("Instructor: Not set");}
				run.addBreak();

				if (c.get(ournames.get("capcapacity")) != null){
					run.setText("Class Enr Cap: " + c.get(ournames.get("capcapacity")));
				} else {run.setText("Class Enr Cap: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("totenrolled")) != null){
					run.setText("Class Enr Tot: " + c.get(ournames.get("totenrolled")));
				} else {run.setText("Class Enr Tot: Not set");}
				run.addTab();
				run.addTab();
				//run.setText("Combined Section: ");

			}


			response.setContentType("application/application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-Disposition", "attachment; filename=PKI"+ session.getAttribute("semester").toString() +".docx");
			document.write(response.getOutputStream());
			} catch(Exception a){
				System.out.print(a);
			}
			
			//run.setText("Testing. ");
			
			//FileOutputStream fOut = new FileOutputStream(new File("C:\\myDownloads\\CLASSES.docx"));
			
		} else if (pathinfo.equalsIgnoreCase("changes")) {

			Map<String,String> ournames = dbServices.getOurNames(semester);
			List<String> headers = dbServices.getHeaders(semester);
			String str = null;
			
			//Capitalize the Semester
			String sessionVal = session.getAttribute("semester").toString();
			sessionVal = sessionVal.substring(0, 1).toUpperCase() + sessionVal.substring(1);
			
			try{
			XWPFDocument document= new XWPFDocument();
			
			XWPFParagraph paragraph = document.createParagraph();
			XWPFParagraph paragraph1 = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun run = paragraph.createRun();
			XWPFRun run2 = paragraph.createRun();
			run.setBold(true);
			run.setFontSize(15);
			run.setText("University of Nebraska-Omaha");
			run.addBreak();
			run.setText("Schedule of Classes for " + sessionVal);
			run.addBreak();
			run.setText("Regular Academic Session");
			run.addBreak();
			run.setText("Information Science & Technology - Computer Science - Subject: Computer Science");
			run.addBreak();
			run.addBreak();
			
			XWPFRun removedClassesHeading = document.createParagraph().createRun();
			removedClassesHeading.setFontSize(14);
			removedClassesHeading.setText("REMOVED CLASSES");			
			
			for (Class1 c : dbServices.getDeletedClasses(semester)){
				paragraph1 = document.createParagraph();
				paragraph1.setAlignment(ParagraphAlignment.CENTER);
				run2 = paragraph1.createRun();
				run2.setUnderline(UnderlinePatterns.THICK);
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				/*run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();*/
				paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				run = paragraph.createRun();
				run.setBold(false);
				run.setFontSize(12);
				if (c.get(ournames.get("subject") + "upload") != null){
					run.setText("Subject: " + c.get(ournames.get("subject") + "upload"));
				} else {run.setText("Subject: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("catalog") + "upload") != null){
					run.setText("Catalog Nbr: " + c.get(ournames.get("catalog") + "upload"));
				} else {run.setText("Catalog Nbr: Not set");}
				run.addTab();
				if (c.get(ournames.get("section") + "upload") != null){
					run.setText("Section: " + c.get(ournames.get("section") + "upload"));
				} else {run.setText("Section: Not set");}
				run.addTab();
				if (c.get(ournames.get("classnumber") + "upload") != null){
					run.setText("Class Nbr: " + c.get(ournames.get("classnumber") + "upload"));
				} else {run.setText("Class Nbr: Not set");}
				run.addBreak();
				if (c.get(ournames.get("classname") + "upload") != null){
					run.setText("Course Title: " + c.get(ournames.get("classname") + "upload"));
				} else {run.setText("Course Title: Not set");}
				run.addTab();
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("component") + "upload") != null){
					run.setText("Component: " + c.get(ournames.get("component") + "upload"));
				} else {run.setText("Component: Not set");}
				run.addBreak();

				if (c.get(ournames.get("classroom") + "upload") != null){
					run.setText("Room: " + c.get(ournames.get("classroom") + "upload"));
				} else {run.setText("Room: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("days") + "upload") != null){
					run.setText("Days: " + c.get(ournames.get("days") + "upload") );
				} else {run.setText("Days: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("starttime") + "upload") != null || c.get(ournames.get("endtime") + "upload") != null){
					run.setText("Time: " + c.get(ournames.get("starttime") + "upload") + "-" + c.get(ournames.get("endtime") + "upload"));
				} else {run.setText("Time: Not set");}
				run.addTab();
				run.addTab();
				if (c.get(ournames.get("instructorfirst") + "upload") != null || c.get(ournames.get("instructorlast") + "upload") != null){
					run.setText("Instructor: " + c.get(ournames.get("instructorfirst") + "upload") + " " + c.get(ournames.get("instructorlast" ) + "upload"));
				} else {run.setText("Instructor: Not set");}
			}
			

			
			XWPFRun changedClassesHeading = document.createParagraph().createRun();
			changedClassesHeading.setFontSize(14);
			changedClassesHeading.setText("CHANGED CLASSES");			
			
			for (Class1 c : dbServices.getChangedClasses(semester)){
				paragraph1 = document.createParagraph();
				paragraph1.setAlignment(ParagraphAlignment.CENTER);
				run2 = paragraph1.createRun();
				run2.setUnderline(UnderlinePatterns.THICK);
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				/*run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();*/
				paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				run = paragraph.createRun();
				run.setBold(false);
				run.setFontSize(12);
				run.setText("Original Listing:");
				run.addTab();
				run.setText(ournames.get("classnumber") + ": " + c.get(ournames.get("classnumber")));
				run.addTab();
				run.setText(ournames.get("subject") + ": " + c.get(ournames.get("subject")));
				run.addTab();
				run.setText(ournames.get("catalog") + ": " + c.get(ournames.get("catalog")));
				run.addTab();
				run.setText(ournames.get("section") + ": " + c.get(ournames.get("section")));
				run.addBreak();
				run.setText("Changes:");
				run.addBreak();
				for (String s : headers){
					if (!c.get(s).equalsIgnoreCase(c.get(s + "upload"))){
						run.setText(s + ": " + c.get(s));
						run.addBreak();
					}
				}
			}
			
			XWPFRun addedClassesHeading = document.createParagraph().createRun();
			addedClassesHeading.setFontSize(14);
			addedClassesHeading.setText("ADDED CLASSES");			
			
			for (Class1 c : dbServices.getAddedClasses(semester)){
				paragraph1 = document.createParagraph();
				paragraph1.setAlignment(ParagraphAlignment.CENTER);
				run2 = paragraph1.createRun();
				run2.setUnderline(UnderlinePatterns.THICK);
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				/*run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();*/
				paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				run = paragraph.createRun();
				run.setBold(false);
				run.setFontSize(12);
				for (String s : headers){
					if (s.equalsIgnoreCase(ournames.get("classname"))){
						run.addBreak();
					}
					if (c.get(s) != null){
						run.setText(s + ": " + c.get(s));
					} else {
						run.setText(s + ": ");
					}
					run.addTab();
				}
			}


			response.setContentType("application/application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-Disposition", "attachment; filename=PKI"+ session.getAttribute("semester").toString() + "Changes.docx");
			document.write(response.getOutputStream());
			} catch(Exception a){
				System.out.print(a);
			}
			
			//run.setText("Testing. ");
			
			//FileOutputStream fOut = new FileOutputStream(new File("C:\\myDownloads\\CLASSES.docx"));
		} else {
			doGet(request, response);
		}
	}

}
