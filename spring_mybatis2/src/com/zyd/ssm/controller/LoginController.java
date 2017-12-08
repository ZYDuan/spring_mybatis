package com.zyd.ssm.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("login")
	public String login(HttpSession session, String username, String password)throws Exception{
		
		session.setAttribute("username", username);
		return "redirect:/items/queryItems.action";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session)throws Exception{
		session.invalidate();
		return "redirect:/items/queryItems.action";
	}
}
