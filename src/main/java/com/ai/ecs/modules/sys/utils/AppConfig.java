package com.ai.ecs.modules.sys.utils;

import org.springframework.stereotype.Service;


@Service
public class AppConfig {
	
	/**   4A登录后回调验证地址*/
	public static final String URL4A = "http://4a.crm.gz.cmcc:8089/Venus4A/services/Venus4AService";
	
	/**   （测试）4A登录地址*/
	//public static final String LOGIN4A = "http://135.10.29.186:18095/Venus4A/LoginAction.do";
	
	/**   （正式）4A登录地址*/
	public static final String LOGIN4A = "https://www.tj.cmcc";
	
	
//	public static void main(String[] args) {
//		double result = (double) (1.64+56.38+5.6-0.71+30.1+17.27+1.57+0.14-20.39+7.27+1.14);
//		System.out.println(result+"");
//	}
}
