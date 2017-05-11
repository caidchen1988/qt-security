/**
 * 
 */
package com.qtdbp.security.repository;

import com.qtdbp.security.model.SysRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public interface SysRoleRepository extends JpaRepository<SysRoles, String> {
	
	//@Query("select x from SysRoles x where x.roleName like ?1")
	public Page<SysRoles> findByRoleNameLike(String roleName, Pageable pageable);
	
	@Query("select x from SysRoles x where x.enable = true")
	public Page<SysRoles> findAllEnabeld(Pageable pageable);
	
	@Query("select x from SysRoles x where x.enable = true")
	public List<SysRoles> findAllEnabeld();
	
	@Query("select x from SysRoles x where x.enable = true and x.issys = ?")
	public List<SysRoles> findAllEnabeld(boolean issys);
	
	@Query("select x from SysRoles x where x.enable = true and x.roleName like ?1")
	public Page<SysRoles> findByRoleNameLikeAndEnabled(String roleName, Pageable pageable);
	
	public List<SysRoles> findByUserId(String userId);
	
	@Query("select x from SysRoles x where x.enable = ? and x.userId = ?")
	public List<SysRoles> findByUserId(boolean enable, String userId);
	
}
