/**
 * Project Name:eccm_new
 * File Name:JinFen2CRMCitysTJ.java
 * Package Name:com.ai.ecs.common.utils
 * Date:2016年7月2日上午9:16:54
 * Copyright (c) 2016, All Rights Reserved.
 *
*/

package com.ai.ecs.common.utils;

import java.util.HashMap;
import java.util.Map;


/**
 * ClassName:JinFen2CRMCitysTJ <br/>
 * Function: TODO 经分地市转换为CRM地市的Key_Val关系. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年7月2日 上午9:16:54 <br/>
 * @author   xiyang
 * @version  v1.0
 * @since    JDK 1.6
 * @see 	 
 */
public class JingFen2CRMCitysTJ {
	
	//经分关系
	public static final String JINGFEN_CITY_KEYS = "028,0834" ;
	public static final String JINGFEN_CITY_VALS = "成都,绵阳" ;
	
	//CRM关系
	public static final String CRM_CITY_KEYS = "001,002" ;
	public static final String CRM_CITY_VALS = "天津,经开区" ;
	
	//经分-CRM[key]映射关系
	public static final String JINGFEN_KEYS_RELATION = "028,0834";
	public static final String CRM_KEYS_RELATION   	 = "001,002" ;
	
	public static Map<String, String> jingFenCityMap  ; //经分地关系字典
	public static Map<String, String> CRMCityMap  ; //CRM分地市关系字典
	public static Map<String, String> jinFenCRMRelations ; //经分--CRM映射关系字典
	
	
	/**
	 * 
	 * getTansCityMap:通过经分key,获取CRM地市编码与内容. <br/>
	 * 
	 * @param key
	 * @return
	 * @since JDK 1.6
	 */
	public static Map<String,String> getTansCityMap(String key) {
		Map<String,String> crmMap = new HashMap<String, String>() ;
		initMaps();
		String crmKey = jinFenCRMRelations.get(key) ;
		String crmValue =  CRMCityMap.get(crmKey) ;
		if(crmKey==null) {
			return null ;
		}
		crmMap.put(crmKey, crmValue) ;
		return crmMap ;
	}
	
	/**
	 * initMaps:初始化映射关系. <br/>
	 *
	 * @since JDK 1.6
	 */
	public static void initMaps() {
		//----经分地市关系初始化
		jingFenCityMap = createMapDatas(JINGFEN_CITY_KEYS, JINGFEN_CITY_VALS) ;
		CRMCityMap = createMapDatas(CRM_CITY_KEYS, CRM_CITY_VALS) ;
		jinFenCRMRelations = createMapDatas(JINGFEN_KEYS_RELATION, CRM_KEYS_RELATION) ;
		
		System.out.println(jingFenCityMap+"\n"+CRMCityMap+"\n"+jinFenCRMRelations);
	}
	
	/**
	 * createMapDatas: keys,values长度必须一致，且必须按照格式设计.<br/>
	 * 
	 * @param keys   ex: "028,0834"
	 * @param values ex: "成都,绵阳"
	 *
	 * @return
	 * @since JDK 1.6
	 */
	public static Map<String,String> createMapDatas(String keys, String values) {
		Map<String,String> map = new HashMap<String, String>();
		String [] keysArr = keys.split(",");
		String [] valuesArr = values.split(",");
		for (int i = 0; i < keysArr.length; i++) {
			map.put(keysArr[i], valuesArr[i]) ;
		}
		return map ;
	}
	
	public static void main(String[] args) {
		String key = "028" ;
		System.out.println(getTansCityMap(key)) ;
	}
}

