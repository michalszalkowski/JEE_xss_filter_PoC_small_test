package pl.btbw.xss;

import pl.btbw.xss.wrapper.XSSJsonRequestWrapper;
import pl.btbw.xss.wrapper.XSSRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request,
	                     ServletResponse response,
	                     FilterChain chain) throws IOException, ServletException {

//		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
		chain.doFilter(new XSSJsonRequestWrapper((HttpServletRequest) request), response);
	}

}