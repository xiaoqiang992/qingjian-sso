package com.qingjian.sso.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingjian.sso.utils.SMSUtil;

@Controller
public class PhoneController {

	@RequestMapping("/send")
	@ResponseBody
	public static String sendSms(@RequestParam("phone") String phone, HttpServletRequest request)
			throws Exception {
		String vcode = "";
		//正则表达式判断手机号
		// String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
		//产生四位随机数
		    vcode=(int)(Math.random()*8999+1000)+"";
			SMSUtil.sendSms(phone, "短信头", "模板号", vcode);
			HttpSession session = request.getSession();
			session.setAttribute("code", vcode);
			session.setAttribute("phone", phone);
			// 缓存设置10分钟
			session.setMaxInactiveInterval(60 * 10);
			return vcode;
	}

}
