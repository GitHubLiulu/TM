/**
 *  
 */
package com.ai.ecs.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.ecs.common.service.CrudService;
import com.ai.ecs.modules.cms.dao.ArticleDataDao;
import com.ai.ecs.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author Admin
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
