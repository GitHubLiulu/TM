/**
 *  
 */
package com.ai.ecs.modules.tm.dao;

import java.util.List;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.tm.entity.SysTollAllReturn;
import com.ai.ecs.modules.tm.entity.SysToolAll;

/**
 * 工具管理DAO接口
 * @author liulu6
 * @version 2017-11-10
 */
@MyBatisDao
public interface SysToolAllDao extends CrudDao<SysToolAll> {
	
	public List<SysToolAll> findListForRecord (SysToolAll sysToolAll);

	/**
	 *  插入返回主键ID
	 * @param sysToolAll
	 * @return
	 */
	public int insertUnique(SysTollAllReturn sysToolAll);

	/**
	 * 查询出所有需要校验的工具（即在定义的校验周期内的数据）
	 * @param sysToolAll
	 * @return
	 */
	public List<SysToolAll> findNeedCheckToolList(SysToolAll sysToolAll);
	
}