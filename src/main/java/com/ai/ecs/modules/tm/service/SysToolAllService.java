/**
 *  
 */
package com.ai.ecs.modules.tm.service;

import java.util.Date;
import java.util.List;

import com.ai.ecs.common.config.Global;
import com.ai.ecs.common.utils.DateUtils;
import com.ai.ecs.modules.tm.entity.SysRemindInfo;
import com.ai.ecs.modules.tm.entity.SysTollAllReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.ecs.common.persistence.Page;
import com.ai.ecs.common.service.CrudService;
import com.ai.ecs.modules.tm.entity.SysToolAll;
import com.ai.ecs.modules.tm.dao.SysToolAllDao;

/**
 * 工具管理Service
 * @author liulu6
 * @version 2017-11-10
 */
@Service
@Transactional(readOnly = true)
public class SysToolAllService extends CrudService<SysToolAllDao, SysToolAll> {

	@Autowired
	private SysToolAllDao sysToolAllDao ;

	@Autowired
	private SysRemindInfoService sysRemindInfoService;
	
	public SysToolAll get(String id) {
		return super.get(id);
	}
	
	public List<SysToolAll> findList(SysToolAll sysToolAll) {
		return super.findList(sysToolAll);
	}
	
	public Page<SysToolAll> findPage(Page<SysToolAll> page, SysToolAll sysToolAll) {
		return super.findPage(page, sysToolAll);
	}
	
	@Transactional(readOnly = false)
	public void save(SysToolAll sysToolAll) {
		int keyId = 0;
		SysTollAllReturn sysToolAll1Temp = new SysTollAllReturn();
		SysRemindInfo sysRemindInfo =  new SysRemindInfo();
		//根据ID判定是否为新增
		if (!"".equals(sysToolAll.getToolId()) && sysToolAll.getToolId() != null) {
			sysToolAll.setId(sysToolAll.getToolId());
			sysRemindInfo.setId(sysToolAll.getToolId());
			sysToolAll1Temp.setId(sysToolAll.getToolId());
			sysToolAll1Temp.setToolId(Integer.parseInt(sysToolAll.getToolId()));
		}
		//如果为循环校验周期，则在现在时间基础上加上周期（天）的时间，即为下次校验的checkDate
		if (Global.CHECK_NO.equals(sysToolAll.getIsCycle())) {
			//下次校验时间=本次校验时间+循环的周期（如果没有本次校验即0次校验则，下次校验时间=购买时间+循环周期。）
			if (sysToolAll.getPreCheckDate() != null) {
				sysToolAll.setCheckDate(DateUtils.changeDate(sysToolAll.getPreCheckDate(),Integer.parseInt(sysToolAll.getCheckPeriod())));
			}else {
				//sysToolAll.setCheckDate(DateUtils.changeDate(new Date(),Integer.parseInt(sysToolAll.getCheckPeriod())));
				//调整为根据购买的日期，定义校验日期
				sysToolAll.setCheckDate(DateUtils.changeDate(sysToolAll.getBuyDate(),Integer.parseInt(sysToolAll.getCheckPeriod())));
			}

		} else {//单次校验，计算出周期，入库
			sysToolAll.setCheckPeriod((int)(DateUtils.getDistanceOfTwoDate(sysToolAll.getBuyDate(),sysToolAll.getCheckDate())) + "");
		}
		// 实体属性拷贝
		String toolName = sysToolAll.getToolName();
		sysToolAll1Temp.setToolName(toolName);
		sysToolAll1Temp.setSpecification(sysToolAll.getSpecification());
		sysToolAll1Temp.setBuyDate(sysToolAll.getBuyDate());
		sysToolAll1Temp.setDeleteDate(sysToolAll.getDeleteDate());
		sysToolAll1Temp.setCheckDate(sysToolAll.getCheckDate());
		sysToolAll1Temp.setPreCheckDate(sysToolAll.getPreCheckDate());
		sysToolAll1Temp.setKeeper(sysToolAll.getKeeper());
		sysToolAll1Temp.setPhone(sysToolAll.getPhone());
		sysToolAll1Temp.setAmount(sysToolAll.getAmount());
		sysToolAll1Temp.setCheckTimes(sysToolAll.getCheckTimes());
		//sysToolAll1Temp.setCheckDate(sysToolAll.getCheckDate());
		sysToolAll1Temp.setPreCheckDate(sysToolAll.getPreCheckDate());
		sysToolAll1Temp.setRecordFlag(sysToolAll.getRecordFlag());
		sysToolAll1Temp.setPeriod(sysToolAll.getPeriod());
		sysToolAll1Temp.setCheckPeriod(sysToolAll.getCheckPeriod());
		sysToolAll1Temp.setIsCycle(sysToolAll.getIsCycle());
		sysToolAll1Temp.setPrice(sysToolAll.getPrice());
		sysToolAll1Temp.setToolIdReal(sysToolAll.getToolIdReal());

		if (sysToolAll1Temp.getIsNewRecord()) {
			sysToolAll1Temp.preInsert();
			keyId = sysToolAllDao.insertUnique(sysToolAll1Temp);
		} else {
			sysToolAll.preUpdate();
			sysToolAllDao.update(sysToolAll);
		}
		//保存默认提醒时间(取不到ID异常)

		sysRemindInfo.setToolId(sysToolAll1Temp.getToolId().toString());
		sysRemindInfo.setPeriod(Global.defalut_period);
		sysRemindInfo.setToolIdReal(sysToolAll1Temp.getToolIdReal());
		sysRemindInfoService.save(sysRemindInfo);
		//super.save(sysToolAll);

	}
	
	@Transactional(readOnly = false)
	public void checkSave(SysToolAll sysToolAll) {
		super.save(sysToolAll);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysToolAll sysToolAll) {
		super.delete(sysToolAll);
	}
	
	public List<SysToolAll> findListForRecord(SysToolAll sysToolAll) {
		return sysToolAllDao.findListForRecord(sysToolAll);
	}

	/**
	 *  插入返回主键ID
	 * @param sysTollAllReturn
	 * @return
	 */
	public int insertUnique(SysTollAllReturn sysTollAllReturn){
		return sysToolAllDao.insertUnique(sysTollAllReturn);
	}

	/**
	 * 查询出所有需要校验的工具（即在定义的校验周期内的数据）
	 * @param sysToolAll
	 * @return
	 */
	public List<SysToolAll> findNeedCheckToolList(SysToolAll sysToolAll){
		//计算距离校验剩余多少天
		List<SysToolAll> sysToolAlls = sysToolAllDao.findNeedCheckToolList(sysToolAll);
		for (SysToolAll sysToolAllTemp : sysToolAlls ) {
				//DateUtils.remainDays(sysToolAllTemp.getCheckDate());
			sysToolAllTemp.setRemainDay(Math.abs(DateUtils.remainDays(sysToolAllTemp.getCheckDate()))+"");
		}
		return sysToolAlls;
	}

}