/*package org.security;

import javax.sql.DataSource;

import org.security.core.authentiction.mobile.SmsCodeAuthenticationSecurityConfig;
import org.security.core.properties.SecurityProperties;
import org.security.core.validata.code.ValidateCodeFilter;
import org.security.core.validata.code.sms.DefaultSmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
public class BrowserSecruityConfigCGQ extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private AuthenticationSuccessHandler defaultAuthenticationSuccessHandller;
	@Autowired
	private AuthenticationFailureHandler defaultAuthenticationFailureHandller;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	@Autowired
	private SpringSocialConfigurer defaultSocialSecurityConfig;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
		
	}
	@Bean 
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
		
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenticationFailureHandler(defaultAuthenticationFailureHandller);
		validateCodeFilter.setSecurityProperties(securityProperties);
		validateCodeFilter.afterPropertiesSet();
		//.loginPage("/browser-default-login.html")
		//.antMatchers("/browser-default-login.html").permitAll()
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
		 .formLogin()
			.loginPage("/authentication/require")
			.loginProcessingUrl("/authentication/form")
			.successHandler(defaultAuthenticationSuccessHandller)
			.failureHandler(defaultAuthenticationFailureHandller)
		    .and()
		    .apply(defaultSocialSecurityConfig)
		    .and()
		.rememberMe()
			 .tokenRepository(persistentTokenRepository())
			 .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
			 .userDetailsService(userDetailsService)
		     .and()
		.authorizeRequests()
		.antMatchers("/authentication/require",
				securityProperties.getBrowser().getLoginPage(),
				securityProperties.getBrowser().getRegistPage(),
				"/code/*").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.csrf().disable()
		.apply(smsCodeAuthenticationSecurityConfig);
		
	}

}
*/