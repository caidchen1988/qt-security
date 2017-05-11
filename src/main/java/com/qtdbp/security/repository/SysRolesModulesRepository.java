/**
 * 
 */
package com.qtdbp.security.repository;

import com.qtdbp.security.model.SysModules;
import com.qtdbp.security.model.SysRolesModules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public interface SysRolesModulesRepository  extends JpaRepository<SysRolesModules, String>{
	
	
	public List<SysRolesModules> findByRoleId(String roleId);
	
	public void deleteByRoleIdAndModuleId(String roleId, Collection<String> moduleId);
	
	public List<SysModules> getModulesByRoleId(String roleId);
	
}
