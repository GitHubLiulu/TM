/**
 *  
 */
package com.ai.ecs.modules.tm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.ecs.common.persistence.Page;
import com.ai.ecs.common.service.CrudService;
import com.ai.ecs.modules.tm.entity.SysRemindInfo;
import com.ai.ecs.modules.tm.dao.SysRemindInfoDao;

/**
 * 周期定义Service
 * @author liulu6
 * @version 2017-11-18
 */
@Service
@Transactional(readOnly = true)
public class SysRemindInfoService extends CrudService<SysRemindInfoDao, SysRemindInfo> {

	@Autowired
	private SysRemindInfoDao sysRemindInfoDao;

	public SysRemindInfo get(String id) {
		return super.get(id);
	}
	
	public List<SysRemindInfo> findList(SysRemindInfo sysRemindInfo) {
		return super.findList(sysRemindInfo);
	}
	
	public Page<SysRemindInfo> findPage(Page<SysRemindInfo> page, SysRemindInfo sysRemindInfo) {
		return super.findPage(page, sysRemindInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SysRemindInfo sysRemindInfo) {
		//根据ID判定是否为新增
//		if (!"".equals(sysRemindInfo.getToolId()) && sysRemindInfo.getToolId() != null) {
//			sysRemindInfo.setId(sysRemindInfo.getToolId());
//		}
		super.save(sysRemindInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysRemindInfo sysRemindInfo) {
		super.delete(sysRemindInfo);
	}

	public void updateBatch(SysRemindInfo sysRemindInfo){
		sysRemindInfoDao.updateBatch(sysRemindInfo);
	}

}