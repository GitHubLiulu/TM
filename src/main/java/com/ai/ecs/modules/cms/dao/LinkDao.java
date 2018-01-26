/**
 *  
 */
package com.ai.ecs.modules.cms.dao;

import java.util.List;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.cms.entity.Link;

/**
 * 链接DAO接口
 * @author Admin
 * @version 2013-8-23
 */
@MyBatisDao
public interface LinkDao extends CrudDao<Link> {
	
	public List<Link> findByIdIn(String[] ids);
//	{
//		return find("front Like where id in (:p1)", new Parameter(new Object[]{ids}));
//	}
	
	public int updateExpiredWeight(Link link);
//	{
//		return update("update Link set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}
//	public List<Link> fjindListByEntity();
}
