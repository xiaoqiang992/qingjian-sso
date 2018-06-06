package com.qingjian.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingjian.common.pojo.TaotaoResult;
import com.qingjian.sso.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	
	@RequestMapping(value="/phone/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String phone, String password, 
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = loginService.login(phone, password, request, response);
		return result;
	}
	
	@RequestMapping(value="/auth/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult logins(String phone, String code, 
			HttpServletRequest request, HttpServletResponse response) {
		
		TaotaoResult results=loginService.logins(phone, code, request, response);
			
			return results;
		}
				
	
	
	@RequestMapping("/user/page/login")
	public String showRegister(String redirect, Model model) {
		//把url参数传递到jsp
		model.addAttribute("redirect", redirect);
		
		return "login";
	}
	
	  
}
