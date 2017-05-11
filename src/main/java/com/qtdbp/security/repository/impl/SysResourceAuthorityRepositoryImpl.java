/**
 * 
 */
package com.qtdbp.security.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public class SysResourceAuthorityRepositoryImpl {

	protected Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void deleteByResourceId(String resourceId){
		String sql = "DELETE FROM SysAuthoritiesResources x WHERE x.resourceId = :resourceId";
		Query query = this.entityManager.createQuery(sql);
		query.setParameter("resourceId", resourceId);
		int deleted = query.executeUpdate();
		
		logger.info("共删除了"+deleted+"行（资源权限表）");
	}
	
}
