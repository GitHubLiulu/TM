/**
 *  
 */
package com.ai.ecs.modules.tm.entity;

import org.hibernate.validator.constraints.Length;

import com.ai.ecs.common.persistence.DataEntity;

/**
 * 周期定义Entity
 * @author liulu6
 * @version 2017-11-18
 */
public class SysRemindInfo extends DataEntity<SysRemindInfo> {
	
	private static final long serialVersionUID = 1L;
	private String toolId;		// TOOL_ID
	private String period;		// PERIOD
	
	public SysRemindInfo() {
		super();
	}

	public SysRemindInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="TOOL_ID长度必须介于 1 和 64 之间")
	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	
	@Length(min=0, max=8, message="PERIOD长度必须介于 0 和 8 之间")
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
}