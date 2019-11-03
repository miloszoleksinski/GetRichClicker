
package com.olas.GetRichClicker.user;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserController 
{
	@Autowired
    private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model,
			@RequestParam(value = "registered", required = false) String registered,
			@RequestParam(value = "logoutSuccess", required = false) String logoutSuccess,
			@RequestParam(value = "error", required = false) String error)
	{
		if (registered != null) {model.addAttribute("registered", "Account registered successfully.");}
		if (logoutSuccess != null) {model.addAttribute("logoutSuccess", "You have successfully logged out.");}
		if (error != null) {model.addAttribute("error", "Username or password is incorrect.");}
		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Locale locale, Model model,
			@RequestParam(value = "registered", required = false) String registered)
	{
		model.addAttribute("userModel", new UserModel());
		if (registered != null) {model.addAttribute("registered", "Account already registered.");}
		return "register";
	}
	
	@RequestMapping(value = "/registered", method = RequestMethod.POST)
	public String registeredUser(@Valid UserModel userModel, BindingResult bindingResult, WebRequest request, Model model)
	{
		if (bindingResult.hasErrors()){ return "redirect:/register?registered"; }
		
		if( userService.getUserByUsername(userModel.getUsername()) == null )
		{
			userService.registerNewUser(userModel);
			return "redirect:/login?registered";
		}
		
		return "redirect:/register?registered";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Locale locale, Model model)
	{
		return "test";
	}
}
