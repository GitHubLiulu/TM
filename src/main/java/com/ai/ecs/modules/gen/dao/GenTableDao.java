/**
 *  
 */
package com.ai.ecs.modules.gen.dao;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * @author Admin
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}
