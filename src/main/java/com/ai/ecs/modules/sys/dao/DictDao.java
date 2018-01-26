/**
 *  
 */
package com.ai.ecs.modules.sys.dao;

import java.util.List;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author Admin
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
	public List<String> findTypeListByConditoin(Dict dict);
	
}
