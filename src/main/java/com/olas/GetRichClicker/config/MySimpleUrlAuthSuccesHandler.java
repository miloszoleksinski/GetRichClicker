package com.olas.GetRichClicker.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("myAuthenticationSuccessHandler")
public class MySimpleUrlAuthSuccesHandler implements AuthenticationSuccessHandler 
{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException 
	{
		HttpSession session = request.getSession(false);
		if (session != null) 
		{
            session.setMaxInactiveInterval(86400);
        }
		response.sendRedirect("http://localhost:8080/GetRichClicker/");
	}
}
