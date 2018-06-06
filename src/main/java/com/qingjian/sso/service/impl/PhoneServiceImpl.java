package com.qingjian.sso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qingjian.common.pojo.TaotaoResult;
import com.qingjian.mapper.QjMemberMapper;
import com.qingjian.pojo.QjMember;
import com.qingjian.pojo.QjMemberExample;
import com.qingjian.sso.service.Phoneservice;

@Service
public class PhoneServiceImpl implements Phoneservice {
	
	@Autowired
	private QjMemberMapper memberMapper;


	@Override
	public TaotaoResult checkInfo(String phone) {
		QjMemberExample example = new QjMemberExample();
		QjMemberExample.Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<QjMember> list = memberMapper.selectByExample(example);
		
		//没查到可以使用
		if (list == null || list.isEmpty()) {
			return TaotaoResult.ok(true); 
		}
		
		return TaotaoResult.build(400, "手机号已存在");
	}
	
	

}
