package com.scheduler.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class adminFilter
 * Ensures only able to access to paths that start with /Admin/ if the user is logged in as an admin
 */
public class adminFilter implements Filter {

	private ServletContext context;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		context = fConfig.getServletContext();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		if ((session != null && (session.getAttribute("userlevel") != null && session.getAttribute("userlevel").toString().equalsIgnoreCase("2")))){
			// pass the request along the filter chain
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect(resp.encodeURL(context.getContextPath()+"/Home"));
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
