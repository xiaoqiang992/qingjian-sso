package com.qingjian.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingjian.common.pojo.TaotaoResult;
import com.qingjian.common.utils.ExceptionUtil;
import com.qingjian.pojo.QjMember;
import com.qingjian.sso.service.MoblieRegisService;
import com.qingjian.sso.service.impl.MoblieRegisServiceImpl;

@Controller
public class MobileRegisController {
	
	@Autowired
	private MoblieRegisService regisService;
	
	
	@RequestMapping(value="/user/check/{param}/{type}", method=RequestMethod.GET)
	@ResponseBody
	public Object checkInfo(@PathVariable String param, @PathVariable Integer type,
			String callback) {
		TaotaoResult taotaoResult = regisService.checkInfo(param);
		if (null == callback) {
			return taotaoResult;
		}
		//需要支持jsonp
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
	
	/*@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(QjMember member) {
		try {
			return regisService.register(member);
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}*/
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult registers(String phone,String password,String code,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			return regisService.registers(phone,password,code,request,response);
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	
	@RequestMapping("/page/register")
	public String showRegister() {
		
		return "register";
		
	}

}
