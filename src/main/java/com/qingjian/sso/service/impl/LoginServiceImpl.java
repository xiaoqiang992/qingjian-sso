package com.qingjian.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.qingjian.common.pojo.TaotaoResult;
import com.qingjian.common.utils.CookieUtils;
import com.qingjian.common.utils.JsonUtils;
import com.qingjian.mapper.QjMemberMapper;
import com.qingjian.pojo.QjMember;
import com.qingjian.pojo.QjMemberExample;
import com.qingjian.sso.dao.JedisClient;
import com.qingjian.sso.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private QjMemberMapper memberMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${REDIS_SESSION_EXPIRE}")
	private Integer REDIS_SESSION_EXPIRE;

private static String TT_TOKEN="TT_TOKEN";
	
	@Override
	public TaotaoResult login(String phone, String password, HttpServletRequest request, HttpServletResponse response) {
		
		//有效性验证
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
			return TaotaoResult.build(400, "手机号和密码不能为空");
		}
		QjMemberExample example = new QjMemberExample();
		QjMemberExample.Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<QjMember> list = memberMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return TaotaoResult.build(400, "手机号错误");
		}
		//判断密码是否正确
		QjMember member = list.get(0);
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(member.getPassword())) {
			return TaotaoResult.build(400, "手机号或密码错误");
		}
		//生成token
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString();
		//把用户信息写入redis
		//把用户的密码清空，为了安全
		member.setPassword(null);
		jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(member));
		jedisClient.expire(REDIS_SESSION_KEY + ":" + token, REDIS_SESSION_EXPIRE);
		//把token写入cookie
		CookieUtils.setCookie(request, response, TT_TOKEN, token);
		//返回token
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult logins(String phone, String code, HttpServletRequest request, HttpServletResponse response) {
		//有效性验证
				if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
					return TaotaoResult.build(400, "手机号和验证码不能为空");

				}
				QjMemberExample example = new QjMemberExample();
				QjMemberExample.Criteria criteria = example.createCriteria();
				criteria.andPhoneEqualTo(phone);
				List<QjMember> list = memberMapper.selectByExample(example);
				if (list == null || list.isEmpty()) {
					return TaotaoResult.build(400, "手机号未注册");
				}
				//判断验证码码是否正确
				HttpSession  session = request.getSession();//设置session
				String sessioncode =(String) session.getAttribute("code");
				String sessionphone =(String) session.getAttribute("userphone");
				//比对缓存
				if((code).equals(sessioncode)){
					
					//生成token
					UUID uuid = UUID.randomUUID();
					String token = uuid.toString();
					//把用户信息写入redis
					//把用户的密码清空，为了安全
					jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(code));
					jedisClient.expire(REDIS_SESSION_KEY + ":" + token, REDIS_SESSION_EXPIRE);
					//把token写入cookie
					CookieUtils.setCookie(request, response, TT_TOKEN, token);
					//返回token
					return TaotaoResult.ok(token);
					
				}
				
		             return TaotaoResult.build(400,"验证码错误");
	}


}
