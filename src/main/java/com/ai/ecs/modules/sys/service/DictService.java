/**
 *  
 */
package com.ai.ecs.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.ecs.common.service.CrudService;
import com.ai.ecs.common.utils.CacheUtils;
import com.ai.ecs.modules.sys.dao.DictDao;
import com.ai.ecs.modules.sys.entity.Dict;
import com.ai.ecs.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author Admin
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}
	
	/**
	 * 查询字段类型列表根据条件
	 * @return
	 */
	public List<String> findTypeListByConditoin(Dict dict){
		return dao.findTypeListByConditoin(dict);
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

}
