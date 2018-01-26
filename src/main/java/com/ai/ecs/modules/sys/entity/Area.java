/**
 *  
 */
package com.ai.ecs.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.ai.ecs.common.persistence.TreeEntity;

/**
 * 区域Entity
 * @author Admin
 * @version 2013-05-15
 */
public class Area extends TreeEntity<Area> {

	private static final long serialVersionUID = 1L;
    
	private Area parent;	// 父级编号
	private String parentIds; // 所有父级编号
	private String code; 	// 区域编码
	private String name; 	// 区域名称
//	private Integer sort;		// 排序
	private String type; 	// 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
	
	private String provinceCode = "00001";//省编码
	private String cityCode = "";//市编码
	private String countiesCode = "";//县编码
	private String areaCode = "";//区编码
	private String townCode = "";//镇（乡）编码
	private String villageCode = "";//村编码
	
	
	public Area(){
		super();
		this.sort = 30;
	}

	public Area(String id){
		super(id);
	}
	
//	@JsonBackReference
//	@NotNull
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	@Length(min=1, max=2000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
		setAreaIDs(parentIds);
	}
//	
//	@Length(min=1, max=100)
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}

	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
//
//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
	
	public void setAreaIDs(String parentIds){
		
		if(parentIds != null && !parentIds.equals("")){
			String [] pids = parentIds.split(",");
			int len = pids.length;	
			if(len >= 1){
				this.provinceCode = pids[0];
				this.cityCode = id;
				this.type = "2";//省
			}
			if(len >= 2){
				this.cityCode = pids[1];
				this.countiesCode = id;
				this.type = "3";//市
			}
			if(len >= 3){
				this.countiesCode = pids[2];
				this.areaCode = id;
				this.type = "4";//县
			}
			if(len >= 4){
				this.areaCode = pids[3];
				this.townCode = id;
				this.type = "5";//网格（片区） 
			}
			if(len >= 5){
				this.townCode = pids[4];
				this.type = "6";//镇村
				this.villageCode = id;
			}

		}	
	}
	
	
	@Override
	public String toString() {
		return name;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCountiesCode() {
		return countiesCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getTownCode() {
		return townCode;
	}

	public String getVillageCode() {
		return villageCode;
	}
	
	
	
}