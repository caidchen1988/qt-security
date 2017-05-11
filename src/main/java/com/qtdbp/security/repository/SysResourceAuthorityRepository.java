package com.qtdbp.security.repository;

import com.qtdbp.security.model.SysAuthoritiesResources;
import com.qtdbp.security.model.SysResources;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author caidchen
 * @version v1.0
 *
 */
public interface SysResourceAuthorityRepository extends JpaRepository<SysAuthoritiesResources, String>{
	
	List<SysAuthoritiesResources> findBySysResources(SysResources resource);
	
	public void deleteByResourceId(String resourceId);
	
}
