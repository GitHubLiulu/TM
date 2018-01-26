/**
 *  
 */
package com.ai.ecs.modules.gen.dao;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author Admin
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
