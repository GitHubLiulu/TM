/**
 *  
 */
package com.ai.ecs.modules.tm.dao;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.tm.entity.SysRemindInfo;

/**
 * 周期定义DAO接口
 * @author liulu6
 * @version 2017-11-18
 */
@MyBatisDao
public interface SysRemindInfoDao extends CrudDao<SysRemindInfo> {

    /**
     * 批量更新工具提醒周期时间
     */

    public void updateBatch(SysRemindInfo sysRemindInfo);

}