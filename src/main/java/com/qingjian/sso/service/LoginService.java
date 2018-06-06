package com.qingjian.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qingjian.common.pojo.TaotaoResult;

public interface LoginService {
	
	TaotaoResult login(String phone, String password, HttpServletRequest request, HttpServletResponse response);
	TaotaoResult logins(String phone, String code, HttpServletRequest request, HttpServletResponse response);
	

}
