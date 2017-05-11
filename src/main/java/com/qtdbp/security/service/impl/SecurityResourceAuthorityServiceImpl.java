/**
 * 
 */
package com.qtdbp.security.service.impl;

import com.zrhis.base.model.Message;
import com.zrhis.system.bean.SysAuthorities;
import com.zrhis.system.bean.SysAuthoritiesResources;
import com.zrhis.system.bean.SysResources;
import com.zrhis.system.repository.SysAuthorityRepository;
import com.zrhis.system.repository.SysResourceAuthorityRepository;
import com.zrhis.system.repository.SysResourceRepository;
import com.zrhis.system.service.SecurityResourceAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-31 下午1:41:30
 * @version v1.0
 *
 */
@Service
public class SecurityResourceAuthorityServiceImpl implements SecurityResourceAuthorityService{
	
	@Autowired
	private SysResourceAuthorityRepository sysResourceAuthorityRepository;
	
	@Autowired
	private SysResourceRepository sysResourceRepository;
	
	@Autowired
	private SysAuthorityRepository sysAuthorityRepository;
	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityResourceAuthorityService#queryByResource(java.lang.String)
	 */
	@Override
	public Message queryByResource(String resourceId) {
		SysResources resource = this.sysResourceRepository.findOne(resourceId);
		return this.queryByResource(resource);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityResourceAuthorityService#queryByResource(com.zrhis.system.bean.SysResources)
	 */
	@Override
	public Message queryByResource(SysResources resource) {
		List<SysAuthoritiesResources> list = this.sysResourceAuthorityRepository.findBySysResources(resource);
		return new Message(list);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.SecurityResourceAuthorityService#addBatch(java.lang.String, java.lang.String[])
	 */
	@Override
	public Message addBatch(String resourceId, String[] sysAuthorities) {
		this.deleteByResourceId(resourceId);
		
		if(!ObjectUtils.isEmpty(sysAuthorities)){
			List<SysAuthoritiesResources> list = new ArrayList<SysAuthoritiesResources>();
			//SysResources sysResources = this.sysResourceRepository.findOne(resourceId);
			SysResources sysResources = new SysResources(resourceId);
			for(String auid : sysAuthorities){
				SysAuthoritiesResources bean = new SysAuthoritiesResources();
				bean.setSysResources(sysResources);
				SysAuthorities auth = new SysAuthorities();
				auth.setAuthorityId(auid);
				bean.setSysAuthorities(auth);
				list.add(bean);
			}
			this.sysResourceAuthorityRepository.save(list);
		}
	
		return new Message(true,"权限分配成功");
	}
	
	public Message deleteByResourceId(String resourceId){
		this.sysResourceAuthorityRepository.deleteByResourceId(resourceId);
		return new Message(true,"已删除此资源对应的所有权限");
	}

}
