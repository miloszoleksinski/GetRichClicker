package com.olas.GetRichClicker.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.olas.GetRichClicker.user.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	 {
		 auth.authenticationProvider( authProvider() );
	 }
	 
	 @Override
	 protected void configure(HttpSecurity http) throws Exception
	 {
		 http.authorizeRequests()
		 	.antMatchers("/login**","/register**").anonymous()
		 	.antMatchers("/").authenticated()
		 	.antMatchers("/test").hasAnyRole("HOST", "ADMIN")
		.and()
			.exceptionHandling().accessDeniedPage("/WEB-INF/views/accessDenied.jsp")
		.and()
			.formLogin().loginPage("/login")
			.defaultSuccessUrl("/")
			.successHandler( myAuthenticationSuccessHandler() )
			.failureHandler( exceptionMappingAuthenticationFailureHandler() )
			.usernameParameter("username").passwordParameter("password")	
		.and()
			.logout().logoutSuccessHandler( myLogoutSuccessHandler() );
	 }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() 
	 {
	     return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 public DaoAuthenticationProvider authProvider() 
	 {
	     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     authProvider.setUserDetailsService(myUserDetailsService);
	     authProvider.setPasswordEncoder(passwordEncoder);
	     return authProvider;
	 }
	 
	 @Bean
	 public AuthenticationSuccessHandler myAuthenticationSuccessHandler()
	 {
		 return new MySimpleUrlAuthSuccesHandler();
	 }
	 
	 @Bean
	 public LogoutSuccessHandler myLogoutSuccessHandler()
	 {
		 return new MyLogoutSuccesHandler();
	 }
	 
	 @Bean
	 public ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler()
	 {
	     ExceptionMappingAuthenticationFailureHandler ex = new ExceptionMappingAuthenticationFailureHandler();
	     Map<String, String> mappings = new HashMap<String, String>();
	     mappings.put("org.springframework.security.core.userdetails.UsernameNotFoundException", "/login?error");
	     mappings.put("org.springframework.security.authentication.BadCredentialsException", "/login?error");
	     mappings.put("org.springframework.security.authentication.LockedException", "/login?banned");
	     ex.setExceptionMappings(mappings);
	     return ex;
	 }
}
