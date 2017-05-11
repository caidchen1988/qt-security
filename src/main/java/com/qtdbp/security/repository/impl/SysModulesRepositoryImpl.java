/**
 * 
 */
package com.qtdbp.security.repository.impl;

import com.qtdbp.security.model.SysModules;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public class SysModulesRepositoryImpl {

	protected Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 根据UserId获取到分配的模块
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysModules> getModulesByUserId(String userId, String parentId){
		String sql = "select * from sys_modules where module_id in ( "+
			  "select s1.module_id from sys_roles_modules s1 "+
			  "join sys_users_roles s2 on s1.role_id = s2.role_id and s2.user_id = ?1) "+
			"and parent = ?2 order by priority";
		
		Query query = this.entityManager.createNativeQuery(sql, SysModules.class);
		query.setParameter(1, userId);
		query.setParameter(2, parentId);
		
		List<SysModules> list = query.getResultList();
		return list;
	}
}
