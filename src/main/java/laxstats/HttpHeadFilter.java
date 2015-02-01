package laxstats;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.stereotype.Component;

@Component
public class HttpHeadFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		if (isHttpHead(httpServletRequest)) {
			final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			final NoBodyResponseWrapper noBodyResponseWrapper = new NoBodyResponseWrapper(
					httpServletResponse);

			chain.doFilter(new ForceGetRequestWrapper(httpServletRequest),
					noBodyResponseWrapper);
			noBodyResponseWrapper.setContentLength();
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

	private boolean isHttpHead(HttpServletRequest request) {
		return "HEAD".equals(request.getMethod());
	}

	private class ForceGetRequestWrapper extends HttpServletRequestWrapper {
		public ForceGetRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getMethod() {
			return "GET";
		}
	}

	private class NoBodyResponseWrapper extends HttpServletResponseWrapper {
		private final NoBodyOutputStream noBodyOutputStream = new NoBodyOutputStream();
		private PrintWriter writer;

		public NoBodyResponseWrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return noBodyOutputStream;
		}

		@Override
		public PrintWriter getWriter() throws UnsupportedEncodingException {
			if (writer == null) {
				writer = new PrintWriter(new OutputStreamWriter(
						noBodyOutputStream, getCharacterEncoding()));
			}

			return writer;
		}

		void setContentLength() {
			super.setContentLength(noBodyOutputStream.getContentLength());
		}
	}

	private class NoBodyOutputStream extends ServletOutputStream {
		private int contentLength = 0;

		int getContentLength() {
			return contentLength;
		}

		@Override
		public void write(int b) {
			contentLength++;
		}

		@Override
		public void write(byte buf[], int offset, int len) throws IOException {
			contentLength += len;
		}

		@SuppressWarnings("unused")
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@SuppressWarnings("unused")
		public void setWriteListener(WriteListener arg0) {
			// TODO Auto-generated method stub

		}
	}
}
