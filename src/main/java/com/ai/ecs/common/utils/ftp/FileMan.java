package com.ai.ecs.common.utils.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class FileMan {
	private static Log log = LogFactory.getLog(FileMan.class);
	public static String getMainFileName(String file_name)
	{
		if (file_name.lastIndexOf(".") == -1)
			return file_name;
		else
			return file_name.substring(0, file_name.lastIndexOf("."));
	}

	public static String getExpandFileName(String file_name)
	{
		if (file_name.lastIndexOf(".") == -1)
			return null;
		else
			return file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length());
	}

	public static String getFileType(String file_name)
	{
		if (file_name.lastIndexOf(".") == -1)
		{
			return null;
		} else
		{
			String file_type = file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length());
			return file_type.toUpperCase();
		}
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
		return index != -1 ? file_name.substring(0, index) : "/";
	}

	public static File[] getFileList(String path)
		throws Exception
	{
		File file = new File(path);
		return file.exists() ? file.listFiles() : null;
	}

	public static void deleteFiles(File file)
		throws Exception
	{
		if (file.exists())
		{
			if (file.isDirectory())
			{
				File fileList[] = file.listFiles();
				for (int i = 0; i < fileList.length; i++)
					deleteFiles(fileList[i]);

			} else
			{
				file.delete();
			}
			file.delete();
		}
	}
	
	public static boolean deleteFile(String full_name)
		throws Exception
	{
		File file = new File(full_name);
		if (file.exists())
			return file.delete();
		else
			return false;
	}

	public static boolean deleteFile(String file_path, String file_name)
		throws Exception
	{
		File file = new File(file_path, file_name);
		if (file.exists())
			return file.delete();
		else
			return false;
	}
	public static boolean createDirectory(String file_path)
		throws Exception
	{
		File dir = new File(file_path);
		if (!dir.exists() && !dir.isDirectory())
			return dir.mkdir();
		else
			return true;
	}

	public static boolean removeDirectory(String file_path, boolean isall)
		throws Exception
	{
		File file = new File(file_path);
		if (!file.exists())
			return false;
		if (isall)
		{
			File fileList[] = file.listFiles();
			for (int i = 0; i < fileList.length; i++)
			{
				File fileItem = fileList[i];
				if (fileItem.isDirectory())
					removeDirectory(fileItem.getPath(), isall);
				else
					fileItem.delete();
			}

		}
		return file.delete();
	}
	
	public static File getFile(String full_name)
			throws Exception
		{
			File file = new File(full_name);
			if (!file.exists())
				log.error("文件 " + full_name + " 未找到!");
			return file;
		}
	
	public static void writeFile(List<String> listdatas ,String full_name)
			throws Exception{
		writeFile(listdatas,full_name,false);
		
	}
	public static void writeFile(List<String> listdatas ,String full_name,boolean isgn)
			throws Exception{
		createDirectory(getFilePath(full_name));
		File file = getFile(full_name);
		FileWriter fw = null;
		if(!isgn){
			fw = new FileWriter(file,true);
		}else{
			fw = new FileWriter(file);
		}
		for(String datavalue:listdatas){
			writeToOutput(datavalue,fw,false);
		 }
		}
	public static void writeFile(String datavalue , String full_name)
			throws Exception{
		writeFile(datavalue,full_name,false);
		}
	public static void writeFile(String datavalue , String full_name,boolean isgn)
			throws Exception
		{
			createDirectory(getFilePath(full_name));
			File file = getFile(full_name);
			FileWriter fw = null;
			if(!isgn){
				fw = new FileWriter(file,true);
			}else{
				fw =new FileWriter(file);
			}
			writeToOutput(datavalue,fw,false);
		}
	
	public static void writeToOutput(String datas ,FileWriter out,boolean iscolse)
			throws Exception {
		//BufferedWriter bw  = new BufferedWriter(out);
		out.write(datas);
		if(!iscolse){
			out.close();
		}
		
		}
	
	public static List readFileDataList(String filePath) throws Exception{
		return readFileDataList(filePath,"");
	}
	
	public static List readFileDataList(String filePath,String dg) throws Exception{
		String fils = readFile(filePath,dg);
		String [] fis = fils.split(dg);
		List alist = new ArrayList();
		for(int i = 0;i < fis.length;i++){
			alist.add(i,fis[i]);
		}
		return alist;
	}
	public static String readFileData(String filePath) throws Exception{
		return readFileData(filePath,"");
	}
	public static String readFileData(String filePath,String dg) throws Exception{
	    return readFile(filePath,dg);
	}	
	protected static String readFile(String filePath,String dg) throws Exception {
		File file =new File(filePath);
		BufferedReader buf = new BufferedReader(new FileReader(file));
		String textvalue = null;
		StringBuffer sb = new StringBuffer();
		while((textvalue = buf.readLine()) != null ){
			sb.append(textvalue+dg.trim());
		}
		buf.close();
		
		return sb.toString();
		
		}
	
	public static void writeInputToOutput(InputStream in, OutputStream out)
			throws Exception
		{
			writeInputToOutput(in, out, false);
		}

		public static void writeInputToOutput(InputStream in, OutputStream out, boolean ispersist)
			throws Exception
		{
			int bufferSize = 1024;
			byte buffer[] = new byte[bufferSize];
			for (int len = -1; (len = in.read(buffer)) != -1;)
			{
				out.write(buffer, 0, len);
				flush(out);
			}

			if (!ispersist)
			{
				in.close();
				out.close();
			}
		}
		
		
		public static void closeOut(FileWriter out)throws Exception{
				try{
					out.close();
				}
				catch (SocketException e) { }
			}
		
		private static void flush(OutputStream out)
				throws Exception
			{
		
				try
				{
					out.flush();
				}
				catch (SocketException e) { }
			}
		
		public static void main(String[] args) {
//			URL ss = FileMan.class.getClassLoader().getResource("");
//			String paths = ss.getPath();
//			paths = paths.substring(0, paths.lastIndexOf("/bin"));
//			System.out.println(paths);
			FileMan fm = new FileMan();
		    
			String filePath = "config/propertysname.txt";
			try {
				fm.readFile(filePath,",");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
