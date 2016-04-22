package com.scheduler.services;

import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.model.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.scheduler.services.*;
import com.scheduler.valueObjects.*;
import com.scheduler.jsp.*;
import com.scheduler.dbconnector.*;

public class wordServices extends baseJSP {
	
	private MyServices ms = null;
	private adminServices as = null;
	
	public wordServices(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, JspWriter stream) throws Exception {
		super(session, request, response, stream);
		ms = new MyServices(session, request, response, stream);
		as = new adminServices(session, request, response, stream);
	}
	
	
	
	public void exportData() throws Exception {
		
		if(request.getParameter("exportWord") != null){

			//Get all classes
			List<Class1> items = ms.getClasses();
			String str = null;
			
			//Capitalize the Semester
			String sessionVal = session.getAttribute("semester").toString();
			sessionVal = sessionVal.substring(0, 1).toUpperCase() + sessionVal.substring(1);
			
			FileOutputStream fOut = new FileOutputStream("PKI.docx");
			
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
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				run2.addTab();
				
				paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.CENTER);
				run = paragraph.createRun();
				run.setBold(false);
				run.setFontSize(12);
				run.setText("Subject: " + c.getClassSubject());
				run.addTab();
				run.addTab();
				run.setText("Catalog Nbr: " + c.getClassCatalog());
				run.addTab();
				run.setText("Section: " + c.getClassSection());
				run.addTab();
				str = Integer.toString(c.getClassNumber());
				run.setText("Class Nbr: " + str);
				run.addTab();
				run.setText("Course Title: " + c.getClassName());
				run.addTab();
				run.addTab();
				run.addTab();
				run.setText("Component: " + c.getClassComponent());
				run.addBreak();
				
				run.setText("Room: " + c.getClassRoom());
				run.addTab();
				run.addTab();
				run.setText("Days: " + c.getClassDays());
				run.addTab();
				run.addTab();
				run.setText("Time: " + c.getClassTimeStart() + "-" + c.getClassTimeEnd());
				run.addTab();
				run.addTab();
				run.setText("Instructor: " + c.getClassInstructFirst() + " " + c.getClassInstructLast());
				run.addBreak();
				
				run.setText("Class Enr Cap: " + c.getClassCapacity());
				run.addTab();
				run.addTab();
				run.setText("Class Enr Tot: " + c.getClassEnrolled());
				run.addTab();
				run.addTab();
				//run.setText("Combined Section: ");

			}


			response.setContentType("application/vnd.ms-word");
			response.setHeader("Content-Disposition", "attachment; filename="+ session.getAttribute("semester").toString() +".docx");
			document.write(response.getOutputStream());
			} catch(Exception a){
				System.out.print(a);
			}
			
			//run.setText("Testing. ");
			
			//FileOutputStream fOut = new FileOutputStream(new File("C:\\myDownloads\\CLASSES.docx"));

			fOut.close();
		}
	}
}
