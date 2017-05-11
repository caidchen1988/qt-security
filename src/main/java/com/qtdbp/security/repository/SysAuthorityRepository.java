/**
 * 
 */
package com.qtdbp.security.repository;

import com.qtdbp.security.model.SysAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2016-2017 qtbigdata.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
public interface SysAuthorityRepository extends JpaRepository<SysAuthorities, String> {
	
//	@Query("select x from SysAuthorities x where moduleId = ?1 or moduleId is null")
	public List<SysAuthorities> findByModuleId(String moduleId);
	
	public List<SysAuthorities> findByModuleIdIsNull();

}
