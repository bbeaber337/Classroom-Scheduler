package com.scheduler.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.scheduler.services.*;
import com.scheduler.valueObjects.*;

/**
 * Servlet implementation class Upload
 */
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
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
		if (pathinfo.equalsIgnoreCase("Confirm")){
			request.getServletContext().getRequestDispatcher("/Admin/uploadconfirm.jsp").forward(request, response);
		} else {
			request.getServletContext().getRequestDispatcher("/Admin/upload.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession(false);
		String semester = session.getAttribute("semester").toString();
		String pathinfo = request.getPathInfo();
		if (pathinfo == null){
			pathinfo = "";
		} else {
			pathinfo = pathinfo.substring(1);
		}
		if (pathinfo.equalsIgnoreCase("Confirm")){
			List<String> headers = dbServices.getHeaders(semester);
			Map<String, String> ournames = new HashMap<String,String>();
			for (String s : Class1.OURNAMES){
				String p = request.getParameter(s);
				if (p != null) {
					ournames.put(s, p);
				}
			}
			dbServices.setHeaders(semester, headers, ournames);
			if (request.getParameter("keep") == null || !request.getParameterValues("keep")[0].equalsIgnoreCase("data")){
				dbServices.clearClassrooms(semester);
				dbServices.clearInstructors(semester);
			}
			
			dbServices.extractCombos(semester);
			dbServices.extractClassrooms(semester);
			dbServices.extractInstructors(semester);
			dbServices.deleteDuplicates(semester);
			
			response.sendRedirect(response.encodeURL(context.getContextPath()+"/Classes"));			
		} else {
			// TODO Auto-generated method stub
			Workbook workbook = null;
			
			Classlist classlist = new Classlist();
			List<String> headers = new ArrayList();
			Map<String, String> ourNames = new HashMap<String, String>();
			Map<String, Integer> sizes = new HashMap<String, Integer>();
			InputStream fis = null;
	
			String contentType = request.getContentType();
		    if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)){
				
				//Clear all current class information
				
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
							if(item.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
								fis = item.getInputStream();
							}
						}
					}
					if (fis != null){
						 
						// Using XSSF for xlsx format, for xls use HSSF
						workbook = new XSSFWorkbook(fis);
	
						int numberOfSheets = workbook.getNumberOfSheets();
						
						//looping over each workbook sheet
					    Sheet sheet = workbook.getSheetAt(0);
					    Iterator<Row> rowIterator = sheet.iterator();
					    
					  //Need to set a format in order to convert Dates into Strings
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
						DateFormat tf = new SimpleDateFormat("h:mm:ss a");
						
						// keeping track of classes to combine
						Class1 prevCombo = null;
						int comboNum = 1;
						
						// Pre-populate the hash for what we're looking for
						for (String s : Class1.OURNAMES){
							ourNames.put(s, null);
						}
						Boolean foundAllNames = false;
						if (rowIterator.hasNext()){
							Row row = (Row) rowIterator.next();
							Iterator cellIterator = row.cellIterator();
							while(cellIterator.hasNext()){
								Cell cell = (Cell) cellIterator.next();
								String header = cell.toString();
								if (header.equalsIgnoreCase("")){
									header = "!";
								}
								if (headers.contains(header)){
									int extra = 2;
									while (headers.contains(header + Integer.toString(extra))){
										extra++;
									}
									header = header + Integer.toString(extra);
								}
								headers.add(header);
								sizes.put(header, 0);
								// Find important headers based name
								if (header.equalsIgnoreCase("class nbr")){
									ourNames.put("classnumber", header);
								} else if (header.equalsIgnoreCase("subject")){
									ourNames.put("subject", header);
								} else if (header.equalsIgnoreCase("catalog")){
									ourNames.put("catalog", header);
								} else if (header.equalsIgnoreCase("section")){
									ourNames.put("section", header);
								} else if (header.equalsIgnoreCase("comb sect")){
									ourNames.put("combo", header);
								} else if (header.equalsIgnoreCase("descr")){
									ourNames.put("classname", header);
								} else if (header.equalsIgnoreCase("cap enrl")){
									ourNames.put("capenrolled", header);
								} else if (header.equalsIgnoreCase("tot enrl")){
									ourNames.put("totenrolled", header);
								} else if (header.equalsIgnoreCase("pat")){
									ourNames.put("days", header);
								} else if (header.equalsIgnoreCase("mtg start")){
									ourNames.put("starttime", header);
								} else if (header.equalsIgnoreCase("mtg end")){
									ourNames.put("endtime", header);
								} else if (header.equalsIgnoreCase("facil id")){
									ourNames.put("classroom", header);
								} else if (header.equalsIgnoreCase("capacity")){
									ourNames.put("capacity", header);
								} else if (header.equalsIgnoreCase("last")){
									ourNames.put("instructorlast", header);
								} else if (header.equalsIgnoreCase("first name")){
									ourNames.put("instructorfirst", header);
								} else if (header.equalsIgnoreCase("start date")){
									ourNames.put("startdate", header);
								} else if (header.equalsIgnoreCase("end date")){
									ourNames.put("enddate", header);
								} else if (header.equalsIgnoreCase("component")){
									ourNames.put("component", header);
								}
							}
						}
						Boolean emptyrow = false;
						//iterating over each row
						  while (rowIterator.hasNext() && !emptyrow) {
							emptyrow = true;
							Class1 c = new Class1();
							Row row = (Row) rowIterator.next();
							Iterator<Cell> cellIterator = row.cellIterator();
							//Iterating over each cell (column wise)  in a particular row.
							while (cellIterator.hasNext()) {
								emptyrow = false;
								Cell cell = (Cell) cellIterator.next();
								//The Cell Containing String will is name.
								
								//All VALUES IN THE EXCEL DOCUMENT ARE RECOGNIZED AS A STRING!!!
								String value = null;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									if(DateUtil.isCellDateFormatted(cell)){
										String date = tf.format(cell.getDateCellValue());
										if (date.equalsIgnoreCase("12:00:00 AM")){
											date = df.format(cell.getDateCellValue());
										}
										value = date;
									} else {
									    value = Integer.toString((int)cell.getNumericCellValue());
									}
								 } else {
									    value = cell.getStringCellValue();
										
								}
								if (value == null){
									value = "";
								}
								c.set(headers.get(cell.getColumnIndex()), value);
								// keep track of how large the inputs are
								if (sizes.get(headers.get(cell.getColumnIndex())) < value.length()){
									sizes.put(headers.get(cell.getColumnIndex()), value.length());
								}
								
								// find remaining important headers using logic
							    if (!foundAllNames){
							    	if (ourNames.get("combo") == null && value.equalsIgnoreCase("c")){
							    		ourNames.put("combo",headers.get(cell.getColumnIndex()));
							    	}
							    }
							}
							if (!foundAllNames){
								foundAllNames = ourNames.containsValue(null);
							}
							
							//End row, add class
							if(!emptyrow){
								classlist.add(c);
							}
						  }
					}
					dbServices.setHeaders(semester, headers, ourNames);
					dbServices.createClassTables(semester, sizes);
					classlist.setHeaders(dbServices.getOurNames(semester));
					classlist.updateClasses(semester);
					dbServices.uploadClasses(semester, classlist);
							  
				//ms.uploadClasses(headers, ourNames, classList, sizes);
				} catch(Exception e){
					e.printStackTrace();
				} finally {
					if (fis != null){
						try{
							fis.close();
						} catch (Exception e){
							e.printStackTrace();
						}
					}
					if (workbook != null){
						try{
							workbook.close();
						} catch (Exception e){
							e.printStackTrace();
						}
					}
				}
			    out.append("success");
		    } else {
		    	doGet(request,response);
		    }
		}
	}
}
