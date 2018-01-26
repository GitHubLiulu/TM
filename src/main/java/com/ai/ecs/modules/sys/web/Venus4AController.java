package com.ai.ecs.modules.sys.web;

import java.io.IOException;
import java.io.StringBufferInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ai.ecs.common.utils.StringUtils;
import com.ai.ecs.common.web.BaseController;
import com.ai.ecs.modules.sys.entity.User;
import com.ai.ecs.modules.sys.security.UsernamePasswordToken;
import com.ai.ecs.modules.sys.utils.AppConfig;
import com.ai.ecs.modules.sys.utils.UserUtils;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

@Controller
public class Venus4AController extends BaseController {

	@Autowired
	AppConfig appConfig;

	/**
	 * 
	 * venu:登录，并往Ehcache缓存中存【user】数据. <br/>
	 * @param request
	 * @param response
	 * @return
	 * @since JDK 1.6
	 */
	public boolean venu(HttpServletRequest request, HttpServletResponse response) {
		boolean ven = false;

		setToken(request);// 重置session
		ven = true;
		
		User user = UserUtils.getUser();
		//setUser to cache
		String uId = (String) request.getAttribute("uId"); //经分数据
		String ssoinfo = (String) request.getAttribute("ssoinfo"); //4A加密串，跳转到外部系统菜单需要带上
		user.setuId(uId);
		user.setSsoinfo(ssoinfo);
		UserUtils.setUser2Cache(user) ; //存User信息到缓存
		return ven;
	}

	/** 请求地址转发 */
	public void sendReUrl(HttpServletResponse response) {
		try {
			response.sendRedirect(appConfig.LOGIN4A);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 系统session重置
	 * 
	 * @param request
	 */
	public void setToken(HttpServletRequest request) {
		boolean rememberMe = false;
		String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
		String captcha = null;
		boolean mobile = false;

		String username =  (String)request.getAttribute("userName");//根据请求的userName来查询数据库
		String password = "admin";// 系统默认密码，对应的加密串：02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032

		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host,
				captcha, mobile);
		// token.setRememberMe(true);
		subject.login(token);
	}

	/**
	 * @deprecated TJ不需要回调
	 * 4A回调验证;
	 * @param resID
	 * @param userName
	 * @param signature
	 * @return 验证成功4A返回1，验证不成功4A返回0
	 */
	public String verify4A(String resID, String userName, String signature) {
		try {
			Service service = new org.apache.axis.client.Service();
			Call call = (Call) service.createCall();
			java.net.URL url = new java.net.URL(appConfig.URL4A);
			call.setTargetEndpointAddress(url);
			call.setOperationName("userSecondarySignatureLogin");
			Object[] param = { Integer.valueOf(resID).intValue(), userName, signature };

			String result = (String) call.invoke(param); // 4A验证
			System.out.println(result);

			Document doc = null;
			SAXReader reader = new SAXReader();
			doc = reader.read(new StringBufferInputStream(result));
			String code = doc.getRootElement().elementTextTrim("Result");
			String sessionId = doc.getRootElement().elementTextTrim("SessionId");
			
			System.out.println("4A验证-------------"+sessionId);
			return code.equals("1") ? "pass" : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Venus4AController test = new Venus4AController();
		test.verify4A("", "", "");

	}

}
