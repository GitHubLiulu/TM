/**
 *  
 */
package com.ai.ecs.modules.sys.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.ai.ecs.common.persistence.TreeEntity;

/**
 * 机构Entity
 * @author Admin
 * @version 2013-05-15
 */
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = 1L;
	private Office parent;	// 父级编号
	private String parentIds; // 所有父级编号
	private Area area;		// 归属区域
	private String code; 	// 机构编码
	private String name; 	// 机构名称
//	private Integer sort;		// 排序
	private String type; 	// 机构类型（1：公司；2：部门；3：小组）
	private String grade; 	// 机构等级（1：省级；2：市级；3：县级；4：区（网格）级；5：片区经理级；6：营业厅级；）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	private String useable;//是否可用
	private User primaryPerson;//主负责人
	private User deputyPerson;//副负责人
	private List<String> childDeptList;//快速添加子部门
	
	
	private String provinceOfficeCode = "00001";//省公司
	private String cityOfficeCode = "";//市公司
	private String countiesOfficeCode = "";//县公司
	private String areaOfficeCode = "";//片区区(网格)
	private String channelOfficeCode = "";//渠道
	private String terminalOfficeCode = "";//实体店（营业厅），小卖部
	private String userDepartCode;//sys_office表中数据，与tc_kpi_target_shoper表中busihall_code字段映射
	    
	    

    public String getUserDepartCode() {
        return userDepartCode;
    }

    public void setUserDepartCode(String userDepartCode) {
        this.userDepartCode = userDepartCode;
    }

	public Office(){
		super();
//		this.sort = 30;
		this.type = "2";
	}

	public Office(String id){
		super(id);
	}
	
	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public User getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(User primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

//	@JsonBackReference
//	@NotNull
	public Office getParent() {
		return parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
	}

	@Length(min=1, max=2000)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
		setOfficeIDs(parentIds);
	}

	@NotNull
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
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

	@Length(min=1, max=1)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min=0, max=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=100)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min=0, max=100)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Length(min=0, max=200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min=0, max=200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
	
	public void setOfficeIDs(String parentIds){
		
		if(parentIds != null && !parentIds.equals("")){
			String [] pids = parentIds.split(",");
			int len = pids.length;	
			if(len >= 1){
				provinceOfficeCode = pids[0];
				//grade = "1";//省级
			}
			if(len >= 2){
				cityOfficeCode = pids[1];
				//grade = "2";//市级
			}
			if(len >= 3){
				countiesOfficeCode = pids[2];
				//grade = "3";//县级
			}
			if(len >= 4){
				areaOfficeCode = pids[3];
				//grade = "4";//区（网格）级
			}
			if(len >= 5){
				areaOfficeCode = pids[3];
				channelOfficeCode = pids[4];
				//grade = "4";//将片区经理级别定义为区级
				
			}
			if(len >= 6){
				terminalOfficeCode = pids[len-1];
				//grade = "6";//营业厅级
				
			}
			

		}	
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getProvinceOfficeCode() {
		return provinceOfficeCode;
	}

	public String getCityOfficeCode() {
		return cityOfficeCode;
	}

	public String getCountiesOfficeCode() {
		return countiesOfficeCode;
	}

	public String getAreaOfficeCode() {
		return areaOfficeCode;
	}

	public String getChannelOfficeCode() {
		return channelOfficeCode;
	}

	public String getTerminalOfficeCode() {
		return terminalOfficeCode;
	}
	
	
}