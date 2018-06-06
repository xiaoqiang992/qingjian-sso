package com.qingjian.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qingjian.common.pojo.TaotaoResult;
import com.qingjian.pojo.QjMember;

public interface MoblieRegisService {
	
	TaotaoResult checkInfo(String data);
	//TaotaoResult register(QjMember member);
	TaotaoResult registers(String phone, String password,String code, HttpServletRequest request, HttpServletResponse response);


}
