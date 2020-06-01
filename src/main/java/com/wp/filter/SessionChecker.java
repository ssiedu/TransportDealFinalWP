package com.wp.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
//import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class SessionChecker
 */
@WebFilter(urlPatterns = "/*",
initParams = { @WebInitParam(name = "excludeUrls", value = "/,/login,/customer/newcustomer,/customer/addcustomer,/transporter/newtransporter,/transporter/addtransporter")})
public class SessionChecker implements Filter {

	List<String> excludedURL;
	final String CUSTOMERURL = "/customer/";
	final String TRANSPORTERURL = "/transporter/";
	final String ADMINURL = "/admin/";
	/**
	 * Default constructor.
	 */
	public SessionChecker() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("calling from order 1");
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String path = ((HttpServletRequest) request).getServletPath();
//		String requestType = path.substring(0, path.indexOf('/', 1)+1);
		System.out.println(path);
		if (!excludedURL.contains(path)) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			System.out.println("-----------------------");
			if(session!=null) {
				String userType = (String)session.getAttribute("utype");
				if(userType!=null) {
					System.out.println("=============USERTYPE============="+userType);
					if(userType.equals("admin") && path.startsWith(ADMINURL))
						chain.doFilter(request, response);
					else if(userType.equals("customer") && path.startsWith(CUSTOMERURL)) {
						System.out.println("==========FILTER CUSTOMER===========");
						chain.doFilter(request, response);
					}
					else if(userType.equals("transporter") && path.startsWith(TRANSPORTERURL)) {
						System.out.println("==========FILTER TRANSPORTER===========");
						chain.doFilter(request, response);
					}
					else {
						System.out.println("==========HOME===========");
						httpResponse.sendRedirect("/TransportPortal/");
					}
				}
				else {
					System.out.println("==========HOME===========");
					httpResponse.sendRedirect("/TransportPortal/");
				}
			}
			else {
				System.out.println("==========HOME===========");
				httpResponse.sendRedirect("/TransportPortal/");
			}
		} else
			chain.doFilter(request, response);

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String url = fConfig.getInitParameter("excludeUrls");
		excludedURL = Arrays.asList(url.split(","));
	}

}
