/**
 *  
 */
package com.ai.ecs.modules.sys.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.ecs.common.config.Global;
import com.ai.ecs.common.security.shiro.session.SessionDAO;
import com.ai.ecs.common.servlet.ValidateCodeServlet;
import com.ai.ecs.common.utils.CacheUtils;
import com.ai.ecs.common.utils.CookieUtils;
import com.ai.ecs.common.utils.IdGen;
import com.ai.ecs.common.utils.SSOTicketDESUtil;
import com.ai.ecs.common.utils.StringUtils;
import com.ai.ecs.common.web.BaseController;
import com.ai.ecs.modules.sys.security.FormAuthenticationFilter;
import com.ai.ecs.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.ai.ecs.modules.sys.utils.AppConfig;
import com.ai.ecs.modules.sys.utils.URLEnDecoder;
import com.ai.ecs.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;

/**
 * 登录Controller
 * @author Admin
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private Venus4AController venus4AControoler;
	@Autowired
	AppConfig appConfig ;
	
	private static final String SSO4ATicketDESKey="4a&crM#$Rm&4A4a&crM#$Rm&4A";  //4A Des key
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		
//		if(!venus4AControoler.venu(request,response)){//接收请求，并拦截，对不是在4A登录的，返回4A登录页面
//			try {
//				response.sendRedirect(appConfig.LOGIN4A);//4A登录页面
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				//需要一个容错页面，让用户跳转到4A从新登录
//			}
//		}
//		

		
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			//return "redirect:" + adminPath;
			System.out.println("进入渠道集中化管理页面跳转控制器.........");
			return "redirect:"+adminPath;
		}else{
//			===============================上线时需要放开
//			try {
//				response.sendRedirect(appConfig.LOGIN4A);//4A登录页面
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				//需要一个容错页面，让用户跳转到4A从新登录
//			}
		}
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/ecsite/src/main/webapp/WEB-INF/lib/ecsite.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
		String v = request.getParameter("login_eccm_cmc_iisc_srd_cd_dept");
		if("1".equals(v)){
			return "modules/sys/sysLogin";
		}
		
		//return "redirect:"+AppConfig.LOGIN4A; //跳转到对应的页面
		return "modules/sys/sysLogin";
		
		
	}
	
	
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "/login_eccm_cmc_iisc_srd_cd_dept", method = RequestMethod.GET)
	public String login_eccm_cmc_iisc_srd_cd_dept(HttpServletRequest request, HttpServletResponse response, Model model) {
		
	
		Principal principal = UserUtils.getPrincipal();


		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			//return "redirect:" + adminPath;
			System.out.println("进入渠道集中化管理页面跳转控制器.........");
			return "redirect:"+adminPath+"/index";
		}else{
//			===============================上线时需要放开
//			try {
//				response.sendRedirect(appConfig.LOGIN4A);//4A登录页面
//			} catch (IOException e) {
//				e.printStackTrace();
//				//需要一个容错页面，让用户跳转到4A从新登录
//			}
		}
		
		return "modules/sys/sysLogin";
	}
	
	
	@RequestMapping(value = "/tj/login", method = RequestMethod.GET)//正式对接的时候，修改为post
	public String login4A(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String ssoinfo = request.getParameter("ssoinfo");
		 if(ssoinfo == null || "".equals(ssoinfo)){
			 logger.error("无票据信息！");
			 rediret4ALogin(response);
			 return null ;
		 }
		 logger.debug("--------ssoinfo---------"+ssoinfo);
		 String ssoParam = SSOTicketDESUtil.decrypt(ssoinfo, SSO4ATicketDESKey);
//	     if(ssoParam == null || "".equals(ssoParam)){
//	    	 logger.error("解析票据信息出错！");
//	    	 rediret4ALogin(response);
//			 return null ;
//	     } 
	     Map<String,String> ssoParamMap = getURLParams(ssoParam);
		   
		   String ticket = ssoParamMap.get("ticket");
		   String staffId = ssoParamMap.get("staffid");
		   String uId = ssoParamMap.get("uid"); 
		   
		   if(ticket == null || "".equals(ticket)){
			   logger.error("票据信息中ticket为空！");
			   rediret4ALogin(response);
			   return null ;
		   }
		   
		   if(staffId == null || "".equals(staffId)){
			   logger.error("票据信息中staffid【CRM工号】为空！");
			   rediret4ALogin(response);
			   return null ;
		   }
		   
		   if(uId == null || "".equals(uId)){
			   logger.error("票据信息中uid【经分工号】为空！");
			   rediret4ALogin(response);
			   return null ;
		   }
		   
		   if(logger.isDebugEnabled()){
			   logger.debug("票据信息解析>> ticket:" + ticket + ",staffid:" + staffId + ",uid:" + uId);
		   }
		   
		   request.setAttribute("ticket", ticket);
		   request.setAttribute("staffId", staffId);
		   request.setAttribute("uId", uId);
		   request.setAttribute("userName", staffId);
		   request.setAttribute("ssoinfo", ssoinfo);
		   logger.info("----------------[4A登录校验通过;校验集中化平台信息并且设置登录session]----------------");
		
		if(!venus4AControoler.venu(request,response)){//接收请求，并拦截，对不是在4A登录的，返回4A登录页面
			 rediret4ALogin(response);
			 return null ;
//			return "error/500";
		}else{
			
			return "redirect:"+adminPath+"/index";
		}
		
	}
	
	
	

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath+"index";
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
		
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		
		
//		User user =	UserUtils.getUser();
//		System.out.println(user.getId()+":"+user.getLoginName());
//		Office off = user.getOffice();
//		off.getGrade();
//			System.out.println(off.getId()+":"+off.getName());
//			System.out.println(off.getParentId()+":"+off.getParentIds());
//			System.out.println(off.getProvinceOfficeCode()+":"+off.getCityOfficeCode()+":"+off.getCountiesOfficeCode()+":"+off.getAreaOfficeCode()+":"+off.getChannelOfficeCode()+":"+off.getTerminalOfficeCode());
//			Area area = off.getArea();
//			area.getType();
//			System.out.println(area.getId()+":"+area.getName());
//			System.out.println(area.getParentId()+":"+area.getParentIds());
//			System.out.println(area.getProvinceCode()+":"+area.getCityCode()+":"+area.getCountiesCode()+":"+area.getAreaCode()+":"+area.getTownCode()+":"+area.getVillageCode());
//		
//			System.out.println("rolegrade:"+user.getRoleGrade());
		
		
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}
		
//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.ai.ecs.common.utils.FileUtils.readFileToByteArray(
//					com.ai.ecs.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	private void rediret4ALogin(HttpServletResponse response) {
		try {
			response.sendRedirect(appConfig.LOGIN4A);//4A登录页面
			return ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//需要一个容错页面，让用户跳转到4A从新登录
		}
	}
	
	private static Map<String,String> getURLParams(String queryString){
		String[] queryStringSplit = queryString.split("&");
		Map<String,String> params = new HashMap<String, String>();

        for (String qs : queryStringSplit) {
        	if(qs!=null && !"".equals(qs)){
        		int idx = qs.indexOf("=");
        		if(idx > -1){
        			String name = qs.substring(0, idx);
        			String value = qs.substring(idx + 1, qs.length());

        			params.put(name, value);
        		}
        	}
        }
        return params;
	}
	
	/**
	 * 
	 * sysRedirect:
	 * 系统跳转到外部系统，带上4A加密参数. <br/>
	 * @author xiyang
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @since JDK 1.6
	 */
	@RequestMapping(value = "/sys/redirect", method = RequestMethod.GET)//正式对接的时候，修改为post
	public String sysRedirect(HttpServletRequest request, HttpServletResponse response, Model model) {
		String toURL = request.getParameter("toURL");
		//获取User中的ssoinfo信息
		String ssoinfo =  UserUtils.getUser().getSsoinfo() ;  //test
		
		if(ssoinfo == null || "".equals(ssoinfo)){
			 logger.error("经分ssoinfo出错....！");
			 rediret4ALogin(response); //重定向4A登录
			 return null ;
		 }
		toURL = URLEnDecoder.urlDecoder(toURL) ; // 解码后拼接链接
		if(toURL.indexOf("?")!=-1) {
			toURL += "&ssoinfo=" + ssoinfo ;
		} else {
			toURL += "?ssoinfo=" + ssoinfo ;
		}
		logger.info("--------toURL---------"+toURL);
		   
		return "redirect:"+toURL; //跳转到对应的页面
	}
	
	
}
