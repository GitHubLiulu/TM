/**
 *  
 */
package com.ai.ecs.modules.sys.dao;

import java.util.List;

import com.ai.ecs.common.persistence.TreeDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author Admin
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	List<Office> findPageList(Office office);
	
}
