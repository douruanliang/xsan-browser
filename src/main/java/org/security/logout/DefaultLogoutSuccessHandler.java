package org.security.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.security.core.properties.SecurityProperties;
import org.security.core.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * 默认成功退出器 与 logourURL互斥
 * @author dourl
 *
 */
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public DefaultLogoutSuccessHandler(String logOutUrl) {
		this.logOutUrl = logOutUrl;
	}

	private String logOutUrl;
	
	private ObjectMapper ObjectMapper = new ObjectMapper();
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("退出成功");
		if(StringUtils.isBlank(logOutUrl)){
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(ObjectMapper.writeValueAsString(new SimpleResponse("退出成功")));
		}else{
			//如果配置了退出url 则跳转
			response.sendRedirect(logOutUrl);
		}
	}

	
	
	
	
	
	
	
}
