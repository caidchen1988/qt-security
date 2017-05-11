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
public class SysUsersRolesRepositoryImpl {

	protected Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void deleteByUserId(String userId){
		String sql = "delete from SysUsersRoles x where x.userId = ?1";
		Query query = this.entityManager.createQuery(sql);
		query.setParameter(1, userId);
		
		int deleted = query.executeUpdate();
		
		logger.info("共删除了"+deleted+"行（用户权限表）");
	}
	
	public void deleteByUserId(String userId,String czybh){
		String sql = "delete from SysUsersRoles x where x.userId = ?1 and x.czybh = ?2";
		Query query = this.entityManager.createQuery(sql);
		query.setParameter(1, userId);
		query.setParameter(2, czybh);
		
		int deleted = query.executeUpdate();
		
		logger.info("共删除了"+deleted+"行（用户权限表）");
	}
}
