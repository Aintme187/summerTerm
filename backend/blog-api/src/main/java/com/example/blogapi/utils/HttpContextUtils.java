package com.example.blogapi.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
 
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
 
/**
 * @author MrBird
 */
public class HttpContextUtils {

	private HttpContextUtils() {

	}

	//获取HttpServletRequest请求
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
	}
}
