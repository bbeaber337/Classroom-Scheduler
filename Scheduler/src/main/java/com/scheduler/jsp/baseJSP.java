package com.scheduler.jsp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

public class baseJSP {
	
	public HttpSession session;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public JspWriter stream;

	
	public baseJSP(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			JspWriter stream) throws Exception {
		this.session = session;
		this.request = request;
		this.response = response;
		this.stream = stream;
	}

	
	
	public void redirect(String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	public String getPageName() {
		String uri = request.getRequestURI();
		return uri.substring(uri.lastIndexOf("/")+1);
	}
	
	
	public String getCurrentUrl() {
		return request.getRequestURI();
	}
}