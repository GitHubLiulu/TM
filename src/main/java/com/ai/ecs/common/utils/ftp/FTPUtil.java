//package com.ai.ecs.common.utils.ftp;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.apache.log4j.Logger;
//
//import com.ai.ecs.common.config.Global;
//
//public class FTPUtil
//{
//  private static final Logger log = Logger.getLogger(FTPUtil.class);
//  protected FTPClient client;
//  private String ftpserver;
//  private static String ftpserverIPs;
//  private static int ftpport;
//  private static String ftpuser;
//  private static String ftppasswd;
//  private static String ftppath;
//  private static String opertype;
//  private static String homepath;
//  
//  
//  /**
//   * Creates a new instance of FTPUtil.
//   *
//   * @param opertype
//   * @throws Exception
//   */
//  public FTPUtil(String opertype)
//    throws Exception
//  {
//    initFTPInfo(opertype);
//  }
//  
//  public void initFTPInfo(String operty) {
//		opertype = operty;
//	    ftpserverIPs = Global.getConfig(opertype + ".server_ip");
//	    System.out.println(opertype + ".server_ip");
//	    System.out.println("文件上传主机server_id===========:" + this.ftpserver);
//	    ftpuser = Global.getConfig(opertype + ".user");
//	    ftppasswd = Global.getConfig(opertype + ".password");
//	    ftpport = Integer.valueOf(Global.getConfig(opertype + ".port")).intValue();
//	    homepath = Global.getConfig(opertype+".path")+"/"+DateUtils.toString(new Date(), "yyyyMM")+"/";
//	    ftppath = (Global.getConfig(new StringBuilder(String.valueOf(opertype)).append(".path").toString()) + "/");
//	    System.out.println("文件上传路径名称===========:" + ftppath);
//	}
//
//public void initFTPServer() throws Exception {
//	this.client = new FTPClient();
//	connectServer(this.ftpserver, this.ftpport, this.ftpuser, this.ftppasswd);
//    this.client.enterLocalPassiveMode();
//    this.client.setDataTimeout(60000);       //设置传输超时时间为60秒 
//    //this.client.setConnectTimeout(60000);       //连接超时为60秒
//}
//
//  public FTPUtil(String server, String user, String password)
//    throws Exception
//  {
//    this(server, 21, user, password, null);
//  }
//
//  public FTPUtil(String server, String user, String password, String path)
//    throws Exception
//  {
//    this(server, 21, user, password, path);
//  }
//
//  public FTPUtil(String server, int port, String user, String password, String path)
//    throws Exception
//  {
//    this.ftpserver = server;
//    this.ftpport = port;
//    this.ftpuser = user;
//    this.ftppasswd = password;
//    this.ftppath = path;
//    this.client = new FTPClient();
//    initFTPServer();
//  }
//
//  /**
//   * 
//   * createFTPDir:创建并切换FTP目录. <br/>
//   *
//   * @param ftpPath
//   * @return
// * @throws IOException 
//   * @since JDK 1.6
//   */
//  public static boolean createAndChangeFTPWorkSpace(FTPClient ftpClient, String ftpPath) throws IOException{
//	  String ftpWorkingPath = ftpClient.printWorkingDirectory() ;
//	  System.out.println("createAndChangeFTPWorkSpace ftpWorkingPath-->"+ftpWorkingPath);
//	  
//	  String fileTmp = ftpWorkingPath ;
//	  ftpPath = ftpPath.substring(ftpWorkingPath.length()) ;
//	  System.out.println("ftpPath-->"+ftpPath);
//	  String [] fileDirs = ftpPath.split("/") ;	 
//	  boolean FTPFlagMake = false ;
//	  boolean FTPFlagChangeDir = false ;
//	  for (int i = 0; i < fileDirs.length; i++) {
//		fileTmp += fileDirs[i] +"/";
//		FTPFlagMake = ftpClient.makeDirectory(fileTmp) ;
//		FTPFlagChangeDir = ftpClient.changeWorkingDirectory(fileTmp);
//		System.out.println("FTPFlagMake-->"+FTPFlagMake+";FTPFlagChangeDir-->"+FTPFlagChangeDir);
//		System.out.println(i+"-->"+fileDirs[i]);
//		System.out.println(i+"-->"+fileTmp);
//	  }
//	  return FTPFlagChangeDir ;
//  }
//  
//  protected void connectServer(String server, int port, String user, String password)
//    throws Exception
//  {
//    log.debug("ftp>connected to " + server + ".");
//		this.client.connect(server, port);
//    log.debug("ftp>connection reply : " + this.client.getReplyCode());
//    boolean loginrs = this.client.login(user, password);
//    if (loginrs)
//      log.debug("ftp>login successful.");
//    else
//      log.error("ftp>login incorrect,please check ftp user and password.");
//  }
//
//  public void setFileType(int fileType) throws Exception
//  {
//    log.debug("ftp>set " + (fileType != 0 ? "binary" : "assii") + " file type.");
//    this.client.setFileType(fileType);
//  }
//
//  public void closeServer() throws Exception {
//    if (this.client.isConnected()) {
//      this.client.disconnect();
//      log.debug("ftp>close " + this.ftpserver + "...");
//    }
//  }
//
//  public boolean changeDirectory(String path) throws Exception {
//    boolean result = this.client.changeWorkingDirectory(path);
//    if (result)
//      log.debug("ftp>change directory [" + path + "].");
//    else
//      log.error("ftp>change directory [" + path + "] failded.");
//    return result;
//  }
//
//  public boolean createDirectory(String path) throws Exception
//  {
//    boolean result = this.client.makeDirectory(path);
//    if (result)
//      log.debug("ftp>create directory [" + path + "].");
//    else
//      log.error("ftp>create directory [" + path + "] failded.");
//    
//    log.error("********--.[createDirectory] FTP workspace-->"+this.client.printWorkingDirectory());
//    return result;
//  }
//
//  public boolean removeDirectory(String path)
//    throws Exception
//  {
//    boolean result = this.client.removeDirectory(path);
//    if (result)
//      log.debug("ftp>remove directory [" + path + "].");
//    else
//      log.error("ftp>remove directory [" + path + "] failded.");
//    return result;
//  }
//
//  public boolean removeDirectory(String path, boolean isall) throws Exception {
//    if (!isall)
//      return removeDirectory(path);
//    FTPFile[] files = this.client.listFiles(path);
//    if ((files == null) || (files.length == 0))
//      return removeDirectory(path);
//    log.debug("ftp>remove directory [" + path + "] and sub directory.");
//    for (int i = 0; i < files.length; i++)
//    {
//      String name = files[i].getName();
//      if (files[i].isDirectory())
//      {
//        removeDirectory(path + "/" + name, true);
//      }
//      else if (files[i].isFile()) {
//        deleteFile(path + "/" + name);
//      }
//    }
//    return this.client.removeDirectory(path);
//  }
//
//  public List getFileList(String path) throws Exception {
//    FTPFile[] files = this.client.listFiles(path);
//    List list = new ArrayList();
//    if ((files == null) || (files.length == 0))
//      return list;
//    for (int i = 0; i < files.length; i++) {
//      if (files[i].isFile())
//        list.add(files[i].getName());
//    }
//    return list;
//  }
//
//  public boolean deleteFile(String filePath)
//    throws Exception
//  {
//    boolean result = this.client.deleteFile(filePath);
//    if (result)
//      log.debug("ftp>delete file [" + filePath + "].");
//    else
//      log.error("ftp>delete file [" + filePath + "] failded.");
//    return result;
//  }
//
//  public boolean uploadFile(String filePath,String changepath)
//    throws Exception
//  {
//    String fileName = FileMan.getFileName(filePath);
//    log.debug("ftp>ready upload file [" + filePath + "] to [" + fileName + "]...");
//    FileInputStream in = new FileInputStream(filePath);
//    return uploadFile(in, changepath,fileName);
//  }
//
// /* public boolean uploadFile(String localfilePath, String serverfileName)
//    throws Exception
//  {
//    log.debug("ftp>ready upload file [" + localfilePath + "] to [" + serverfileName + "]...");
//    FileInputStream in = new FileInputStream(localfilePath);
//    return uploadFile(in, serverfileName);
//  }*/
//
//  public boolean uploadFile(InputStream in, String changepath,String serverfileName) throws Exception {
//    boolean result = false;
//    String s = /*ftppath +"/" +*/changepath+ "/" + FileMan.getFilePath(serverfileName);
//    String w = ftppath + serverfileName;
//    String ftpPath = FileMan.getFilePath(StringUtil.getLinuxPath(s));
//    log.info("FTP 目标文件为：" + w);
//    log.info("FTP 目标目录为：" + ftpPath);
//    
//    log.error("********1. FTP workspace-->"+this.client.printWorkingDirectory());
//    boolean flag = this.client.changeWorkingDirectory(ftpPath);
//    log.error("********2. FTP workspace-->"+this.client.printWorkingDirectory());
//    log.info("FTP 目标目录切换状态：" + flag);
//    if (!flag) {
////      createDirectory(p);
////      boolean b = this.client.changeWorkingDirectory(p);
//    	ftpPath = StringUtil.getLinuxPath(ftpPath);
//    	System.out.println(ftpPath);
//    	boolean b =createAndChangeFTPWorkSpace(this.client, ftpPath) ;
//     
//      log.info("FTP 目标目录创建并切换状态：" + b);
//    }
//    log.error("********3. FTP workspace-->"+this.client.printWorkingDirectory());
//
//    try{
//      deleteFile(w);
//
//      String filename = FileMan.getFileName(serverfileName);
//      this.client.setBufferSize(1024);
//      this.client.setFileType(FTP.BINARY_FILE_TYPE);
//      result = this.client.storeFile(filename, in);
//      log.info("FTP storeFile 状态:" + result + ";filename:" + filename);
//    }
//    catch (Exception e)
//    {
//      log.error(e);
//    }
//    finally
//    {
//      if (result)
//        log.debug("ftp>upload file to [" + serverfileName + "].");
//      else {
//        log.error("ftp>upload file to [" + serverfileName + "] failded.");
//      }
//
//      in.close();
//    }
//
//    return result;
//  }
//
//  public boolean downloadFile(String serverFilePath, String localFilePath)
//    throws Exception
//  {
//    log.debug("ftp>ready download file [" + serverFilePath + "] to [" + localFilePath + "]...");
//    File file = new File(localFilePath);
//    return downloadFile(serverFilePath, new FileOutputStream(file));
//  }
//
//  public boolean downloadFile(String serverFilePath, OutputStream out)
//    throws Exception
//  {
//    boolean result = false;
//    try
//    {
//      result = this.client.retrieveFile(serverFilePath, out);
//    }
//    catch (Exception e)
//    {
//      log.error(e);
//    }
//    finally
//    {
//      if (result)
//        log.debug("ftp>download file from [" + serverFilePath + "].");
//      else {
//        log.error("ftp>download file from [" + serverFilePath + "] failded.");
//      }
//      out.close();
//    }
//
//    return result;
//  }
//
//  public InputStream getFileStream(String filePath)
//    throws Exception
//  {
//    InputStream in = null;
//    try
//    {
//      in = this.client.retrieveFileStream(filePath);
//    }
//    catch (Exception e)
//    {
//      log.error("");
//    }
//    finally
//    {
//    }
//
//    if (in != null)
//      log.debug("ftp>download file stream from [" + filePath + "].");
//    else {
//      log.error("ftp>download file stream from [" + filePath + "] failded.");
//    }
//
//    return in;
//  }
//
//  public OutputStream storeFileStream(String filePath)
//    throws Exception
//  {
//    log.debug("ftp>store file stream from [" + filePath + "].");
//    return this.client.storeFileStream(filePath);
//  }
//
///**
// * 
// * @deprecated
// * 上传一台服务器
// * 上传内容时调用，
// * 使用时修改{ecsite.properties}配置文件.
// * ftpFiles:上传文件. <br/>
// *
// * @since JDK 1.6
// */
////public static void ftpFiles(String filePathAndName) {
////	try {
////      FTPUtil ftp = new FTPUtil("mall.ecop.data.ftps"); //@ ecsite.properties FTP config prefix
//////System.out.println("homepath-->"+ftp.homepath);
////      ftp.uploadFile(filePathAndName, ftp.homepath);
////      ftp.closeServer(); 
////    }
////    catch (Exception e)
////    {
////      e.printStackTrace();
////    }
////}
///**
// * 
// * 上传多台台服务器
// * 上传内容时调用，
// * 使用时修改{ecsite.properties}配置文件.
// * ftpFiles:上传文件. <br/>
// *
// * @since JDK 1.6
// */
//public static void ftpFiles(String filePathAndName) {
//	try {
//		FTPUtil ftp = new FTPUtil("mall.ecop.data.ftp"); //@ ecsite.properties FTP config prefix
//		String serverIPs = FTPUtil.ftpserverIPs ;
//		
////		System.out.println(serverIPs);
//		String serverIPList[] = null ;
//		boolean loopFlag = false ;
//		if(null!=serverIPs && FTPUtil.ftpserverIPs.indexOf(";")!=-1) {
//			loopFlag = true ;
//			serverIPList = serverIPs.split(";") ;
//		} else {
//			ftp.ftpserver = serverIPs ;
//		}
//		if(loopFlag){
//			for (int i = 0; loopFlag && i < serverIPList.length; i++) {
//				ftp.ftpserver = serverIPList[i] ;
//				System.out.println(i+"-->"+serverIPList[i]);
//				ftpUploadAndClose(filePathAndName, ftp); 
//			}
//		} else {
//			System.out.println("0->"+"-->"+ftp.ftpserver);
//			ftpUploadAndClose(filePathAndName, ftp); 
//		}
//		
//	}
//	catch (Exception e)
//	{
//		e.printStackTrace();
//	}
//}
//
//
///**
// * 
// * 上传多台台服务器
// * 上传内容时调用，
// * 使用时修改{ecsite.properties}配置文件.上传到文件到指定目录
// * ftpFiles:上传文件. <br/>
// * flag : true ,上传到指定路径
// * @since JDK 1.6
// */
//public static void ftpFiles(String filePathAndName, String ftpPath) {
//	try {
//		FTPUtil ftp = new FTPUtil("mall.ecop.data.ftp"); //@ ecsite.properties FTP config prefix
//		String serverIPs = FTPUtil.ftpserverIPs ;
//		
//		if(null != ftpPath && !"".equals(ftpPath)) {
//			ftp.homepath = ftpPath ;
//		}
////		System.out.println(serverIPs);
//		String serverIPList[] = null ;
//		boolean loopFlag = false ;
//		if(null!=serverIPs && FTPUtil.ftpserverIPs.indexOf(";")!=-1) {
//			loopFlag = true ;
//			serverIPList = serverIPs.split(";") ;
//		} else {
//			ftp.ftpserver = serverIPs ;
//		}
//		if(loopFlag){
//			for (int i = 0; loopFlag && i < serverIPList.length; i++) {
//				ftp.ftpserver = serverIPList[i] ;
//				System.out.println(i+"-->"+serverIPList[i]);
//				ftpUploadAndClose(filePathAndName, ftp); 
//			}
//		} else {
//			System.out.println("0->"+"-->"+ftp.ftpserver);
//			ftpUploadAndClose(filePathAndName, ftp); 
//		}
//		
//	}
//	catch (Exception e)
//	{
//		e.printStackTrace();
//	}
//}
//
//
//public static void ftpUploadAndClose(String filePathAndName, FTPUtil ftp) throws Exception {
//	try {
//		ftp.initFTPServer() ;
//		ftp.uploadFile(filePathAndName, ftp.homepath);
//	} catch (Exception e) {
//		log.debug("[FTP ERRORS]-->FTP ConnectException【"+ftp.ftpserver+":"+ftpport+"】-user->【"+ftp.ftpuser+"】");
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} finally {
//		ftp.closeServer();
//	}
//}
//
//public static void main(String[] args) {
//
//	String files = "E:\\Apress.Big.Data.Analytics.with.Spark.pdf" ;  //上传文件在服务器根路径
//    ftpFiles(files);
//  }
//}