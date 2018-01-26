//package com.ai.ecs.common.utils.ftp;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.ai.ecs.common.config.Global;
//
//public class FTPMan {
//	private static Log log = LogFactory.getLog(FTPMan.class);
//	public static final String MALL_FTP_TYPE_SFTP="sftp";
//	public static final String MALL_FTP_TYPE_FTP="ftp";
//	String ftp_type = "";
//	private String perpkeys = "";
//	public FTPMan(String perpkey){
//		
//		perpkeys = getFtpPerpkey(perpkey);
//		ftp_type = Global.getConfig(perpkeys+".type");
//		System.out.println("FTPMan=========perpkeys:"+perpkeys);
//		System.out.println("FTPMan=========ftp_type:"+ftp_type);
//		
//	}
//	public String getFtpPerpkey(String typeNum){
//		String Perpkey = "";
//		if("1".equals(typeNum)){
//			Perpkey = "mall.ecop.data.ftp";
//		}else if("2".equals(typeNum)){
//			Perpkey = "mall.ecop.data.sftp";
//		}else{
//			Perpkey = typeNum;
//		}
//		return Perpkey;
//	}
//	public boolean putFile(List<Map> filelist){
//		
//	    if(MALL_FTP_TYPE_FTP.equals(ftp_type)){
//	    	ftpFile(filelist);
//		}else if(MALL_FTP_TYPE_SFTP.equals(ftp_type)){
//			sftpFile(filelist);
//		}else{
//			log.error("没能正确匹配上合适的FTP传送方式，请检查FTP配置是否正确！");
//		}
//		return false;
//	}
//	
//	protected boolean ftpFile(List<Map> filelist){
//		int i = 0;
//		FTPUtil ftp = null;
//		try {
//		 ftp = new FTPUtil(perpkeys);
//			for(Map<String, String> filemap:filelist){
//				String localfilepath = String.valueOf(filemap.get("path"));
//				String changepath = String.valueOf(filemap.get("changepath"));
//				log.info("FTP上传文件路径："+localfilepath);
//					if(ftp.uploadFile(localfilepath,changepath)){
//						i++;
//					}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				ftp.closeServer();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if(filelist.size() != i){
//			log.error("FTP上传文件内容不完整，请检测！应上传文件数："+filelist.size()+" 实传文件数："+i);
//		}
//		log.info("本次FTP上传文件数为："+i);
//		return filelist.size() == i;
//	}
//	protected boolean sftpFile(List<Map> filelist){
//		int i = 0;
//		SFTPUtil sftp = null;
//		try {
//			sftp = new SFTPUtil(perpkeys);
//			for(Map<String,String> filemap:filelist){
//				String localfilepath = String.valueOf(filemap.get("path"));
//				log.info("SFTP上传文件路径："+localfilepath);
//				if(sftp.put(localfilepath)){
//					i++;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			sftp.closeconnect();
//		}
//		if(filelist.size() != i){
//			log.error("SFTP上传文件内容不完整，请检测！应上传文件数："+filelist.size()+" 实传文件数："+i);
//		}
//		log.info("本次FTP上传文件数为："+i);
//		return filelist.size() == i;
//	}
//	
//	public static void main(String[] args) {
//		List lis = new ArrayList<Map>();
//		Map mp = new HashMap();
//		mp.put("path", "G:\\workspace-indigo\\asiainfo-new\\qb_admin\\common\\com\\linkage\\qb\\admin\\rew\\PropertiesLoader.java");
//		lis.add(mp);
//		Map mp1 = new HashMap();
//		mp1.put("path", "G:\\workspace-indigo\\asiainfo-new\\qb_admin\\common\\com\\linkage\\qb\\admin\\rew\\FileServlet.java");
//		lis.add(mp1);
//		FTPMan fm = new FTPMan("mall.ecop.data.ftp");
//		fm.putFile(lis);
//	}
//
//
//}
