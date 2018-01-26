/**
 *  
 */
package com.ai.ecs.modules.gen.dao;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.gen.entity.GenTemplate;

/**
 * 代码模板DAO接口
 * @author Admin
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTemplateDao extends CrudDao<GenTemplate> {
	
}
