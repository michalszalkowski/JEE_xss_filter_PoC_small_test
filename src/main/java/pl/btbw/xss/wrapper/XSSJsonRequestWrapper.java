package pl.btbw.xss.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.btbw.xss.util.CleanXSSUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

public class XSSJsonRequestWrapper extends XSSRequestWrapper {

	public XSSJsonRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	private void goDeeper(Map<String, Object> jsonMap) {
		for (String key : jsonMap.keySet()) {
			if (jsonMap.get(key) != null) {
				if (jsonMap.get(key).getClass() == String.class) {
					jsonMap.put(key, CleanXSSUtil.cleanXSS((String) jsonMap.get(key)));
				} else if (jsonMap.get(key).getClass() == LinkedHashMap.class) {
					goDeeper((Map<String, Object>) jsonMap.get(key));
				}
			}
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		System.out.println("getInputStream");

		ObjectMapper mapper = new ObjectMapper();
		InputStream inputStream = super.getInputStream();

		Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);
		goDeeper(jsonMap);

		final ByteArrayInputStream byteIn = new ByteArrayInputStream(
				Charset.forName("UTF-16").encode(mapper.writeValueAsString(jsonMap)).array()
		);

		return new ServletInputStream() {

			@Override
			public boolean isFinished() {
				return true;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			@Override
			public int read() throws IOException {
				return byteIn.read();
			}
		};
	}

}