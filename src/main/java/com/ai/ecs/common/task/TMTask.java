package com.ai.ecs.common.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ai.ecs.common.config.Global;
import com.ai.ecs.common.utils.DateUtils;
import com.ai.ecs.modules.tm.entity.SysToolAll;
import com.ai.ecs.modules.tm.entity.SysToolCheckRecord;
import com.ai.ecs.modules.tm.service.SysToolAllService;
import com.ai.ecs.modules.tm.service.SysToolCheckRecordService;
import com.google.common.collect.Lists;


//@Component
@Service
@Lazy(false)
public class TMTask {
	
	@Autowired
	private SysToolAllService sysToolAllService;
	
	@Autowired
	private SysToolCheckRecordService sysToolCheckRecordService;
	
//	int i = 10;
//	@Scheduled(fixedDelay = 50000)
//	public void doSomething() { 
//	    System.out.println(i);
//	    i++;
//	}
	
	@Scheduled(fixedDelay = 50000)
	public void doTask(){
		List<SysToolAll> listSysToolAll = sysToolAllService.findListForRecord( new SysToolAll());
		int period = 0;
		for(SysToolAll sysToolAll : listSysToolAll){
			if(sysToolAll.getPeriod() != null || "".equals(sysToolAll.getPeriod())){
				period = Integer.parseInt(sysToolAll.getPeriod()) ;
				if(period >= Math.abs(DateUtils.remainDays(sysToolAll.getCheckDate()))){
					//因时间问题，两个表的更新为单表更新，以后若时间充足，修改为批量更新
					//写入记录表
					SysToolCheckRecord sysToolCheckRecord = new SysToolCheckRecord();
					sysToolCheckRecord.setToolId(sysToolAll.getToolId());
					sysToolCheckRecord.setToolIdReal(sysToolAll.getToolIdReal());
					sysToolCheckRecord.setToolName(sysToolAll.getToolName());
					sysToolCheckRecordService.save(sysToolCheckRecord);
					//更新SysToolAll表，record_flag字段，代表已记录
					sysToolAll.setRecordFlag(Global.CHECK_YES);
					sysToolAll.setId(sysToolAll.getToolId());
					sysToolAllService.save(sysToolAll);
				}
			}
		}
	}
	
	
	
}
