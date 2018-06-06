package com.qingjian.sso.service;

import com.qingjian.common.pojo.TaotaoResult;

public interface TokenService {
	
	TaotaoResult getUserByToken(String token);

}
