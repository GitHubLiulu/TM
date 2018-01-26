/**
 *  
 */
package com.ai.ecs.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.ecs.common.persistence.Page;
import com.ai.ecs.common.service.TreeService;
import com.ai.ecs.modules.sys.dao.AreaDao;
import com.ai.ecs.modules.sys.entity.Area;
import com.ai.ecs.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author Admin
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	@Autowired
	AreaDao areaDao;
	
	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	public Page<Area> findPageList(Area area){
		Page<Area> page = area.getPage();
		page.setList(areaDao.findPageList(area));
		return page;
	}
	
	public List<Area> findLeafList(String parentId) {
		return dao.findLeafList(parentId);
	}
	
}
