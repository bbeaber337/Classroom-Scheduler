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

			String sessionVal = session.getAttribute("semester").toString();
			sessionVal = sessionVal.substring(0, 1).toUpperCase() + sessionVal.substring(1);
			
			FileOutputStream fOut = new FileOutputStream("PKI.docx");
			
			try{
			XWPFDocument document= new XWPFDocument();
			
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun run = paragraph.createRun();
			run.setBold(true);
			run.setFontSize(11);
			run.setText("University of Nebraska-Omaha");
			run.addBreak();
			run.setText("Schedule of Classes for " + sessionVal);
			run.addBreak();
			run.setText("Regular Academic Session");
			run.addBreak();
			run.setText("Information Science & Technology - Computer Science - Subject: Computer Science");
			run.addBreak();
			run.addBreak();
			
			paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			run = paragraph.createRun();
			run.setBold(true);
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
