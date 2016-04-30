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
 * Servlet Filter implementation class semesterFilter
 */
public class semesterFilter implements Filter {

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
		String path = req.getRequestURI();
		if ((session != null && session.getAttribute("semester") != null)
				|| (path != null && path.startsWith(context.getContextPath() + "/Login"))
				|| (path != null && path.startsWith(context.getContextPath() + "/Home"))
				|| (path != null && path.startsWith(context.getContextPath() + "/Admin"))){
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
