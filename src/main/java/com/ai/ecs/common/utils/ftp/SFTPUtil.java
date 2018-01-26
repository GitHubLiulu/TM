//package com.ai.ecs.common.utils.ftp;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Properties;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.log4j.Logger;
//
//import com.ai.ecs.common.config.Global;
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//
//public class SFTPUtil{
//	private static final Logger log = Logger.getLogger(SFTPUtil.class);
//	private String opertype;
//	private Session session;
//	private ChannelSftp sftp;
//
//	
//	private String homepath;
//	private String server;
//	private String port;
//	private String username;
//	private String password;
//	
//	public SFTPUtil(String opertype) throws Exception{
//	    this.opertype = opertype;
//	    this.server = Global.getConfig(opertype + ".server_ip");
//	    System.out.println(opertype + ".server_ip");
//	    System.out.println("文件上传主机server_id===========:" + this.server);
//		this.username = Global.getConfig(opertype+".user");
//		this.password = Global.getConfig(opertype+".password");
//		this.port = Global.getConfig(opertype+".port");
//		this.homepath = Global.getConfig(opertype+".path")+"/"+DateUtils.toString(new Date(), "yyyyMM")+"/";
//		this.session = this.getSession(server, username, Integer.parseInt(port)); //取得sftp连接会话
//		this.sftp    = this.login(session, server, username, password); //取得sftp客户端	
//	}
//	public boolean put(String localFilePath){
//		String fileName = FileMan.getFileName(localFilePath);
//		return put(localFilePath,fileName);
//	}
//	public boolean put(String localFilePath,String ftppath) {
//		File localFile = null;
//		FileInputStream fis = null;
//
//		boolean suc = false;
//
//		try {
//			localFile = new File(localFilePath);
//			if(localFile.isFile()){
//			fis = new FileInputStream(localFile);
//			String desAddr = StringUtil.getLinuxPath(homepath+"/"+ftppath);
//			String pp = FileMan.getFilePath(desAddr);
//			this.makeRemoteDir(pp);
//
//			sftp.cd(pp);
//			sftp.put(fis,desAddr);
//		 }
//		} catch (Exception e) {
//			this.closeFTP(sftp); 
//			this.closeFTP(session); 
//			log.error("Error put:" + server + "," + username + "," + homepath, e);
//			log.error("status:" + sftp.getExitStatus());
//		} finally {
//			if (fis != null) {
//				
//				IOUtils.closeQuietly(fis);
//			}
//		  suc = true;
//		}
//	   
//		return 	suc;
//	}
//	
//	private boolean get(String localFilePath,String srcFilePath) {
//		localFilePath = StringUtil.getLinuxPath(localFilePath);
//		File localFile = null;
//		FileOutputStream localFOS = null;
//		boolean suc = false;
//		try {
//			localFile = this.createLocalFile(localFilePath);
//			localFOS = new FileOutputStream(localFile);
//
//			String srcAddr = StringUtil.parseGBKtoISO(StringUtil.getLinuxPath(srcFilePath));
//
//			sftp.get(srcAddr, localFOS);
//			
//		} catch (Exception e) {
//			log.error("Error put:" + server + "," + username + "," + homepath, e);
//			log.error("status:" + sftp.getExitStatus());
//		} finally {
//			if (localFOS != null) {
//				IOUtils.closeQuietly(localFOS);
//			}
//		 suc = true;
//		}
//		return  suc;
//	}
//	private File createLocalFile(String fullPath) throws Exception {
//		String localDirPath = fullPath.substring(0, fullPath.lastIndexOf("/"));
//		File localDirFile = new File(localDirPath);
//		if (!localDirFile.exists()) {
//			localDirFile.mkdirs();
//		}
//		File localFile = new File(fullPath);
//		if (localFile.exists()) {
//			localFile.delete();
//		}
//		localFile.createNewFile();
//		return localFile;
//	}
//	private void makeRemoteDir(String path) throws IOException {
//	
//		try {
//			sftp.cd(path);	
//		} catch (SftpException e) {
//			try {			
//				sftp.mkdir(path);	
//			} catch (SftpException e1) {
//				this.closeFTP(sftp); 
//				this.closeFTP(session); 
//				log.error("makeremoteDir error: path = " + path, e);
//			}
//			
//		}
//	}	
//	public ChannelSftp login(Session sshSession, String ip, String account, String password) {
//		if(sshSession == null) {
//			return null;
//		}
//		try {
//			sshSession.setPassword(password);
//			Properties sshConfig = new Properties();
//			sshConfig.put("StrictHostKeyChecking", "no");
//			sshSession.setConfig(sshConfig);
//			sshSession.connect();
//			
//			Channel channel = sshSession.openChannel("sftp");
//			channel.connect();
//			sftp = (ChannelSftp) channel;
//		
//		} catch (JSchException e) {
//			log.error("failed to login:" + ip + ":" + port + "," + account + ",status:" + sftp.getExitStatus(), e);
//			this.closeFTP(sftp);
//			this.closeFTP(sshSession);
//			return null;
//		}
//		return sftp;
//	}
//
//	public Session getSession(String ip, String account, int port) {
//		try {
//			JSch jsch = new JSch();
//			jsch.getSession(account, ip, port);
//			return  jsch.getSession(account, ip, port);
//		} catch (JSchException e) {
//			log.error("failed to session:" + ip + ":" + port + "," + account, e);
//			return null;
//		}
//	}
//	public void closeFTP(ChannelSftp sftp) {
//		try {
//			if (sftp != null) {
//				sftp.exit();
//				sftp.disconnect();
//			}
//		} catch (Exception ex3) {
//			log.error("closeSFTP", ex3);
//		}
//	}
//
//	public void closeFTP(Session sshSession) {
//		try {
//			if (sshSession != null) {
//				sshSession.disconnect();
//			}
//		} catch (Exception ex3) {
//			log.error("closeSFTPSession", ex3);
//		}
//	}
//	
//	public void closeconnect(){
//		try {
//			if (sftp != null) {
//				sftp.exit();
//				sftp.disconnect();
//			}
//		} catch (Exception ex3) {
//			log.error("closeSFTP", ex3);
//		}
//		
//		try {
//			if (session != null) {
//				session.disconnect();
//			}
//		} catch (Exception ex3) {
//			log.error("closeSFTPSession", ex3);
//		}
//		
//	}
//
//	public void uploadFile(Object item, String file_path ,String file_name) throws Exception {		
//		if(sftp != null){
//			this.put(file_path+"/"+file_name,file_name); 
//		}	
//	}
//	public void ftpFile( String file_path,String ftp_path) throws Exception {
//		if(sftp != null){
//			
//			this.put(file_path,ftp_path); 
//		}
//		
//	}
//	
//	
//	public static void main(String[] args) {
//		SFTPUtil sftp;
//		try {
//			sftp = new SFTPUtil("mall.ecop.data.sftp");
//			sftp.put("E:\\Apress.Big.Data.Analytics.with.Spark.pdf");
//			//sftp.get("G:\\c\\UtilBean.java", "test111/UtilBean.java");
//			sftp.closeconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
//
//}
