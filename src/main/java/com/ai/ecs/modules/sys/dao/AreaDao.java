/**
 *  
 */
package com.ai.ecs.modules.sys.dao;

import java.util.List;

import com.ai.ecs.common.persistence.TreeDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author Admin
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
	public List<Area> queryAreaTop();

	public List<Area> findPageList(Area area);
	
	public List<Area> findLeafList(String parentId);
}
