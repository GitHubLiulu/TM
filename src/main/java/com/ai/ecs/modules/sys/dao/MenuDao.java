/**
 *  
 */
package com.ai.ecs.modules.sys.dao;

import java.util.List;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author Admin
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	public List<Menu> selectMenuByDesc(Menu menu);
	
	public List<Menu> findAdminAllMenu(Menu menu);
	
	public List<Menu> findByAdminUserId(Menu menu);
	
}
