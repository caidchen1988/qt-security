/**
 * 
 */
package com.qtdbp.security.repository;

import com.qtdbp.security.entity.SysUsers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 类功能说明：用户管理Dao层
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public interface SysUsersRepository extends JpaRepository<SysUsers, String> {
	
//	Page<SysUsers> findByUsernameLike(String username, Pageable pageable);

	SysUsers findByUsername(String username);
	
}
