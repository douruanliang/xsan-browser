package org.security.validate.code.impl;

import org.security.core.validata.code.ValidateCode;
import org.security.core.validata.code.ValidateCodeRepository;
import org.security.core.validata.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
	/**
	 * 校验码放入Session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request,ValidateCodeType validateCodeType) {
		//return null;
		return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
	}
	
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
		sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType), code);

	}

	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
		// TODO Auto-generated method stub
		return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, validateCodeType));
	}

	@Override
	public void removeget(ServletWebRequest request, ValidateCodeType validateCodeType) {
		sessionStrategy.removeAttribute(request, getSessionKey(request, validateCodeType));

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
