/**
 *  
 */
package com.ai.ecs.modules.tm.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.ai.ecs.common.persistence.DataEntity;

/**
 * 工具管理Entity
 * @author liulu6
 * @version 2017-11-10
 */
public class SysToolAll extends DataEntity<SysToolAll> {
	
	private static final long serialVersionUID = 1L;
	private String toolId;		// tool_id
	private String toolName;		// tool_name
	private String specification;		// specification
	private Date buyDate;		// buy_date
	private Date deleteDate;		// delete_date
	private Date checkDate;		// check_date
	private Date preCheckDate;		// pre_check_date
	private String keeper;		// keeper
	private String phone;		// phone
	private String amount;
	private String checkTimes;
	private String checkFlag;
	private String recordFlag;

	private String period;  //定义预警时间，定时5天，却提前5天提醒用户。

	private String checkPeriod;  //定义周期，比如30天。即30天后会校验。

	private String isCycle; //是否循环校验工具，0为循环校验，1为仅校验一次

	private String origin; //定义来源，确定是否是来源于校验工具之后  0为是，1为否

	private String remainDay; //离校验还剩多少天   计算方式：校验日期 - 当前日期

	private String toolIdReal ;//用户实际录入ID

	private String price ; //价格

	public SysToolAll() {
		super();
		this.checkTimes = DEL_FLAG_NORMAL;
		this.checkFlag = DEL_FLAG_NORMAL;
		this.recordFlag = DEL_FLAG_NORMAL;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getToolIdReal() {
		return toolIdReal;
	}

	public void setToolIdReal(String toolIdReal) {
		this.toolIdReal = toolIdReal;
	}

	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}


	public String getRecordFlag() {
		return recordFlag;
	}


	public void setRecordFlag(String recordFlag) {
		this.recordFlag = recordFlag;
	}



	public SysToolAll(String id){
		super(id);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	

	public String getCheckTimes() {
		return checkTimes;
	}

	public void setCheckTimes(String checkTimes) {
		this.checkTimes = checkTimes;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	//@Length(min=1, max=64, message="tool_id长度必须介于 1 和 64 之间")
	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	
	@Length(min=0, max=256, message="tool_name长度必须介于 0 和 256 之间")
	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	
	@Length(min=0, max=64, message="specification长度必须介于 0 和 64 之间")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPreCheckDate() {
		return preCheckDate;
	}

	public void setPreCheckDate(Date preCheckDate) {
		this.preCheckDate = preCheckDate;
	}
	
	@Length(min=0, max=64, message="keeper长度必须介于 0 和 64 之间")
	public String getKeeper() {
		return keeper;
	}

	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}
	
	@Length(min=0, max=200, message="phone长度必须介于 0 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIsCycle() {
		return isCycle;
	}

	public void setIsCycle(String isCycle) {
		this.isCycle = isCycle;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getRemainDay() {
		return remainDay;
	}

	public void setRemainDay(String remainDay) {
		this.remainDay = remainDay;
	}
}