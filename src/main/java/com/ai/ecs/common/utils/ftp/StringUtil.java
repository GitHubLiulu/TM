package com.ai.ecs.common.utils.ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.ecs.common.config.Global;
public class StringUtil {
	private static Log logger = LogFactory.getLog(StringUtil.class);
	/**
	 * 编码中文字符
	 */
	public static String parseGBKtoISO(String str) {
		String temp_p = str;
		String temp = null;
		try {
			byte[] temp_t = temp_p.getBytes("GBK");
			temp = new String(temp_t, "ISO8859-1");
		} catch (Exception e) {
			logger.error("getGBKStr", e);
		}
		return temp;
	}
    //转换符合LINUX系统的路径格式
	public static String getLinuxPath(String path) {
		if (path == null) {
			return null;
		}
		return path.trim().replaceAll("[\\\\]+", "/").replaceAll("/+", "/");
	}
	public static String getFileName(String file_name)
	{
		file_name = file_name.replaceAll("\\\\", "/");
		int index = file_name.lastIndexOf("/");
		return index != -1 ? file_name.substring(index + 1) : file_name;
	}

	public static String getFilePath(String file_name)
	{
		file_name = file_name.replaceAll("\\\\", "/");
		int index = file_name.lastIndexOf("/");
		return index != -1 ? file_name.substring(0, index) : null;
	}
	//获取当前项目的物理路径
	public static String getPath(){
		String file_path_begin = "";
		
	     return file_path_begin;
	}
	//新建一个按月的文件夹名
	public static String getFileData(){
		String datastr = null;
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datastr;
	}
  
	//获取FTP上传的最终路径，是后半部分
	public static String getFTPFilePath(String act_type,String dbFilePath){

		return null;
	}
	//获取FTP上传的最终路径，是后半部分
		public static String getFTPFilePathNoDate(String act_type,String dbFilePath){

			return null;
		}
		
    //替换服务器图片路径到静态服务器
		public static String change(String str){
	        String re=str.replaceAll("\r", "\\\r");
	        re=re.replaceAll("\t", "\\\t");
	        re=re.replaceAll("\n", "\\\n");
	        re=re.replaceAll("/eccm/userfiles/", Global.getUserResourcefilesBaseDir()+"userfiles/");
	        return re;
	    }

		public static void main(String[] args) {
			System.out.println(StringUtil.change("/eccm/userfiles/AHZ00293/images/photo/2016/07/5dd929225a14e6b0d235c448fff2fd29_b(1).jpg"));
		}
}
