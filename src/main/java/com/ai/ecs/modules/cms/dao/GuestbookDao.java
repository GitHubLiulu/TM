/**
 *  
 */
package com.ai.ecs.modules.cms.dao;

import com.ai.ecs.common.persistence.CrudDao;
import com.ai.ecs.common.persistence.annotation.MyBatisDao;
import com.ai.ecs.modules.cms.entity.Guestbook;

/**
 * 留言DAO接口
 * @author Admin
 * @version 2013-8-23
 */
@MyBatisDao
public interface GuestbookDao extends CrudDao<Guestbook> {

}
