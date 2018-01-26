package com.ai.ecs.common.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLUtils {
	public static Map getDataFromXml(String fileName){
		  Element element = null;
		  File f = new File(fileName);//
		  // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		  DocumentBuilder db = null;
		  DocumentBuilderFactory dbf = null;
		  Map returnData = new HashMap();
		  try {
		   // 返回documentBuilderFactory对象
		   dbf = DocumentBuilderFactory.newInstance();
		   // 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
		   db = dbf.newDocumentBuilder();
		   // 得到一个DOM并返回给document对象
		   Document dt = db.parse(f);
		   // 得到一个elment根元素
		   element = dt.getDocumentElement();
		   // 获得根元素下的子节点
		   NodeList childNodes = element.getChildNodes();
		   // 遍历这些子节点
		   for (int i = 0; i < childNodes.getLength(); i++) {
		    // 获得每个对应位置i的结点
		    Node node1 = childNodes.item(i);
		    returnData.put(node1.getNodeName(), node1.getTextContent());
		   }
		  }
		   catch (Exception e) {
		   e.printStackTrace();
		  }
		  return returnData;
	}
}
