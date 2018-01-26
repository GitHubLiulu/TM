/**
 *  
 */
package com.ai.ecs.modules.sys.dao;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author Admin
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
