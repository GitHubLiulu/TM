/**
 *  
 */
package com.ai.ecs.modules.tm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.ecs.common.persistence.Page;
import com.ai.ecs.common.service.CrudService;
import com.ai.ecs.modules.tm.entity.SysToolCheckRecord;
import com.ai.ecs.modules.tm.dao.SysToolCheckRecordDao;

/**
 * 检查数据记录Service
 * @author liulu6
 * @version 2017-11-18
 */
@Service
@Transactional(readOnly = true)
public class SysToolCheckRecordService extends CrudService<SysToolCheckRecordDao, SysToolCheckRecord> {

	public SysToolCheckRecord get(String id) {
		return super.get(id);
	}
	
	public List<SysToolCheckRecord> findList(SysToolCheckRecord sysToolCheckRecord) {
		return super.findList(sysToolCheckRecord);
	}
	
	public Page<SysToolCheckRecord> findPage(Page<SysToolCheckRecord> page, SysToolCheckRecord sysToolCheckRecord) {
		return super.findPage(page, sysToolCheckRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(SysToolCheckRecord sysToolCheckRecord) {
		super.save(sysToolCheckRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysToolCheckRecord sysToolCheckRecord) {
		super.delete(sysToolCheckRecord);
	}
	
}