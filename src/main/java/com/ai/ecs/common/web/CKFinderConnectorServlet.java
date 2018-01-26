/**
 *  
 */
package com.ai.ecs.common.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.ecs.common.config.Global;
import com.ai.ecs.common.utils.FileUtils;
//import com.ai.ecs.common.utils.ftp.FTPUtil;
import com.ai.ecs.common.utils.ftp.StringUtil;
import com.ai.ecs.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.ai.ecs.modules.sys.utils.UserUtils;
import com.ckfinder.connector.ConnectorServlet;

/**
 * CKFinderConnectorServlet
 * @author Admin
 * @version 2014-06-25
 */
public class CKFinderConnectorServlet extends ConnectorServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, false);
		super.doGet(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		prepareGetResponse(request, response, true);
		super.doPost(request, response);
	}
	
	private void prepareGetResponse(final HttpServletRequest request,
			final HttpServletResponse response, final boolean post) throws ServletException {
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			return;
		}
		String command = request.getParameter("command");
		String type = request.getParameter("type");
		
		// 初始化时，如果startupPath文件夹不存在，则自动创建startupPath文件夹
		if ("Init".equals(command)){
			String startupPath = request.getParameter("startupPath");// 当前文件夹可指定为模块名
			if (startupPath!=null){
				String[] ss = startupPath.split(":");
				if (ss.length==2){
					String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
							+ principal + "/" + ss[0] + ss[1];
					FileUtils.createDirectory(FileUtils.path(realPath));
				}
			}
		}
		// 快捷上传，自动创建当前文件夹，并上传到该路径
		else if ("QuickUpload".equals(command) && type!=null){
			String currentFolder = request.getParameter("currentFolder");// 当前文件夹可指定为模块名
			String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
					+ principal + "/" + type + (currentFolder != null ? currentFolder : "");
			FileUtils.createDirectory(FileUtils.path(realPath));
		}
//		System.out.println("------------------------");
//		for (Object key : request.getParameterMap().keySet()){
//			System.out.println(key + ": " + request.getParameter(key.toString()));
//		}
		
		if(false) { //判断是否是对应的串，FTP文件到静态服务器  //    由于 本机无FTP环境 ，暂时注释掉判定代码以后上线可放开 -->   "Thumbnail".equals(command) && null!=type && "images".equals(type)
			System.out.println("-----------------1. CKFinderConnectorServlet getFTPFileUploadPath start --------------------");
			Map<Object,String> ftpMap = getFTPFileUploadPath(request);
			String currentFolder = ftpMap.get("currentFolder") ;
			String FileName = ftpMap.get("FileName") ;
			String fileHash = ftpMap.get("fileHash") ;
			if(null!=fileHash && !"".equals(fileHash) 
				&& null!=currentFolder && !"".equals(currentFolder)
				&& null!=FileName && !"".equals(FileName)){
			
				//---FTP to Static Server.
				
				String userId = UserUtils.getUser().getId();
				String ftpDesPath = Global.getConfig("mall.ecop.data.ftp.path")+"/userfiles/"+userId+"/images"+ currentFolder ;
				String ftpLocalFile = Global.getConfig("userfiles.basedir") +"/userfiles/"+userId+"/images"+ currentFolder + FileName ;
		System.out.println("ftpLocalServerFile Path-->"+ftpLocalFile);
				ftpLocalFile = StringUtil.getLinuxPath(ftpLocalFile) ;
		System.out.println("FTP UPload Path-->"+ftpLocalFile);
				//FTPUtil.ftpFiles(ftpLocalFile, ftpDesPath); // FTP传递数据到Linux服务器@20160703
			}
			System.out.println("-----------------2. CKFinderConnectorServlet getFTPFileUploadPath end   --------------------");
		}
		
	}

	/**
	 * 
	 * getFTPFileUploadPath: 解析CKFinderConnectorServlet，通过FTP上传图片. <br/>
	 * --------------------格式--------------------
	 * 
	 * step1:----
command: Init
startupPath: images:/content/tcChlContent/2016/07/
type: images
------------------------
command: GetFolders
type: images
currentFolder: /
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
------------------------
command: GetFolders
type: images
currentFolder: /content/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
------------------------
command: GetFolders
type: images
currentFolder: /content/tcChlContent/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
------------------------
command: GetFolders
type: images
currentFolder: /content/tcChlContent/2016/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
------------------------
command: GetFiles
type: images
currentFolder: /content/tcChlContent/2016/07/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/

step2:----
------------------------
command: FileUpload
type: images
currentFolder: /content/tcChlContent/2016/07/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
response_type: txt
------------------------
command: GetFiles
type: images
currentFolder: /content/tcChlContent/2016/07/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
------------------------
command: Thumbnail
type: images
currentFolder: /content/tcChlContent/2016/07/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
FileName: health.jpg
fileHash: 201607071948-31


step3:----
command: Thumbnail
type: images
currentFolder: /content/tcChlContent/2016/07/
langCode: zh-cn
hash: 823dfc61d18925c1d2af759c8f8b182a
startupPath: images:/content/tcChlContent/2016/07/
FileName: health.jpg
	 * @param request
	 * @return
	 * @since JDK 1.6
	 */
	public Map<Object,String> getFTPFileUploadPath(final HttpServletRequest request) {
		Map<Object,String> mapList = new HashMap<Object, String>() ;
		for (Object key : request.getParameterMap().keySet()){
			String val = request.getParameter(key.toString()) ;
			if("command".equals(key) || "images".equals(key) 
					|| "currentFolder".equals(key) 
					|| "FileName".equals(key)
					|| "fileHash".equals(key)) {
				mapList.put(key, val) ;
			}
			System.out.println(key + ": " + val);
		}
		System.out.println("------[getFTPFileUploadPath mapList]------>"+mapList);
		return mapList ;
	}
	
	public static void main(String[] args) {
		Map<Object,Object> mapList = new HashMap<Object, Object>() ;
		mapList.put("command", "Thumbnail") ;
		mapList.put("command", "Thumbnail") ;
		
		System.out.println(mapList.get("currentFolder")) ; //null
	}
}
