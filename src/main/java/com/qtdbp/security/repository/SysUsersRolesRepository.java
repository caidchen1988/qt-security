/**
 * 
 */
package com.qtdbp.security.repository;

import com.qtdbp.security.model.SysUsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public interface SysUsersRolesRepository extends JpaRepository<SysUsersRoles, String> {
	
	public List<SysUsersRoles> findByUserId(String userId);
	
	public void deleteByUserId(String userId);
	
	public void deleteByUserId(String userId, String czybh);
}
