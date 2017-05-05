package com.allets.backend.dashboard.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;


public class HttpLogInterceptor extends HandlerInterceptorAdapter {

	static final Logger log = LoggerFactory.getLogger(HttpLogInterceptor.class);
	static final String STOP_WATCH_NAME = "HTTP_REQ_STOPWATCH";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (log.isInfoEnabled()) {

			StringBuffer buffer = new StringBuffer();
			buffer.append(
					"\n\n\n--------------------------------------------------\n")
					.append("Request Information\n")
					.append("--------------------------------------------------\n")
					.append("Request URL : "
							+ request.getRequestURL().toString() + "\n")
					.append("Method : " + request.getMethod() + "\n")
					.append("HTTP Status : " + response.getStatus() + "\n")
					.append("Session ID : " + request.getSession().getId()
							+ "\n")
					.append("\n\nHeaders : ★★★★★★★★★★★★★★★★★★★★★★\n");

			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				buffer.append(headerName + " : " + headerValue + "\n");
			}

			// multipart가 아닐 경우
			if (!isMultipart(request)) {

				buffer.append("\n\nParameters : ★★★★★★★★★★★★★★★★★★★★★★\n");

				Enumeration<String> parameterNames = request
						.getParameterNames();

				while (parameterNames.hasMoreElements()) {

					String paramName = parameterNames.nextElement();
					String[] paramValues = request
							.getParameterValues(paramName);
					buffer.append(paramName + " : "
							+ JsonUtil.marshallingJson(paramValues) + "\n");

				}
			}
			buffer.append("--------------------------------------------------\n");
			log.info(buffer.toString());

		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private boolean isMultipart(final HttpServletRequest request) {
		return request.getContentType() != null
				&& request.getContentType().startsWith("multipart/form-data");
	}
}
