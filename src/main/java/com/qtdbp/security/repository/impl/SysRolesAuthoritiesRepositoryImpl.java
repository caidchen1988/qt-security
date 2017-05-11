/**
 * 
 */
package com.qtdbp.security.repository.impl;

import com.qtdbp.security.model.SysAuthorities;
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
public class SysRolesAuthoritiesRepositoryImpl {
	
	protected Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<SysAuthorities> getAssignationAuthority(String roleId){
		String sql = "select ss.authority_id,ss.authority_mark,ss.authority_name,ss.module_id,"+
			"ts.module_name remark,ss.authority_desc,ss.message,ss.enable,ss.issys "+ 
			"from (select * from sys_authorities s where module_id in ( "+
			"      select module_id from sys_roles_modules t where t.role_id = ?1 "+
			") or module_id is null and enable = 1) ss "+
			"left join sys_modules ts on ss.module_id = ts.module_id ";
		
		Query query = this.entityManager.createNativeQuery(sql, SysAuthorities.class);
		query.setParameter(1, roleId);
		List<SysAuthorities> list = query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<SysAuthorities> findByIssys(boolean issys){
		String sql = "SELECT SS.AUTHORITY_ID,SS.AUTHORITY_MARK,SS.AUTHORITY_NAME,SS.MODULE_ID, " +
				"S2.MODULE_NAME REMARK,SS.AUTHORITY_DESC,SS.MESSAGE,SS.ENABLE,SS.ISSYS FROM " +
				"(SELECT * FROM SYS_AUTHORITIES WHERE ISSYS = ?1 AND ENABLE = 1) SS " +
				"LEFT JOIN SYS_MODULES S2 ON SS.MODULE_ID = S2.MODULE_ID ORDER BY S2.PRIORITY";
		
		Query query = this.entityManager.createNativeQuery(sql, SysAuthorities.class);
		query.setParameter(1, issys);
		List<SysAuthorities> list = query.getResultList();
		return list;
	}
	

	@SuppressWarnings("unchecked")
	public List<SysAuthorities> findByEnabled(){
		String sql = "SELECT SS.AUTHORITY_ID,SS.AUTHORITY_MARK,SS.AUTHORITY_NAME,SS.MODULE_ID, " +
				"S2.MODULE_NAME REMARK,SS.AUTHORITY_DESC,SS.MESSAGE,SS.ENABLE,SS.ISSYS FROM " +
				"(SELECT * FROM SYS_AUTHORITIES WHERE ENABLE = 1) SS " +
				"LEFT JOIN SYS_MODULES S2 ON SS.MODULE_ID = S2.MODULE_ID ORDER BY S2.PRIORITY";
		
		Query query = this.entityManager.createNativeQuery(sql, SysAuthorities.class);
		List<SysAuthorities> list = query.getResultList();
		return list;
	};
	
	public void deleteByRoleId(String roleId){
		String sql = "DELETE FROM SysRolesAuthorities x WHERE x.roleId = :roleId";
		
		Query query = this.entityManager.createQuery(sql);
		query.setParameter("roleId", roleId);
		
		int deleted = query.executeUpdate();
		logger.info("共删除了"+deleted+"行（角色权限表）");
	}
}
