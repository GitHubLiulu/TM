/**
 *  
 */
package com.ai.ecs.modules.tm.entity;

import org.hibernate.validator.constraints.Length;

import com.ai.ecs.common.persistence.DataEntity;

/**
 * 检查数据记录Entity
 * @author liulu6
 * @version 2017-11-18
 */
public class SysToolCheckRecord extends DataEntity<SysToolCheckRecord> {
	
	private static final long serialVersionUID = 1L;
	private String toolId;		// TOOL_ID
	
	public SysToolCheckRecord() {
		super();
	}

	public SysToolCheckRecord(String id){
		super(id);
	}

	@Length(min=1, max=64, message="TOOL_ID长度必须介于 1 和 64 之间")
	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	
}