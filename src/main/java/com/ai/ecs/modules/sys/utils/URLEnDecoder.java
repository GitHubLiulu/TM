/**
 *  
 */
package com.ai.ecs.modules.sys.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 加密解密URL,解决参数无法再request中获取的问题
 * 加密后存放到数据库
 * @author Admin
 * @version 2013-5-29
 */
public class URLEnDecoder {
	
	public static String urlDecoder(String urlEncoder) {
		String urlDe = URLDecoder.decode(urlEncoder) ;
		return urlDe;
	}

	public static String urlEncoder(String url) {
		String urlEncoder = URLEncoder.encode(url) ;
		return urlEncoder;
	}
	public static void main(String[] args) {
		String methodPrefix = "/../../eccm/sys/redirect?toURL=" ;
		String url = "http://cmplat.tj.cmcc/qms/rms/login.do?method=login&function=fwgl&sessiongid=00001"; //toURL outer menu 
		String urlEncoder = urlEncoder(url);
		System.err.println("Copy to DataBase:"+methodPrefix+urlEncoder);
		
		String urlDe = urlDecoder(urlEncoder);
		System.out.println(methodPrefix+urlDe);
	}
}
