package pl.btbw.xss.wrapper;

import pl.btbw.xss.util.CleanXSSUtil;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public String getQueryString() {
		System.out.println("getQueryString");
		try {
			String queryString = super.getQueryString();
			queryString = URLDecoder.decode(queryString, "UTF-8");
			return CleanXSSUtil.cleanXSS(queryString);
		} catch (Exception e) {
			return "";
		}
	}

	public String[] getParameterValues(String parameter) {
		System.out.println("getParameterValues");
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = CleanXSSUtil.cleanXSS(values[i]);
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		System.out.println("getParameter");
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return CleanXSSUtil.cleanXSS(value);
	}

	private Map<String, String[]> sanitizedQueryString;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> getParameterMap() {
		System.out.println("getParameterMap");
		if (sanitizedQueryString == null) {
			Map<String, String[]> res = new HashMap<>();
			Map<String, String[]> originalQueryString = super.getParameterMap();
			if (originalQueryString != null) {
				for (String key : originalQueryString.keySet()) {
					String[] rawVals = originalQueryString.get(key);
					String[] snzVals = new String[rawVals.length];
					for (int i = 0; i < rawVals.length; i++) {
						snzVals[i] = CleanXSSUtil.cleanXSS(rawVals[i]);
						System.out.println("Sanitized: " + rawVals[i] + " to " + snzVals[i]);
					}
					res.put(CleanXSSUtil.cleanXSS(key), snzVals);
				}
			}
			sanitizedQueryString = res;
		}
		return sanitizedQueryString;
	}

	public String getHeader(String name) {
		System.out.println(name);
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		return CleanXSSUtil.cleanXSS(value);
	}


}