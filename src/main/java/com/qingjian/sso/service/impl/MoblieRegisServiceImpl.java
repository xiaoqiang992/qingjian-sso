package com.qingjian.sso.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.qingjian.common.pojo.TaotaoResult;
import com.qingjian.mapper.QjMemberMapper;
import com.qingjian.pojo.QjMember;
import com.qingjian.pojo.QjMemberExample;
import com.qingjian.sso.service.MoblieRegisService;

@Service
public class MoblieRegisServiceImpl implements MoblieRegisService{
	
	@Autowired
	private QjMemberMapper memberMapper;

	@Override
	public TaotaoResult checkInfo(String data) {
		QjMemberExample example = new QjMemberExample();
		QjMemberExample.Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(data);
		List<QjMember> list = memberMapper.selectByExample(example);
		
		//没查到可以使用
		if (list == null || list.isEmpty()) {
			return TaotaoResult.ok(true); 
		}
		return TaotaoResult.build(400, "手机号已存在");
	}
	
	

/*	
 * @Override
	public TaotaoResult register(QjMember member) {
		      //有效性验证
				if (StringUtils.isBlank(member.getPhone())) {
					return TaotaoResult.build(400, "手机号不能为空");
				}
				
				if (StringUtils.isBlank(member.getPassword())) {
					return TaotaoResult.build(400, "密码不能为空");
				}
				//密码进行md5加密
				String password = DigestUtils.md5DigestAsHex(member.getPassword().getBytes());
				member.setPassword(password);
				
				//补全member对象中的信息
				String chars = "abcdefghijklmnopqrstuvwxyz";
				 member.setUsername("ql_"+member.getPhone());
				member.setState(1);
				member.setCreated(new Date());
				member.setUpdated(new Date());
				//把会员信息插入到数据库
				memberMapper.insert(member);
				
				return TaotaoResult.ok();
	}
*/


	@Override
	public TaotaoResult registers(String phone, String password,String code, HttpServletRequest request, HttpServletResponse response) {
		
		if (StringUtils.isBlank(phone) ) {
			return TaotaoResult.build(400, "手机号不能为空");
		}
		if(StringUtils.isBlank(password)) {
			return TaotaoResult.build(400, "密码不能为空");
		}
		if(StringUtils.isBlank(code)) {
			return TaotaoResult.build(400, "验证码不能为空");
		}
		
		QjMemberExample example = new QjMemberExample();
		QjMemberExample.Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<QjMember>list=memberMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			
			//判断验证码码是否正确
			HttpSession  session = request.getSession();//设置session
			String sessioncode =(String) session.getAttribute("code");
			String sessionphone =(String) session.getAttribute("phone");
			//比对缓存
			if((code).equals(sessioncode)&&(phone).equals(sessionphone)){
				
			QjMember member=new QjMember();
			//密码进行md5加密
			String passwords = DigestUtils.md5DigestAsHex(password.getBytes());
			member.setPassword(passwords);
			//补全member对象中的信息
			 member.setUsername("ql_"+phone);
			 member.setPhone(phone);
			member.setState(1);
			member.setCreated(new Date());
			member.setUpdated(new Date());
			//把会员信息插入到数据库
			memberMapper.insert(member);
			
			return TaotaoResult.ok();
			}
			
			return TaotaoResult.build(400, "验证码错误");
		}
		
		return TaotaoResult.build(400, "手机号已被注册");
	}
	
	

}
