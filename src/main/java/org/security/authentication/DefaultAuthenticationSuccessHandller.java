package org.security.authentication;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.security.core.properties.LoginType;
import org.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义登录成功后处理行为
 * 
 * 一般 形式 implements AuthenticationSuccessHandler
 * 
 * 默认父类的方法就是跳转
 * 
 * @author dourl
 *
 */
@Component("defaultAuthenticationSuccessHandler")
public class DefaultAuthenticationSuccessHandller extends SavedRequestAwareAuthenticationSuccessHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	// spring boot 启动的时候会有一个
	@Autowired
	private ObjectMapper ObjectMapper;

	@Autowired
	private SecurityProperties securityProperties;

	private RequestCache requestCache = new HttpSessionRequestCache(); 
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("默认成功处理类--登录成功");
		
		 SavedRequest savedRequest = requestCache.getRequest(request, response);
		//获取认证成功后的角色（？？？）
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		String path = request.getContextPath() ;
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
		if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(ObjectMapper.writeValueAsString(authentication));

		} else {
			logger.info("request{}",request.getRequestURL());
			
			 if (savedRequest == null) {
		           super.onAuthenticationSuccess(request, response, authentication);
		           return;
		       }
			 
			 String targetUrlParameter = getTargetUrlParameter();
		       if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
		           requestCache.removeRequest(request, response);
		           super.onAuthenticationSuccess(request, response, authentication);
		           return;
		       }
		       clearAuthenticationAttributes(request);
		       // Use the DefaultSavedRequest URL
		       String targetUrl = savedRequest.getRedirectUrl();
		       logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);

		       getRedirectStrategy().sendRedirect(request, response,this.getDefaultTargetUrl());
			
		}
	}
	
	public void setRequestCache(RequestCache requestCache) {  
	       this.requestCache = requestCache;  
	   }
}
