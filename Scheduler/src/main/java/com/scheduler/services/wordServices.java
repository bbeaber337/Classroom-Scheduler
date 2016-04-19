package com.scheduler.services;

import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.text.SimpleDateFormat;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

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
		
			XWPFDocument document= new XWPFDocument();
			//XWPFParagraph paragraph = document.createParagraph();
			//XWPFRun run=paragraph.createRun();
			
			//run.setText("Testing. ");
			
			//FileOutputStream fOut = new FileOutputStream(new File("C:\\myDownloads\\CLASSES.docx"));
			response.setContentType("application/vnd.ms-word");
			response.setHeader("Content-Disposition", "attachment; filename="+ session.getAttribute("semester").toString() +".docx");
			document.write(response.getOutputStream());
			//fOut.close();
		}
	}
}
