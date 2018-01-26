/**
 *  
 */
package com.ai.ecs.modules.tm.dao;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.tm.entity.SysToolCheckRecord;

/**
 * 检查数据记录DAO接口
 * @author liulu6
 * @version 2017-11-18
 */
@MyBatisDao
public interface SysToolCheckRecordDao extends CrudDao<SysToolCheckRecord> {
	
}