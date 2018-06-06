package com.qingjian.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestJedis {
	
	@Test  
    public void testJedis(){  
        //创建jedis对象，需要指定Redis服务的IP和端口号  
        Jedis jedis = new Jedis("192.168.238.13",6379);  
        //直接操作数据库  
        jedis.set("jedis-key", "hello jedis!");  
        //获取数据  
        String result = jedis.get("jedis-key");  
        System.out.println(result);  
        //关闭jedis  
        jedis.close();  
    }  

}
